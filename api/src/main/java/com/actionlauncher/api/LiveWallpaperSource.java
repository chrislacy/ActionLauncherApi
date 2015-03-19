/*
 * Copyright 2015 Chris Lacy
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

package com.actionlauncher.api;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.actionlauncher.api.actionpalette.ActionPalette;
import com.actionlauncher.api.internal.ProtocolConstants;
import com.actionlauncher.api.internal.SourceState;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.actionlauncher.api.internal.ProtocolConstants.ACTION_FETCH_PALETTE;
import static com.actionlauncher.api.internal.ProtocolConstants.ACTION_PUBLISH_STATE;
import static com.actionlauncher.api.internal.ProtocolConstants.ACTION_SUBSCRIBE;
import static com.actionlauncher.api.internal.ProtocolConstants.EXTRA_LIVE_WALLPAPER_INFO;
import static com.actionlauncher.api.internal.ProtocolConstants.EXTRA_STATE;
import static com.actionlauncher.api.internal.ProtocolConstants.EXTRA_SUBSCRIBER_COMPONENT;
import static com.actionlauncher.api.internal.ProtocolConstants.EXTRA_TOKEN;

/**
 *
 */
public class LiveWallpaperSource extends IntentService {
    private static final String TAG = "Action3-api";
    private static boolean LOGGING_ENABLED = false;

    /**
     * The {@link Intent} action representing an Action Launcher live wallpaper source. This service
     * should declare an <code>&lt;intent-filter&gt;</code> for this action in order to register with
     * Action Launcher 3.
     */
    public static final String ACTION_WALLPAPER_SOURCE
            = "com.actionlauncher.api.action.LiveWallpaperSource";

    private static final String PREF_STATE = "state";
    private static final String PREF_SUBSCRIPTIONS = "subscriptions";

    private static final int MSG_PUBLISH_CURRENT_STATE = 1;

    private SharedPreferences mSharedPrefs;

    private String mName = "<not_set>";

    private Map<ComponentName, String> mSubscriptions;
    private SourceState mCurrentState;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_PUBLISH_CURRENT_STATE) {
                publishCurrentState();
                saveState();
            }
        }
    };

    public LiveWallpaperSource() {
        this("<not_set>");  // mName is set with the package name in onCreate()
    }

    /**
     * Remember to call this constructor from an empty constructor!
     *
     * @param name Should be an ID-style name for your source, usually just the class name. This is
     *             not user-visible and is only used for {@linkplain #getSharedPreferences()
     *             storing preferences} and in system log output.
     */
    public LiveWallpaperSource(String name) {
        super(name);
        mName = name;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPrefs = getSharedPreferences();
        loadSubscriptions();
        loadState();
    }

    /**
     * Method called before a new subscriber is added that determines whether the subscription is
     * allowed or not. The default behavior is to allow all subscriptions.
     *
     * @return true if the subscription should be allowed, false if it should be denied.
     */
    protected boolean onAllowSubscription(ComponentName subscriber) {
        return true;
    }

    /**
     * Lifecycle method called when a new subscriber is added. Sources generally don't need to
     * override this. For more details on the source lifecycle, see the discussion in the
     * {@link LiveWallpaperSource} reference.
     */
    protected void onSubscriberAdded(ComponentName subscriber) {
    }

    /**
     * Lifecycle method called when a subscriber is removed. Sources generally don't need to
     * override this. For more details on the source lifecycle, see the discussion in the
     * {@link LiveWallpaperSource} reference.
     */
    protected void onSubscriberRemoved(ComponentName subscriber) {
    }

    /**
     * Lifecycle method called when the first subscriber is added. This will be called before
     * {@link #onSubscriberAdded(ComponentName)}. Sources generally don't need to override this.
     * For more details on the source lifecycle, see the discussion in the {@link LiveWallpaperSource}
     * reference.
     */
    protected void onEnabled() {
    }

    /**
     * Lifecycle method called when the last subscriber is removed. This will be called after
     * {@link #onSubscriberRemoved(ComponentName)}. Sources generally don't need to override this.
     * For more details on the source lifecycle, see the discussion in the {@link LiveWallpaperSource}
     * reference.
     */
    protected void onDisabled() {
    }

    /**
     * Publishes the provided {@link LiveWallpaperInfo} object. This will be sent to all current subscribers
     * and to all future subscribers, until a new item is published.
     *
     * @param liveWallpaperInfo the LiveWallpaperInfo to publish
     */
    protected final void publishLiveWallpaperInfo(LiveWallpaperInfo liveWallpaperInfo) {
        mCurrentState.setCurrentLiveWallpaperInfo(liveWallpaperInfo);
        mHandler.removeMessages(MSG_PUBLISH_CURRENT_STATE);
        mHandler.sendEmptyMessage(MSG_PUBLISH_CURRENT_STATE);
    }

    /**
     * Returns the most recently {@linkplain #publishLiveWallpaperInfo(LiveWallpaperInfo) published} item, or null
     * if none has been published.
     *
     * @return the current LiveWallpaperInfo (if one exists).
     */
    protected final LiveWallpaperInfo getCurrentLiveWallpaperInfo() {
        return mCurrentState != null ? mCurrentState.getCurrentLiveWallpaperInfo() : null;
    }

    /**
     * Returns true if this source is enabled; that is, if there is at least one active subscriber.
     *
     * @see #onEnabled()
     * @see #onDisabled()
     *
     * @return true if enabled.
     */
    protected synchronized final boolean isEnabled() {
        return mSubscriptions.size() > 0;
    }

    /**
     * Convenience method for accessing preferences specific to the source (with the given name
     * within this package. The source name must be the one provided in the
     * {@link #LiveWallpaperSource(String)} constructor. This static method is useful for exposing source
     * preferences to other application components such as the source settings activity.
     *
     * @param context    The context; can be an application context.
     * @param sourceName The source name, provided in the {@link #LiveWallpaperSource(String)}
     *                   constructor.
     */
    protected static SharedPreferences getSharedPreferences(Context context, String sourceName) {
        return context.getSharedPreferences("action3source_" + sourceName, 0);
    }

    /**
     * Convenience method for accessing preferences specific to the source.
     */
    protected final SharedPreferences getSharedPreferences() {
        return getSharedPreferences(this, mName);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        String action = intent.getAction();
        LOGD("LiveWallpaperSource.onHandleIntent() - action:" + action + ", id:" + mName);
        // TODO: permissions?
        if (ACTION_SUBSCRIBE.equals(action)) {
            processSubscribe(
                    (ComponentName) intent.getParcelableExtra(EXTRA_SUBSCRIBER_COMPONENT),
                    intent.getStringExtra(EXTRA_TOKEN));
        } else if (ACTION_FETCH_PALETTE.equals(action)) {
            publishCurrentPalette();
        } else if (action.equals(ProtocolConstants.ACTION_PUBLISH_STATE)) {
            boolean wallpaperInfoSet = false;
            if (intent.hasExtra(EXTRA_LIVE_WALLPAPER_INFO)) {
                Bundle bundle = intent.getExtras().getBundle(EXTRA_LIVE_WALLPAPER_INFO);
                if (bundle != null) {
                    LiveWallpaperInfo info = LiveWallpaperInfo.fromBundle(bundle);
                    mCurrentState.setCurrentLiveWallpaperInfo(info);
                    LOGD("LiveWallpaperInfo.fromBundle():" + (info != null ? info.toString() : null));
                    wallpaperInfoSet = true;
                }
            }
            if (!wallpaperInfoSet) {
                mCurrentState.setCurrentLiveWallpaperInfo(null);
            }
            publishCurrentPalette();
        }
    }

    public void publishCurrentPalette() {
        LOGD("publishCurrentPalette()");
        mHandler.removeMessages(MSG_PUBLISH_CURRENT_STATE);
        mHandler.sendEmptyMessage(MSG_PUBLISH_CURRENT_STATE);
    }

    private synchronized void processSubscribe(ComponentName subscriber, String token) {
        if (subscriber == null) {
            LOGD("No subscriber given.");
            return;
        }

        String oldToken = mSubscriptions.get(subscriber);
        if (TextUtils.isEmpty(token)) {
            if (oldToken == null) {
                return;
            }

            // Unsubscribing
            mSubscriptions.remove(subscriber);
            processAndDispatchSubscriberRemoved(subscriber);

        } else {
            // Subscribing
            if (!TextUtils.isEmpty(oldToken)) {
                // Was previously subscribed, treat this as a unsubscribe + subscribe
                mSubscriptions.remove(subscriber);
                processAndDispatchSubscriberRemoved(subscriber);
            }

            if (!onAllowSubscription(subscriber)) {
                return;
            }

            mSubscriptions.put(subscriber, token);
            processAndDispatchSubscriberAdded(subscriber);
        }

        saveSubscriptions();
    }

    private synchronized void processAndDispatchSubscriberAdded(ComponentName subscriber) {
        // Trigger callbacks
        if (mSubscriptions.size() == 1) {
            onEnabled();
        }

        onSubscriberAdded(subscriber);

        LOGD("processAndDispatchSubscriberAdded():" + subscriber
                + ", mSubscriptions.size():" + mSubscriptions.size());

        // If there's no LiveWallpaperInfo, trigger initial update
        //if (mSubscriptions.size() == 1
        //        && mLiveWallpaperInfo == null) {
        //    // TODO: Broadcast that we need a palette
        //}

        // Immediately publish current state to subscriber
        publishCurrentState(subscriber);
    }

    private synchronized void processAndDispatchSubscriberRemoved(ComponentName subscriber) {
        // Trigger callbacks
        onSubscriberRemoved(subscriber);
        if (mSubscriptions.size() == 0) {
            onDisabled();
        }
        LOGD("processAndDispatchSubscriberRemoved():" + subscriber
                + ", mSubscriptions.size():" + mSubscriptions.size());
    }

    private synchronized void publishCurrentState() {
        for (ComponentName subscription : mSubscriptions.keySet()) {
            publishCurrentState(subscription);
        }
    }

    private synchronized void publishCurrentState(final ComponentName subscriber) {
        String token = mSubscriptions.get(subscriber);
        if (TextUtils.isEmpty(token)) {
            LOGD("Not active, canceling update, id=" + mName);
            return;
        }

        // Publish update
        Intent intent = new Intent(ACTION_PUBLISH_STATE)
                .setComponent(subscriber)
                .putExtra(EXTRA_TOKEN, token)
                .putExtra(EXTRA_STATE, (mCurrentState != null) ? mCurrentState.toBundle() : null);
        try {
            ComponentName returnedSubscriber = startService(intent);
            if (returnedSubscriber == null) {
                LOGE("Update wasn't published because subscriber no longer exists"
                        + ", id=" + mName);
                // Unsubscribe the now-defunct subscriber
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        processSubscribe(subscriber, null);
                    }
                });
            } else {
                LOGD("publishCurrentState(): successfully started service "
                        + returnedSubscriber.toString() + " with intent " + intent.toString());
            }
        } catch (SecurityException e) {
            LOGE("Couldn't publish update, id=" + mName, e);
        }
    }

    private synchronized void loadSubscriptions() {
        mSubscriptions = new HashMap<ComponentName, String>();
        Set<String> serializedSubscriptions = mSharedPrefs.getStringSet(PREF_SUBSCRIPTIONS, null);
        if (serializedSubscriptions != null) {
            for (String serializedSubscription : serializedSubscriptions) {
                String[] arr = serializedSubscription.split("\\|", 2);
                ComponentName subscriber = ComponentName.unflattenFromString(arr[0]);
                String token = arr[1];
                mSubscriptions.put(subscriber, token);
            }
        }
    }

    private synchronized void saveSubscriptions() {
        Set<String> serializedSubscriptions = new HashSet<String>();
        for (ComponentName subscriber : mSubscriptions.keySet()) {
            serializedSubscriptions.add(subscriber.flattenToShortString() + "|"
                    + mSubscriptions.get(subscriber));
        }
        mSharedPrefs.edit().putStringSet(PREF_SUBSCRIPTIONS, serializedSubscriptions).commit();
    }

    private void loadState() {
        String stateString = mSharedPrefs.getString(PREF_STATE, null);
        if (stateString != null) {
            try {
                mCurrentState = SourceState.fromJson((JSONObject)
                        new JSONTokener(stateString).nextValue());
            } catch (JSONException e) {
                LOGE("Couldn't deserialize current state, id=" + mName, e);
            }
        } else {
            mCurrentState = new SourceState();
        }
    }

    private void saveState() {
        try {
            String state = mCurrentState.toJson().toString();
            mSharedPrefs.edit().putString(PREF_STATE, state).commit();
            LOGD("saveState() - " + state);
        } catch (JSONException e) {
            LOGE("Couldn't serialize current state, id=" + mName, e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Static helper function that generates an ActionPalette from the supplied Bitmap, and then
     * begins the process of transferring this palette information to Action Launcher.
     *
     * @param context (required) A Context. Used to call Context.startService().
     * @param bitmap (required) A Bitmap of the current wallpaper. Note that the Palette system
     *               automatically resizes this before processing so you don't have to.
     *
     * @return true if a palette was generated and the LiveWallpaperSource Service was started.
     */
    public static boolean setBitmapSynchronous(Context context, Bitmap bitmap) {
        LOGD("setBitmapSynchronous()");

        ActionPalette actionPalette;
        try {
            actionPalette = ActionPalette.from(bitmap).generate();
        } catch (OutOfMemoryError ex) {
            LOGE("OutOfMemoryError fetching palette info. Consider passing a smaller sized Bitmap as input", ex);
            return false;
        }

        Intent serviceIntent = new Intent(context, LiveWallpaperSource.class)
                .setAction(ProtocolConstants.ACTION_PUBLISH_STATE)
                .putExtra(EXTRA_LIVE_WALLPAPER_INFO, (actionPalette == null) ? null :
                        new LiveWallpaperInfo.Builder()
                                .palette(actionPalette)
                                .build()
                                .toBundle())
                .putExtra("dummy", System.currentTimeMillis());
        try {
            ComponentName result = context.startService(serviceIntent);
            LOGD("startService() result:" + result);
            return result != null;
        } catch (Exception ex) {
            LOGE("Error starting service with intent:" + serviceIntent
                    + "\n" + ex.getLocalizedMessage(), ex);
        }
        return false;
    }

    /**
     * Static helper function that enables logs
     *
     * @param enabled whether to output logs
     */
    public static void enableLogging(boolean enabled) {
        LOGGING_ENABLED = enabled;
    }

    static void LOGD(String msg) {
        LOGD(msg, null);
    }

    static void LOGD(String msg, Throwable throwable) {
        if (LOGGING_ENABLED) {
            Log.d(TAG, msg, throwable);
        }
    }

    static void LOGE(String msg) {
        LOGE(msg, null);
    }

    static void LOGE(String msg, Throwable throwable) {
        if (LOGGING_ENABLED) {
            Log.e(TAG, msg, throwable);
        }
    }

}
