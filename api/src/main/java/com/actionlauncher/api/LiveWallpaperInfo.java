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

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.actionlauncher.api.actionpalette.ActionPalette;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A serializable object representing a single artwork produced by a {@link LiveWallpaperSource}.
 *
 * <p> To create an instance, use the {@link LiveWallpaperInfo.Builder} class.
 */
public class LiveWallpaperInfo {
    private static final String TAG = "Action3-api";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_PALETTE_VIBRANT_RGB = "paletteVibrant";
    private static final String KEY_PALETTE_LIGHT_VIBRANT_RGB = "paletteLightVibrant";
    private static final String KEY_PALETTE_DARK_VIBRANT_RGB = "paletteDarkVibrant";
    private static final String KEY_PALETTE_MUTED_RGB = "paletteMuted";
    private static final String KEY_PALETTE_LIGHT_MUTED_RGB = "paletteLightMuted";
    private static final String KEY_PALETTE_DARK_MUTED_RGB = "paletteDarkMuted";

    private String mToken;
    private Integer mPaletteVibrantRgb;
    private Integer mPaletteLightVibrantRgb;
    private Integer mPaletteDarkVibrantRgb;
    private Integer mPaletteMutedRgb;
    private Integer mPaletteLightMutedRgb;
    private Integer mPaletteDarkMutedRgb;

    private LiveWallpaperInfo() {
    }

    /**
     * Returns the artwork's opaque application-specific identifier, or null if it doesn't have
     * one.
     *
     * @see LiveWallpaperInfo.Builder#token(String)
     */
    public String getToken() {
        return mToken;
    }

    public Integer getPaletteVibrantRgb() {
        return mPaletteVibrantRgb;
    }
    public Integer getPaletteLightVibrantRgb() {
        return mPaletteLightVibrantRgb;
    }
    public Integer getPaletteDarkVibrantRgb() {
        return mPaletteDarkVibrantRgb;
    }
    public Integer getPaletteMutedRgb() {
        return mPaletteMutedRgb;
    }
    public Integer getPaletteLightMutedRgb() {
        return mPaletteLightMutedRgb;
    }
    public Integer getPaletteDarkMutedRgb() {
        return mPaletteDarkMutedRgb;
    }


    /**
     * A <a href="http://en.wikipedia.org/wiki/Builder_pattern">builder</a>-style, <a
     * href="http://en.wikipedia.org/wiki/Fluent_interface">fluent interface</a> for creating {@link
     * LiveWallpaperInfo} objects.
     */
    public static class Builder {
        private LiveWallpaperInfo mLiveWallpaperInfo;

        public Builder() {
            mLiveWallpaperInfo = new LiveWallpaperInfo();
        }

        /**
         * Sets the artwork's opaque application-specific identifier.
         */
        public Builder token(String token) {
            mLiveWallpaperInfo.mToken = token;
            return this;
        }

        public Builder palette(ActionPalette actionPalette) {
            if (actionPalette != null) {
                paletteVibrantRgb(actionPalette.getVibrantSwatch() != null
                        ? actionPalette.getVibrantSwatch().getRgb() : null);
                paletteLightVibrantRgb(actionPalette.getLightVibrantSwatch() != null
                        ? actionPalette.getLightVibrantSwatch().getRgb() : null);
                paletteDarkVibrantRgb(actionPalette.getDarkVibrantSwatch() != null
                        ? actionPalette.getDarkVibrantSwatch().getRgb() : null);
                paletteMutedRgb(actionPalette.getMutedSwatch() != null
                        ? actionPalette.getMutedSwatch().getRgb() : null);
                paletteLightMutedRgb(actionPalette.getLightMutedSwatch() != null
                        ? actionPalette.getLightMutedSwatch().getRgb() : null);
                paletteDarkMutedRgb(actionPalette.getDarkMutedSwatch() != null
                        ? actionPalette.getDarkMutedSwatch().getRgb() : null);
            }
            return this;
        }

        public Builder paletteVibrantRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteVibrantRgb = color;
            return this;
        }
        public Builder paletteVibrantRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteVibrantRgb = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteLightVibrantRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteLightVibrantRgb = color;
            return this;
        }
        public Builder paletteLightVibrantRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteLightVibrantRgb = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteDarkVibrantRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteDarkVibrantRgb = color;
            return this;
        }
        public Builder paletteDarkVibrantRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteDarkVibrantRgb = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteMutedRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteMutedRgb = color;
            return this;
        }
        public Builder paletteMutedRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteMutedRgb = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteLightMutedRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteLightMutedRgb = color;
            return this;
        }
        public Builder paletteLightMutedRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteLightMutedRgb = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteDarkMutedRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteDarkMutedRgb = color;
            return this;
        }
        public Builder paletteDarkMutedRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteDarkMutedRgb = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }

        /**
         * Creates and returns the final LiveWallpaperInfo object. Once this method is called, it is not valid
         * to further use this {@link LiveWallpaperInfo.Builder} object.
         */
        public LiveWallpaperInfo build() {
            return mLiveWallpaperInfo;
        }
    }

    /**
     * Serializes this artwork object to a {@link Bundle} representation.
     */
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TOKEN, mToken);
        bundle.putString(KEY_PALETTE_VIBRANT_RGB, mPaletteVibrantRgb != null
                ? mPaletteVibrantRgb.toString() : null);
        bundle.putString(KEY_PALETTE_LIGHT_VIBRANT_RGB, mPaletteLightVibrantRgb != null
                ? mPaletteLightVibrantRgb.toString() : null);
        bundle.putString(KEY_PALETTE_DARK_VIBRANT_RGB, mPaletteDarkVibrantRgb != null
                ? mPaletteDarkVibrantRgb.toString() : null);
        bundle.putString(KEY_PALETTE_MUTED_RGB, mPaletteMutedRgb != null
                ? mPaletteMutedRgb.toString() : null);
        bundle.putString(KEY_PALETTE_LIGHT_MUTED_RGB, mPaletteLightMutedRgb != null
                ? mPaletteLightMutedRgb.toString() : null);
        bundle.putString(KEY_PALETTE_DARK_MUTED_RGB, mPaletteDarkMutedRgb != null
                ? mPaletteDarkMutedRgb.toString() : null);
        return bundle;
    }

    /**
     * Deserializes an artwork object from a {@link Bundle}.
     */
    public static LiveWallpaperInfo fromBundle(Bundle bundle) {
        Builder builder = new Builder()
                .token(bundle.getString(KEY_TOKEN))
                .paletteVibrantRgb(bundle.getString(KEY_PALETTE_VIBRANT_RGB, null))
                .paletteLightVibrantRgb(bundle.getString(KEY_PALETTE_LIGHT_VIBRANT_RGB, null))
                .paletteDarkVibrantRgb(bundle.getString(KEY_PALETTE_DARK_VIBRANT_RGB, null))
                .paletteMutedRgb(bundle.getString(KEY_PALETTE_MUTED_RGB, null))
                .paletteLightMutedRgb(bundle.getString(KEY_PALETTE_LIGHT_MUTED_RGB, null))
                .paletteDarkMutedRgb(bundle.getString(KEY_PALETTE_DARK_MUTED_RGB, null));

        LiveWallpaperInfo result = builder.build();
        Log.d(TAG, "LiveWallpaperInfo.fromBundle() " + result.toString());
        return result;
    }

    /**
     * Serializes this artwork object to a {@link JSONObject} representation.
     */
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_TOKEN, mToken);
        jsonObject.put(KEY_PALETTE_VIBRANT_RGB, mPaletteVibrantRgb != null ?
                Integer.toString(mPaletteVibrantRgb) : null);
        jsonObject.put(KEY_PALETTE_LIGHT_VIBRANT_RGB, mPaletteLightVibrantRgb != null ?
                Integer.toString(mPaletteLightVibrantRgb) : null);
        jsonObject.put(KEY_PALETTE_DARK_VIBRANT_RGB, mPaletteDarkVibrantRgb != null ?
                Integer.toString(mPaletteDarkVibrantRgb) : null);
        jsonObject.put(KEY_PALETTE_MUTED_RGB, mPaletteMutedRgb != null ?
                Integer.toString(mPaletteMutedRgb) : null);
        jsonObject.put(KEY_PALETTE_LIGHT_MUTED_RGB, mPaletteLightMutedRgb != null ?
                Integer.toString(mPaletteLightMutedRgb) : null);
        jsonObject.put(KEY_PALETTE_MUTED_RGB, mPaletteDarkMutedRgb != null ?
                Integer.toString(mPaletteDarkMutedRgb) : null);
        return jsonObject;
    }

    /**
     * Deserializes an artwork object from a {@link JSONObject}.
     */
    public static LiveWallpaperInfo fromJson(JSONObject jsonObject) throws JSONException {
        return new Builder()
                .token(jsonObject.optString(KEY_TOKEN))
                .paletteVibrantRgb(paletteValue(jsonObject, KEY_PALETTE_VIBRANT_RGB))
                .paletteLightVibrantRgb(paletteValue(jsonObject, KEY_PALETTE_LIGHT_VIBRANT_RGB))
                .paletteDarkVibrantRgb(paletteValue(jsonObject, KEY_PALETTE_DARK_VIBRANT_RGB))
                .paletteMutedRgb(paletteValue(jsonObject, KEY_PALETTE_MUTED_RGB))
                .paletteLightMutedRgb(paletteValue(jsonObject, KEY_PALETTE_LIGHT_MUTED_RGB))
                .paletteDarkMutedRgb(paletteValue(jsonObject, KEY_PALETTE_DARK_MUTED_RGB))
                .build();
    }

    private static Integer paletteValue(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.has(key)) {
            String value = jsonObject.getString(key);
            if (value != null) {
                return Integer.valueOf(value);
            }
        }
        return null;
    }

    public String toString() {
        return "v:" + mPaletteVibrantRgb + ", lv:" + mPaletteLightVibrantRgb
                + ", dv:" + mPaletteDarkVibrantRgb + ", m:" + mPaletteMutedRgb
                + ", lm:" + mPaletteLightMutedRgb + ", dm:" + mPaletteDarkMutedRgb
                + ", token:" + mToken;
    }
}
