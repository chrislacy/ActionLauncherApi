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

    public LiveWallpaperInfo getCurrentLiveWallpaperInfo() {
        return mCurrentLiveWallpaperInfo;
    }

    public void setCurrentLiveWallpaperInfo(LiveWallpaperInfo liveWallpaperInfo) {
        mCurrentLiveWallpaperInfo = liveWallpaperInfo;
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
        Bundle liveWallpaperInfoBundle = bundle.getBundle(KEY_CURRENT_LIVE_WALLPAPER_INFO);
        if (liveWallpaperInfoBundle != null) {
            state.mCurrentLiveWallpaperInfo = LiveWallpaperInfo.fromBundle(liveWallpaperInfoBundle);
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
        JSONObject liveWallpaperInfoObject = jsonObject.optJSONObject(KEY_CURRENT_LIVE_WALLPAPER_INFO);
        if (liveWallpaperInfoObject != null) {
            mCurrentLiveWallpaperInfo = LiveWallpaperInfo.fromJson(liveWallpaperInfoObject);
        }
    }

    public static SourceState fromJson(JSONObject jsonObject) throws JSONException{
        SourceState state = new SourceState();
        state.readJson(jsonObject);
        return state;
    }

}
