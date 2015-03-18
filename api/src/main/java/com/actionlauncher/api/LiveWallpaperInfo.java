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
 * A serializable object representing a single LiveWallpaperInfo item produced by
 * a {@link LiveWallpaperSource}.
 *
 * <p> To create an instance, use the {@link LiveWallpaperInfo.Builder} class.
 */
public class LiveWallpaperInfo {
    private static final String TAG = "Action3-api";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_PALETTE_VIBRANT_RGB = "paletteVibrant";
    private static final String KEY_PALETTE_VIBRANT_TITLE_TEXT = "paletteVibrantTitleText";
    private static final String KEY_PALETTE_VIBRANT_BODY_TEXT = "paletteVibrantBodyText";
    private static final String KEY_PALETTE_LIGHT_VIBRANT_RGB = "paletteLightVibrant";
    private static final String KEY_PALETTE_LIGHT_VIBRANT_TITLE_TEXT = "paletteLightVibrantTitleText";
    private static final String KEY_PALETTE_LIGHT_VIBRANT_BODY_TEXT = "paletteLightVibrantBodyText";
    private static final String KEY_PALETTE_DARK_VIBRANT_RGB = "paletteDarkVibrant";
    private static final String KEY_PALETTE_DARK_VIBRANT_TITLE_TEXT = "paletteDarkVibrantTitleText";
    private static final String KEY_PALETTE_DARK_VIBRANT_BODY_TEXT = "paletteDarkVibrantBodyText";
    private static final String KEY_PALETTE_MUTED_RGB = "paletteMuted";
    private static final String KEY_PALETTE_MUTED_TITLE_TEXT = "paletteMutedTitleText";
    private static final String KEY_PALETTE_MUTED_BODY_TEXT = "paletteMutedBodyText";
    private static final String KEY_PALETTE_LIGHT_MUTED_RGB = "paletteLightMuted";
    private static final String KEY_PALETTE_LIGHT_MUTED_TITLE_TEXT = "paletteLightMutedTitleText";
    private static final String KEY_PALETTE_LIGHT_MUTED_BODY_TEXT = "paletteLightMutedBodyText";
    private static final String KEY_PALETTE_DARK_MUTED_RGB = "paletteDarkMuted";
    private static final String KEY_PALETTE_DARK_MUTED_TITLE_TEXT = "paletteDarkMutedTitleText";
    private static final String KEY_PALETTE_DARK_MUTED_BODY_TEXT = "paletteDarkMutedBodyText";

    private String mToken;
    private Integer mPaletteVibrantRgb;
    private Integer mPaletteVibrantTitleTextColor;
    private Integer mPaletteVibrantBodyTextColor;
    private Integer mPaletteLightVibrantRgb;
    private Integer mPaletteLightVibrantTitleTextColor;
    private Integer mPaletteLightVibrantBodyTextColor;
    private Integer mPaletteDarkVibrantRgb;
    private Integer mPaletteDarkVibrantTitleTextColor;
    private Integer mPaletteDarkVibrantBodyTextColor;
    private Integer mPaletteMutedRgb;
    private Integer mPaletteMutedTitleTextColor;
    private Integer mPaletteMutedBodyTextColor;
    private Integer mPaletteLightMutedRgb;
    private Integer mPaletteLightMutedTitleTextColor;
    private Integer mPaletteLightMutedBodyTextColor;
    private Integer mPaletteDarkMutedRgb;
    private Integer mPaletteDarkMutedTitleTextColor;
    private Integer mPaletteDarkMutedBodyTextColor;

    private LiveWallpaperInfo() {
    }

    /**
     * Returns the LiveWallpaperInfo's opaque application-specific identifier, or null if it doesn't have
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
    public Integer getPaletteVibrantTitleTextColor() {
        return mPaletteVibrantTitleTextColor;
    }
    public Integer getPaletteVibrantBodyTextColor() {
        return mPaletteVibrantBodyTextColor;
    }

    public Integer getPaletteLightVibrantRgb() {
        return mPaletteLightVibrantRgb;
    }
    public Integer getPaletteLightVibrantTitleTextColor() {
        return mPaletteLightVibrantTitleTextColor;
    }
    public Integer getPaletteLightVibrantBodyTextColor() {
        return mPaletteLightVibrantBodyTextColor;
    }

    public Integer getPaletteDarkVibrantRgb() {
        return mPaletteDarkVibrantRgb;
    }
    public Integer getPaletteDarkVibrantTitleTextColor() {
        return mPaletteDarkVibrantTitleTextColor;
    }
    public Integer getPaletteDarkVibrantBodyTextColor() {
        return mPaletteDarkVibrantBodyTextColor;
    }

    public Integer getPaletteMutedRgb() {
        return mPaletteMutedRgb;
    }
    public Integer getPaletteMutedTitleTextColor() {
        return mPaletteMutedTitleTextColor;
    }
    public Integer getPaletteMutedBodyTextColor() {
        return mPaletteMutedBodyTextColor;
    }

    public Integer getPaletteLightMutedRgb() {
        return mPaletteLightMutedRgb;
    }
    public Integer getPaletteLightMutedTitleTextColor() {
        return mPaletteLightMutedTitleTextColor;
    }
    public Integer getPaletteLightMutedBodyTextColor() {
        return mPaletteLightMutedBodyTextColor;
    }

    public Integer getPaletteDarkMutedRgb() {
        return mPaletteDarkMutedRgb;
    }
    public Integer getPaletteDarkMutedTitleTextColor() {
        return mPaletteDarkMutedTitleTextColor;
    }
    public Integer getPaletteDarkMutedBodyTextColor() {
        return mPaletteDarkMutedBodyTextColor;
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
         * Sets the LiveWallpaperInfo's opaque application-specific identifier.
         */
        public Builder token(String token) {
            mLiveWallpaperInfo.mToken = token;
            return this;
        }

        public Builder palette(ActionPalette actionPalette) {
            if (actionPalette != null) {
                paletteVibrant(actionPalette.getVibrantSwatch());
                paletteLightVibrant(actionPalette.getLightVibrantSwatch());
                paletteDarkVibrant(actionPalette.getDarkVibrantSwatch());
                paletteMuted(actionPalette.getMutedSwatch());
                paletteLightMuted(actionPalette.getLightMutedSwatch());
                paletteDarkMuted(actionPalette.getDarkMutedSwatch());
            }
            return this;
        }

        public Builder paletteVibrant(ActionPalette.Swatch swatch) {
            if (swatch != null) {
                paletteVibrantRgb(swatch.getRgb());
                paletteVibrantBodyTextRgb(swatch.getBodyTextColor());
                paletteVibrantTitleTextRgb(swatch.getTitleTextColor());
            } else {
                paletteVibrantRgb((String)null);
                paletteVibrantBodyTextRgb((String)null);
                paletteVibrantTitleTextRgb((String)null);
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
        public Builder paletteVibrantTitleTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteVibrantTitleTextColor = color;
            return this;
        }
        public Builder paletteVibrantTitleTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteVibrantTitleTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteVibrantBodyTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteVibrantBodyTextColor = color;
            return this;
        }
        public Builder paletteVibrantBodyTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteVibrantBodyTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }

        public Builder paletteLightVibrant(ActionPalette.Swatch swatch) {
            if (swatch != null) {
                paletteLightVibrantRgb(swatch.getRgb());
                paletteLightVibrantBodyTextRgb(swatch.getBodyTextColor());
                paletteLightVibrantTitleTextRgb(swatch.getTitleTextColor());
            } else {
                paletteLightVibrantRgb((String)null);
                paletteLightVibrantBodyTextRgb((String)null);
                paletteLightVibrantTitleTextRgb((String)null);
            }
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
        public Builder paletteLightVibrantTitleTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteLightVibrantTitleTextColor = color;
            return this;
        }
        public Builder paletteLightVibrantTitleTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteLightVibrantTitleTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteLightVibrantBodyTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteLightVibrantBodyTextColor = color;
            return this;
        }
        public Builder paletteLightVibrantBodyTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteLightVibrantBodyTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }

        public Builder paletteDarkVibrant(ActionPalette.Swatch swatch) {
            if (swatch != null) {
                paletteDarkVibrantRgb(swatch.getRgb());
                paletteDarkVibrantBodyTextRgb(swatch.getBodyTextColor());
                paletteDarkVibrantTitleTextRgb(swatch.getTitleTextColor());
            } else {
                paletteDarkVibrantRgb((String)null);
                paletteDarkVibrantBodyTextRgb((String)null);
                paletteDarkVibrantTitleTextRgb((String)null);
            }
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
        public Builder paletteDarkVibrantTitleTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteDarkVibrantTitleTextColor = color;
            return this;
        }
        public Builder paletteDarkVibrantTitleTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteDarkVibrantTitleTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteDarkVibrantBodyTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteDarkVibrantBodyTextColor = color;
            return this;
        }
        public Builder paletteDarkVibrantBodyTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteDarkVibrantBodyTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }

        public Builder paletteMuted(ActionPalette.Swatch swatch) {
            if (swatch != null) {
                paletteMutedRgb(swatch.getRgb());
                paletteMutedBodyTextRgb(swatch.getBodyTextColor());
                paletteMutedTitleTextRgb(swatch.getTitleTextColor());
            } else {
                paletteMutedRgb((String)null);
                paletteMutedBodyTextRgb((String)null);
                paletteMutedTitleTextRgb((String)null);
            }
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
        public Builder paletteMutedTitleTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteMutedTitleTextColor = color;
            return this;
        }
        public Builder paletteMutedTitleTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteMutedTitleTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteMutedBodyTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteMutedBodyTextColor = color;
            return this;
        }
        public Builder paletteMutedBodyTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteMutedBodyTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }

        public Builder paletteLightMuted(ActionPalette.Swatch swatch) {
            if (swatch != null) {
                paletteLightMutedRgb(swatch.getRgb());
                paletteLightMutedBodyTextRgb(swatch.getBodyTextColor());
                paletteLightMutedTitleTextRgb(swatch.getTitleTextColor());
            } else {
                paletteLightMutedRgb((String)null);
                paletteLightMutedBodyTextRgb((String)null);
                paletteLightMutedTitleTextRgb((String)null);
            }
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
        public Builder paletteLightMutedTitleTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteLightMutedTitleTextColor = color;
            return this;
        }
        public Builder paletteLightMutedTitleTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteLightMutedTitleTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteLightMutedBodyTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteLightMutedBodyTextColor = color;
            return this;
        }
        public Builder paletteLightMutedBodyTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteLightMutedBodyTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }

        public Builder paletteDarkMuted(ActionPalette.Swatch swatch) {
            if (swatch != null) {
                paletteDarkMutedRgb(swatch.getRgb());
                paletteDarkMutedBodyTextRgb(swatch.getBodyTextColor());
                paletteDarkMutedTitleTextRgb(swatch.getTitleTextColor());
            } else {
                paletteDarkMutedRgb((String)null);
                paletteDarkMutedBodyTextRgb((String)null);
                paletteDarkMutedTitleTextRgb((String)null);
            }
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
        public Builder paletteDarkMutedTitleTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteDarkMutedTitleTextColor = color;
            return this;
        }
        public Builder paletteDarkMutedTitleTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteDarkMutedTitleTextColor = colorAsString != null
                    ? Integer.valueOf(colorAsString) : null;
            return this;
        }
        public Builder paletteDarkMutedBodyTextRgb(Integer color) {
            mLiveWallpaperInfo.mPaletteDarkMutedBodyTextColor = color;
            return this;
        }
        public Builder paletteDarkMutedBodyTextRgb(String colorAsString) {
            mLiveWallpaperInfo.mPaletteDarkMutedBodyTextColor = colorAsString != null
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
     * Serializes this liveWallpaperInfo object to a {@link Bundle} representation.
     */
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TOKEN, mToken);
        bundle.putString(KEY_PALETTE_VIBRANT_RGB, mPaletteVibrantRgb != null
                ? mPaletteVibrantRgb.toString() : null);
        bundle.putString(KEY_PALETTE_VIBRANT_TITLE_TEXT, mPaletteVibrantTitleTextColor != null
                ? mPaletteVibrantTitleTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_VIBRANT_BODY_TEXT, mPaletteVibrantBodyTextColor != null
                ? mPaletteVibrantBodyTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_LIGHT_VIBRANT_RGB, mPaletteLightVibrantRgb != null
                ? mPaletteLightVibrantRgb.toString() : null);
        bundle.putString(KEY_PALETTE_LIGHT_VIBRANT_TITLE_TEXT, mPaletteLightVibrantTitleTextColor != null
                ? mPaletteLightVibrantTitleTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_LIGHT_VIBRANT_BODY_TEXT, mPaletteLightVibrantBodyTextColor != null
                ? mPaletteLightVibrantBodyTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_DARK_VIBRANT_RGB, mPaletteDarkVibrantRgb != null
                ? mPaletteDarkVibrantRgb.toString() : null);
        bundle.putString(KEY_PALETTE_DARK_VIBRANT_TITLE_TEXT, mPaletteDarkVibrantTitleTextColor != null
                ? mPaletteDarkVibrantTitleTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_DARK_VIBRANT_BODY_TEXT, mPaletteDarkVibrantBodyTextColor != null
                ? mPaletteDarkVibrantBodyTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_MUTED_RGB, mPaletteMutedRgb != null
                ? mPaletteMutedRgb.toString() : null);
        bundle.putString(KEY_PALETTE_MUTED_TITLE_TEXT, mPaletteMutedTitleTextColor != null
                ? mPaletteMutedTitleTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_MUTED_BODY_TEXT, mPaletteMutedBodyTextColor != null
                ? mPaletteMutedBodyTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_LIGHT_MUTED_RGB, mPaletteLightMutedRgb != null
                ? mPaletteLightMutedRgb.toString() : null);
        bundle.putString(KEY_PALETTE_LIGHT_MUTED_TITLE_TEXT, mPaletteLightMutedTitleTextColor != null
                ? mPaletteLightMutedTitleTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_LIGHT_MUTED_BODY_TEXT, mPaletteLightMutedBodyTextColor != null
                ? mPaletteLightMutedBodyTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_DARK_MUTED_RGB, mPaletteDarkMutedRgb != null
                ? mPaletteDarkMutedRgb.toString() : null);
        bundle.putString(KEY_PALETTE_DARK_MUTED_TITLE_TEXT, mPaletteDarkMutedTitleTextColor != null
                ? mPaletteDarkMutedTitleTextColor.toString() : null);
        bundle.putString(KEY_PALETTE_DARK_MUTED_BODY_TEXT, mPaletteDarkMutedBodyTextColor != null
                ? mPaletteDarkMutedBodyTextColor.toString() : null);
        return bundle;
    }

    /**
     * Deserializes an liveWallpaperInfo object from a {@link Bundle}.
     */
    public static LiveWallpaperInfo fromBundle(Bundle bundle) {
        Builder builder = new Builder()
                .token(bundle.getString(KEY_TOKEN))
                .paletteVibrantRgb(bundle.getString(KEY_PALETTE_VIBRANT_RGB, null))
                .paletteVibrantTitleTextRgb(bundle.getString(KEY_PALETTE_VIBRANT_TITLE_TEXT, null))
                .paletteVibrantBodyTextRgb(bundle.getString(KEY_PALETTE_VIBRANT_BODY_TEXT, null))
                .paletteLightVibrantRgb(bundle.getString(KEY_PALETTE_LIGHT_VIBRANT_RGB, null))
                .paletteLightVibrantTitleTextRgb(bundle.getString(KEY_PALETTE_LIGHT_VIBRANT_TITLE_TEXT, null))
                .paletteLightVibrantBodyTextRgb(bundle.getString(KEY_PALETTE_LIGHT_VIBRANT_BODY_TEXT, null))
                .paletteDarkVibrantRgb(bundle.getString(KEY_PALETTE_DARK_VIBRANT_RGB, null))
                .paletteDarkVibrantTitleTextRgb(bundle.getString(KEY_PALETTE_DARK_VIBRANT_TITLE_TEXT, null))
                .paletteDarkVibrantBodyTextRgb(bundle.getString(KEY_PALETTE_DARK_VIBRANT_BODY_TEXT, null))
                .paletteMutedRgb(bundle.getString(KEY_PALETTE_MUTED_RGB, null))
                .paletteMutedTitleTextRgb(bundle.getString(KEY_PALETTE_MUTED_TITLE_TEXT, null))
                .paletteMutedBodyTextRgb(bundle.getString(KEY_PALETTE_MUTED_BODY_TEXT, null))
                .paletteLightMutedRgb(bundle.getString(KEY_PALETTE_LIGHT_MUTED_RGB, null))
                .paletteLightMutedTitleTextRgb(bundle.getString(KEY_PALETTE_LIGHT_MUTED_TITLE_TEXT, null))
                .paletteLightMutedBodyTextRgb(bundle.getString(KEY_PALETTE_LIGHT_MUTED_BODY_TEXT, null))
                .paletteDarkMutedRgb(bundle.getString(KEY_PALETTE_DARK_MUTED_RGB, null))
                .paletteDarkMutedTitleTextRgb(bundle.getString(KEY_PALETTE_DARK_MUTED_TITLE_TEXT, null))
                .paletteDarkMutedBodyTextRgb(bundle.getString(KEY_PALETTE_DARK_MUTED_BODY_TEXT, null));

        LiveWallpaperInfo result = builder.build();
        Log.d(TAG, "LiveWallpaperInfo.fromBundle() " + result.toString());
        return result;
    }

    /**
     * Serializes this liveWallpaperInfo object to a {@link JSONObject} representation.
     */
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_TOKEN, mToken);
        jsonObject.put(KEY_PALETTE_VIBRANT_RGB, mPaletteVibrantRgb != null ?
                Integer.toString(mPaletteVibrantRgb) : null);
        jsonObject.put(KEY_PALETTE_VIBRANT_TITLE_TEXT, mPaletteVibrantTitleTextColor != null ?
                Integer.toString(mPaletteVibrantTitleTextColor) : null);
        jsonObject.put(KEY_PALETTE_VIBRANT_BODY_TEXT, mPaletteVibrantBodyTextColor != null ?
                Integer.toString(mPaletteVibrantBodyTextColor) : null);
        jsonObject.put(KEY_PALETTE_LIGHT_VIBRANT_RGB, mPaletteLightVibrantRgb != null ?
                Integer.toString(mPaletteLightVibrantRgb) : null);
        jsonObject.put(KEY_PALETTE_LIGHT_VIBRANT_TITLE_TEXT, mPaletteLightVibrantTitleTextColor != null ?
                Integer.toString(mPaletteLightVibrantTitleTextColor) : null);
        jsonObject.put(KEY_PALETTE_LIGHT_VIBRANT_BODY_TEXT, mPaletteLightVibrantBodyTextColor != null ?
                Integer.toString(mPaletteLightVibrantBodyTextColor) : null);
        jsonObject.put(KEY_PALETTE_DARK_VIBRANT_RGB, mPaletteDarkVibrantRgb != null ?
                Integer.toString(mPaletteDarkVibrantRgb) : null);
        jsonObject.put(KEY_PALETTE_DARK_VIBRANT_TITLE_TEXT, mPaletteDarkVibrantTitleTextColor != null ?
                Integer.toString(mPaletteDarkVibrantTitleTextColor) : null);
        jsonObject.put(KEY_PALETTE_DARK_VIBRANT_BODY_TEXT, mPaletteDarkVibrantBodyTextColor != null ?
                Integer.toString(mPaletteDarkVibrantBodyTextColor) : null);
        jsonObject.put(KEY_PALETTE_MUTED_RGB, mPaletteMutedRgb != null ?
                Integer.toString(mPaletteMutedRgb) : null);
        jsonObject.put(KEY_PALETTE_MUTED_TITLE_TEXT, mPaletteMutedTitleTextColor != null ?
                Integer.toString(mPaletteMutedTitleTextColor) : null);
        jsonObject.put(KEY_PALETTE_MUTED_BODY_TEXT, mPaletteMutedBodyTextColor != null ?
                Integer.toString(mPaletteMutedBodyTextColor) : null);
        jsonObject.put(KEY_PALETTE_LIGHT_MUTED_RGB, mPaletteLightMutedRgb != null ?
                Integer.toString(mPaletteLightMutedRgb) : null);
        jsonObject.put(KEY_PALETTE_LIGHT_MUTED_TITLE_TEXT, mPaletteLightMutedTitleTextColor != null ?
                Integer.toString(mPaletteLightMutedTitleTextColor) : null);
        jsonObject.put(KEY_PALETTE_LIGHT_MUTED_BODY_TEXT, mPaletteLightMutedBodyTextColor != null ?
                Integer.toString(mPaletteLightMutedBodyTextColor) : null);
        jsonObject.put(KEY_PALETTE_DARK_MUTED_RGB, mPaletteDarkMutedRgb != null ?
                Integer.toString(mPaletteDarkMutedRgb) : null);
        jsonObject.put(KEY_PALETTE_DARK_MUTED_TITLE_TEXT, mPaletteDarkMutedTitleTextColor != null ?
                Integer.toString(mPaletteDarkMutedTitleTextColor) : null);
        jsonObject.put(KEY_PALETTE_DARK_MUTED_BODY_TEXT, mPaletteDarkMutedBodyTextColor != null ?
                Integer.toString(mPaletteDarkMutedBodyTextColor) : null);
        return jsonObject;
    }

    /**
     * Deserializes an liveWallpaperInfo object from a {@link JSONObject}.
     */
    public static LiveWallpaperInfo fromJson(JSONObject jsonObject) throws JSONException {
        return new Builder()
                .token(jsonObject.optString(KEY_TOKEN))
                .paletteVibrantRgb(paletteValue(jsonObject, KEY_PALETTE_VIBRANT_RGB))
                .paletteVibrantTitleTextRgb(paletteValue(jsonObject, KEY_PALETTE_VIBRANT_TITLE_TEXT))
                .paletteVibrantBodyTextRgb(paletteValue(jsonObject, KEY_PALETTE_VIBRANT_BODY_TEXT))
                .paletteLightVibrantRgb(paletteValue(jsonObject, KEY_PALETTE_LIGHT_VIBRANT_RGB))
                .paletteLightVibrantTitleTextRgb(paletteValue(jsonObject, KEY_PALETTE_LIGHT_VIBRANT_TITLE_TEXT))
                .paletteLightVibrantBodyTextRgb(paletteValue(jsonObject, KEY_PALETTE_LIGHT_VIBRANT_BODY_TEXT))
                .paletteDarkVibrantRgb(paletteValue(jsonObject, KEY_PALETTE_DARK_VIBRANT_RGB))
                .paletteDarkVibrantTitleTextRgb(paletteValue(jsonObject, KEY_PALETTE_DARK_VIBRANT_TITLE_TEXT))
                .paletteDarkVibrantBodyTextRgb(paletteValue(jsonObject, KEY_PALETTE_DARK_VIBRANT_BODY_TEXT))
                .paletteMutedRgb(paletteValue(jsonObject, KEY_PALETTE_MUTED_RGB))
                .paletteMutedTitleTextRgb(paletteValue(jsonObject, KEY_PALETTE_MUTED_TITLE_TEXT))
                .paletteMutedBodyTextRgb(paletteValue(jsonObject, KEY_PALETTE_MUTED_BODY_TEXT))
                .paletteLightMutedRgb(paletteValue(jsonObject, KEY_PALETTE_LIGHT_MUTED_RGB))
                .paletteLightMutedTitleTextRgb(paletteValue(jsonObject, KEY_PALETTE_LIGHT_MUTED_TITLE_TEXT))
                .paletteLightMutedBodyTextRgb(paletteValue(jsonObject, KEY_PALETTE_LIGHT_MUTED_BODY_TEXT))
                .paletteDarkMutedRgb(paletteValue(jsonObject, KEY_PALETTE_DARK_MUTED_RGB))
                .paletteDarkMutedTitleTextRgb(paletteValue(jsonObject, KEY_PALETTE_DARK_MUTED_TITLE_TEXT))
                .paletteDarkMutedBodyTextRgb(paletteValue(jsonObject, KEY_PALETTE_DARK_MUTED_BODY_TEXT))
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

    private static String asHex(Integer color) {
        return color != null ? "0x" + Integer.toHexString(color) : null;
    }

    public String toString() {
        return "v:[" + asHex(mPaletteVibrantRgb) + "," + asHex(mPaletteVibrantTitleTextColor) + "," + asHex(mPaletteVibrantBodyTextColor) + "]"
                + ", lv:[" + asHex(mPaletteLightVibrantRgb) + "," + asHex(mPaletteLightVibrantTitleTextColor) + "," + asHex(mPaletteLightVibrantBodyTextColor) + "]"
                + ", dv:[" + asHex(mPaletteDarkVibrantRgb) + "," + asHex(mPaletteDarkVibrantTitleTextColor) + "," + asHex(mPaletteDarkVibrantBodyTextColor) + "]"
                + ", m:[" + asHex(mPaletteMutedRgb) + "," + asHex(mPaletteMutedTitleTextColor) + "," + asHex(mPaletteMutedBodyTextColor) + "]"
                + ", lm:[" + asHex(mPaletteLightMutedRgb) + "," + asHex(mPaletteLightMutedTitleTextColor) + "," + asHex(mPaletteLightMutedBodyTextColor) + "]"
                + ", dm:[" + asHex(mPaletteDarkMutedRgb) + "," + asHex(mPaletteDarkMutedTitleTextColor) + "," + asHex(mPaletteDarkMutedBodyTextColor) + "]"
                + ", token:" + mToken;
    }
}
