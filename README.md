Action Launcher API
==================================

A simple API live wallpaper developers can use to allow [Action Launcher 3](2) to theme itself based on the current colors of your wallpaper.

<img src="screenshot.png" width="300">

Note: use of this API is only necessary for **live wallpapers** (not status wallpapers). This is due to Android not providing any APIs for apps to fetch Bitmap data about the current live wallpaper.



Usage
=====

1. Integrate the Action 3 API code into the `dependencies` section of your `build.gradle` file:
	
	`compile 'com.actionlauncher:action3-api:1.0.1'`
  <br><br>If you're not using gradle, you can add the `action3-api-1.0.1jar` to your `/libs` folder, or copy the API [code][3] directly into your project.<br><br>


2. Add this code to your `AndroidManifest.xml` (inside the `Application` entry):

    ```
    <service android:name="com.actionlauncher.api.LiveWallpaperSource"
        android:label="LiveWallpaperSource"
        android:exported="true">
        <intent-filter>
            <action android:name="com.actionlauncher.api.action.LiveWallpaperSource" />
        </intent-filter>
    </service>
    ```
3. In your application code, you will need a `Bitmap` instance of your live wallpaper. At the point in your code where you have this `Bitmap` instance, add the following:

    ```
    Bitmap myBitmap = ...
    LiveWallpaperSource.setBitmapSynchronous(context, myBitmap);
    ```



<br>To test it all works:
 
 * Load Action Launcher 3 (you *must* be using version 3.3 or later).
* Ensure your wallpaper is set as the live wallpaper.
* Ensure Action Launcher's wallpaper extraction mode is enabled (Settings -> Quicktheme -> Theme -> Wallpaper).


Demo
====
The `main` app in this repository demonstrates the live wallpaper functionality. It is basically the main app from the [Android Live Wallpaper Hello World project](1). If you double-tap empty space on Action Launcher 3's home screen, the wallpaper image will change, and you items such as the search bar will have their colors updated as per the current wallpaper image in Action Launcher 3.

Check out the `LiveWallpaperSource.setBitmapSynchronous()` call in `MuzeiBlurRenderer.java`.

Notes
=====

* Keep in mind that each time you call `LiveWallpaperSource.setBitmapSynchronous()`, a new palette will be generated. In order to not waste battery, you only want to make this call when you know there has been a meaninful visual change in your wallpaper app and Action Launcher's Quicktheme feature should be updated.
* This API includes a copy of API 22's Palette library from Support Library named `ActionPalette`[4]. It has been integrated directly into the ActionLauncherApi rather than as a dependency because:
 * Many live-wallpaper developers are still using Eclipse, which has seemingly isn't well set up to use AARs.
 * Makes the dependencies easier.
 * It doesn't take much code size, so there's little harm in it.


3rd party examples
==================
The following Android apps make use of this API:

* [Minima Pro Live Wallpaper](https://play.google.com/store/apps/details?id=com.joko.minimapro)



License
=======

    Copyright 2015 Chris Lacy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/chrislacy/AndroidLiveWallpaperHelloWorld
[2]: https://play.google.com/store/apps/details?id=com.actionlauncher.playstore
[3]: https://github.com/chrislacy/ActionLauncherApi/tree/master/api/src/main/java
[4]: https://github.com/chrislacy/ActionLauncherApi/tree/master/api/src/main/java/com/actionlauncher/api/actionpalette