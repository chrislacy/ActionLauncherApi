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

package com.actionlauncher.api.internal;

/**
 * Internal intent constants for sources.
 */
public class ProtocolConstants {
    // Received intents
    public static final String ACTION_SUBSCRIBE = "com.actionlauncher.api.action.SUBSCRIBE";
    public static final String EXTRA_SUBSCRIBER_COMPONENT = "com.actionlauncher.api.extra.SUBSCRIBER_COMPONENT";
    public static final String EXTRA_TOKEN = "com.actionlauncher.api.extra.TOKEN";
    public static final String EXTRA_STATE = "com.actionlauncher.api.extra.STATE";

    public static final String ACTION_FETCH_PALETTE = "com.actionlauncher.api.FETCH_PALETTE";

    // Sent intents
    public static final String ACTION_PUBLISH_STATE = "com.actionlauncher.api.action.PUBLISH_UPDATE";
    public static final String EXTRA_LIVE_WALLPAPER_INFO = "com.actionlauncher.api.extra.LIVE_WALLPAPER_INFO";

    private ProtocolConstants() {
    }
}
