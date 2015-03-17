package com.actionlauncher.api.internal;

import android.os.Bundle;

import com.actionlauncher.api.LiveWallpaperInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents the published state of a live wallpaper source.
 */
public class SourceState {
    private static final String KEY_CURRENT_LIVE_WALLPAPER_INFO = "currentLWPI";

    private LiveWallpaperInfo mCurrentLiveWallpaperInfo;

    public LiveWallpaperInfo getCurrentArtwork() {
        return mCurrentLiveWallpaperInfo;
    }

    public void setCurrentLiveWallpaperInfo(LiveWallpaperInfo artwork) {
        mCurrentLiveWallpaperInfo = artwork;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (mCurrentLiveWallpaperInfo != null) {
            bundle.putBundle(KEY_CURRENT_LIVE_WALLPAPER_INFO, mCurrentLiveWallpaperInfo.toBundle());
        }
        return bundle;
    }

    public static SourceState fromBundle(Bundle bundle) {
        SourceState state = new SourceState();
        Bundle artworkBundle = bundle.getBundle(KEY_CURRENT_LIVE_WALLPAPER_INFO);
        if (artworkBundle != null) {
            state.mCurrentLiveWallpaperInfo = LiveWallpaperInfo.fromBundle(artworkBundle);
        }
        return state;
    }

    public JSONObject toJson() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        if (mCurrentLiveWallpaperInfo != null) {
            jsonObject.put(KEY_CURRENT_LIVE_WALLPAPER_INFO, mCurrentLiveWallpaperInfo.toJson());
        }
        return jsonObject;
    }

    public void readJson(JSONObject jsonObject) throws JSONException {
        JSONObject artworkJsonObject = jsonObject.optJSONObject(KEY_CURRENT_LIVE_WALLPAPER_INFO);
        if (artworkJsonObject != null) {
            mCurrentLiveWallpaperInfo = LiveWallpaperInfo.fromJson(artworkJsonObject);
        }
    }

    public static SourceState fromJson(JSONObject jsonObject) throws JSONException{
        SourceState state = new SourceState();
        state.readJson(jsonObject);
        return state;
    }

}
