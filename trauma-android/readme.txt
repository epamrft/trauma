***********************************************
Deployment guide for the trauma-android project
***********************************************

************************
Getting started
************************
1. Install JDK 1.6 or above (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Install the Android SDK (http://developer.android.com/sdk/index.html)
3. Install Maven (http://maven.apache.org/)
4. Install Git (http://git-scm.com/)
5. Set environment variable ANDROID_HOME to the path of your installed Android SDK
6. Add %ANDROID_HOME%/tools and %ANDROID_HOME%/platform_tools to your $PATH
7. Set environment variable JAVA_HOME to the path of your java sdk folder
8. Using Android SDK Manager download the SDK Platform and Google APIs for Android 2.3.3 (API 10). Also download the USB Driver.
9. Using the Android Virtual Device Manager create a new Android Virtual Device, name it Android10, set the target attribute to Google APIs (Google Inc.) - API Level 10. Set the SD Card size to a minimum of 50 MiB
10. Open a command prompt and enter the following:
	`mvn install:install-file -Dfile=%ANDROID_HOME%/add-ons/addon-google_apis-google-10/libs/maps.jar -DgroupId=com.google.android.maps -DartifactId=maps -Dversion=1.0 -Dpackaging=jar`
11. Download the trauma-android project from GitHub using Git

Note: you can skip step 9 if you have an android device and wish to install the TrAUMa to it.

************************
Installing
************************
1a. Plug in your Android device
	OR
1b. Start the Virtual Device that you created earlier with the command `emulator @Android10`
2. Go to the trauma-android directory (where the pom.xml is located) and type `mvn install`. This will create an *.apk to the ./target/ folder
3a. To install the application to your Android smartphone/Virtual Device navigate to the target folder, find the *.apk file (if it had the name trauma.apk) and write `adb install trauma.apk`
	OR
3b. If you already have the application and you want to update it `adb install -r trauma.apk`

************************
Misc
************************
Files and folders like project.properties, .settings/, gen/, etc. are left in the repository intentionally, they are needed by the Android SDK