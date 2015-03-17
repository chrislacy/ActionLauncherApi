/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.muzei;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.apps.muzei.event.ArtDetailOpenedClosedEvent;
import com.google.android.apps.muzei.event.ArtworkSizeChangedEvent;
import com.google.android.apps.muzei.event.SwitchingPhotosStateChangedEvent;
import com.google.android.apps.muzei.event.WallpaperActiveStateChangedEvent;
import com.google.android.apps.muzei.event.WallpaperSizeChangedEvent;
import com.google.android.apps.muzei.util.DrawInsetsFrameLayout;
import com.google.android.apps.muzei.util.LogUtil;
import com.google.android.apps.muzei.util.PanScaleProxyView;
import com.google.android.apps.muzei.util.ScrimUtil;

import net.nurik.roman.muzei.R;

import de.greenrobot.event.EventBus;

public class MuzeiActivity extends ActionBarActivity {
    private static final String TAG = LogUtil.makeLogTag(MuzeiActivity.class);

    // Controller/logic fields
    private float mWallpaperAspectRatio;
    private float mArtworkAspectRatio;

    // UI flags
    private int mUiMode = UI_MODE_ART_DETAIL;
    private static final int UI_MODE_ART_DETAIL = 0;
    private static final int UI_MODE_TUTORIAL = 2; // active wallpaper, but first time

    private boolean mPaused;
    private boolean mWindowHasFocus;
    private Boolean mWallpaperActive;
    private boolean mSeenTutorial;
    private boolean mDeferResetViewport;

    // UI
    private Handler mHandler = new Handler();
    private DrawInsetsFrameLayout mContainerView;

    // Normal mode UI
    private View mChromeContainerView;
    private View mStatusBarScrimView;
    private View mMetadataView;
    private TextView mTitleView;
    private TextView mBylineView;
    private PanScaleProxyView mPanScaleProxyView;

    // Tutorial mode UI
    private View mTutorialContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muzei_activity);

        mContainerView = (DrawInsetsFrameLayout) findViewById(R.id.container);

        setupArtDetailModeUi();
        setupIntroModeUi();

        mContainerView.setOnInsetsCallback(new DrawInsetsFrameLayout.OnInsetsCallback() {
            @Override
            public void onInsetsChanged(Rect insets) {
                mChromeContainerView.setPadding(
                        insets.left, insets.top, insets.right, insets.bottom);
                if (mTutorialContainerView != null) {
                    mTutorialContainerView.setPadding(
                            insets.left, insets.top, insets.right, insets.bottom);
                }
            }
        });

        showHideChrome(true);

        EventBus.getDefault().register(this);

        WallpaperSizeChangedEvent wsce = EventBus.getDefault().getStickyEvent(
                WallpaperSizeChangedEvent.class);
        if (wsce != null) {
            onEventMainThread(wsce);
        }

        ArtworkSizeChangedEvent asce = EventBus.getDefault().getStickyEvent(
                ArtworkSizeChangedEvent.class);
        if (asce != null) {
            onEventMainThread(asce);
        }

        SwitchingPhotosStateChangedEvent spsce = EventBus.getDefault().getStickyEvent(
                SwitchingPhotosStateChangedEvent.class);
        if (spsce != null) {
            onEventMainThread(spsce);
        }

        updateArtDetailUi();
    }

    private void setupIntroModeUi() {
        findViewById(R.id.activate_muzei_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
                            .putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                    new ComponentName(MuzeiActivity.this,
                                            MuzeiWallpaperService.class))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } catch (ActivityNotFoundException e) {
                    try {
                        startActivity(new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } catch (ActivityNotFoundException e2) {
                        Toast.makeText(MuzeiActivity.this, R.string.error_wallpaper_chooser,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private View getMainContainerForUiMode(int mode) {
        switch (mode) {
            case UI_MODE_ART_DETAIL: return mChromeContainerView;
            case UI_MODE_TUTORIAL: return mTutorialContainerView;
        }

        return null;
    }

    private void updateUiMode() {
        // TODO: this should really just use fragment transactions and transitions

        int newUiMode = UI_MODE_ART_DETAIL;
        findViewById(R.id.activate_muzei_button).setVisibility(mWallpaperActive != null && mWallpaperActive ? View.GONE : View.VISIBLE);

        if (mUiMode == newUiMode) {
            return;
        }

        // Crossfade between main containers
        final View oldContainerView = getMainContainerForUiMode(mUiMode);
        final View newContainerView = getMainContainerForUiMode(newUiMode);

        if (oldContainerView != null) {
            oldContainerView.animate()
                    .alpha(0)
                    .setDuration(1000)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            oldContainerView.setVisibility(View.GONE);
                        }
                    });
        }

        if (newContainerView != null) {
            if (newContainerView.getAlpha() == 1) {
                newContainerView.setAlpha(0);
            }
            newContainerView.setVisibility(View.VISIBLE);
            newContainerView.animate()
                    .alpha(1)
                    .setDuration(1000)
                    .withEndAction(null);
        }

        mPanScaleProxyView.setVisibility(newUiMode == UI_MODE_ART_DETAIL
                ? View.VISIBLE : View.GONE);

        mUiMode = newUiMode;

        maybeUpdateArtDetailOpenedClosed();
    }

    private void maybeUpdateArtDetailOpenedClosed() {
        boolean currentlyOpened = false;
        ArtDetailOpenedClosedEvent adoce = EventBus.getDefault()
                .getStickyEvent(ArtDetailOpenedClosedEvent.class);
        if (adoce != null) {
            currentlyOpened = adoce.isArtDetailOpened();
        }

        boolean shouldBeOpened = false;
        if (mUiMode == UI_MODE_ART_DETAIL
//                && !mArtworkLoading // uncomment when this wouldn't cause
//                                    // a zoom out / in visual glitch
                && (mWindowHasFocus)
                && !mPaused) {
            shouldBeOpened = true;
        }

        if (currentlyOpened != shouldBeOpened) {
            EventBus.getDefault().postSticky(new ArtDetailOpenedClosedEvent(shouldBeOpened));
        }
    }

    private void setupArtDetailModeUi() {
        mChromeContainerView = findViewById(R.id.chrome_container);
        mStatusBarScrimView = findViewById(R.id.statusbar_scrim);

        mChromeContainerView.setBackground(ScrimUtil.makeCubicGradientScrimDrawable(
                0xaa000000, 8, Gravity.TOP));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mStatusBarScrimView.setVisibility(View.GONE);
            mStatusBarScrimView = null;
        } else {
            mStatusBarScrimView.setBackground(ScrimUtil.makeCubicGradientScrimDrawable(
                    0x44000000, 8, Gravity.TOP));
        }

        mMetadataView = findViewById(R.id.metadata);

        mTitleView = (TextView) findViewById(R.id.title);
        mBylineView = (TextView) findViewById(R.id.byline);

        mPanScaleProxyView = (PanScaleProxyView) findViewById(R.id.pan_scale_proxy);
    }

    private void updateArtDetailUi() {
        mTitleView.setText(R.string.app_description);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
    }

    private void showHideChrome(boolean show) {
        int flags = show ? 0 : View.SYSTEM_UI_FLAG_LOW_PROFILE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (!show) {
                flags |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
            }
        }
        mContainerView.setSystemUiVisibility(flags);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mWindowHasFocus = hasFocus;
        maybeUpdateArtDetailOpenedClosed();
    }

    public void onEventMainThread(WallpaperSizeChangedEvent wsce) {
        if (wsce.getHeight() > 0) {
            mWallpaperAspectRatio = wsce.getWidth() * 1f / wsce.getHeight();
        } else {
            mWallpaperAspectRatio = mPanScaleProxyView.getWidth()
                    * 1f / mPanScaleProxyView.getHeight();
        }
        resetProxyViewport();
    }

    public void onEventMainThread(ArtworkSizeChangedEvent ase) {
        mArtworkAspectRatio = ase.getWidth() * 1f / ase.getHeight();
        resetProxyViewport();
    }

    private void resetProxyViewport() {
        if (mWallpaperAspectRatio == 0 || mArtworkAspectRatio == 0) {
            return;
        }

        mDeferResetViewport = false;
        SwitchingPhotosStateChangedEvent spe = EventBus.getDefault()
                .getStickyEvent(SwitchingPhotosStateChangedEvent.class);
        if (spe != null && spe.isSwitchingPhotos()) {
            mDeferResetViewport = true;
            return;
        }

        if (mPanScaleProxyView != null) {
            mPanScaleProxyView.setRelativeAspectRatio(mArtworkAspectRatio / mWallpaperAspectRatio);
        }
    }

    public void onEventMainThread(SwitchingPhotosStateChangedEvent spe) {
        mPanScaleProxyView.enablePanScale(!spe.isSwitchingPhotos());
        // Process deferred artwork size change when done switching
        if (!spe.isSwitchingPhotos() && mDeferResetViewport) {
            resetProxyViewport();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPaused = true;
        maybeUpdateArtDetailOpenedClosed();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPaused = false;

        // update intro mode UI to latest wallpaper active state
        WallpaperActiveStateChangedEvent e = EventBus.getDefault()
                .getStickyEvent(WallpaperActiveStateChangedEvent.class);
        if (e != null) {
            onEventMainThread(e);
        } else {
            onEventMainThread(new WallpaperActiveStateChangedEvent(false));
        }

        updateUiMode();
        mChromeContainerView.setVisibility((mUiMode == UI_MODE_ART_DETAIL)
                ? View.VISIBLE : View.GONE);
        if (mStatusBarScrimView != null) {
            mStatusBarScrimView.setVisibility((mUiMode == UI_MODE_ART_DETAIL)
                    ? View.VISIBLE : View.GONE);
        }

        // Note: normally should use window animations for this, but there's a bug
        // on Samsung devices where the wallpaper is animated along with the window for
        // windows showing the wallpaper (the wallpaper _should_ be static, not part of
        // the animation).
        View decorView = getWindow().getDecorView();
        decorView.setAlpha(0f);
        decorView.animate().cancel();
        decorView.animate()
                .setStartDelay(500)
                .alpha(1f)
                .setDuration(300);

        maybeUpdateArtDetailOpenedClosed();
    }

    public void onEventMainThread(final WallpaperActiveStateChangedEvent e) {
        if (mPaused) {
            return;
        }

        mWallpaperActive = e.isActive();
        updateArtDetailUi();
        updateUiMode();
    }


}
