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

package com.google.android.apps.muzei.render;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.google.android.apps.muzei.util.LogUtil;

import java.io.IOException;

import static com.google.android.apps.muzei.util.LogUtil.LOGE;

public class LocalRenderController extends RenderController {
    private static final String TAG = LogUtil.makeLogTag(LocalRenderController.class);

    private static final String PREF_LOCAL_IMAGE_INDEX = "local_image_index";

    static final String LOCAL_IMAGES[] = {
            "kepler-01.jpg",
            "kepler-02.jpg",
            "kepler-03.jpg",
    };

    private final Handler mHandler = new Handler();

    public LocalRenderController(Context context, MuzeiBlurRenderer renderer,
            Callbacks callbacks) {
        super(context, renderer, callbacks);
    }

    @Override
    public void destroy() {
        super.destroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected BitmapRegionLoader openDownloadedCurrentArtwork(boolean forceReload) {
        try {
            final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
            int index = sp.getInt(PREF_LOCAL_IMAGE_INDEX, 0);
            int nextIndex = index+1;
            if (nextIndex >= LOCAL_IMAGES.length) {
                nextIndex = 0;
            }
            sp.edit().putInt(PREF_LOCAL_IMAGE_INDEX, nextIndex).apply();

            return BitmapRegionLoader.newInstance(mContext.getAssets().open(LOCAL_IMAGES[index]));
        } catch (IOException e) {
            LOGE(TAG, "Error opening demo image.", e);
            return null;
        }
    }
}
