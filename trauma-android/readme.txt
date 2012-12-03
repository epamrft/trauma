***********************************************
Deployment guide for the trauma-android project
***********************************************

************************
Getting started
************************
1. Install JDK 1.6 or above
2. Install the Android SDK
3. Install Maven
4. Install Git
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
2. Go to the trauma-android directory (where the pom.xml is located) and type `mvn clean install`. This will create an *.apk to the ./target/ folder
3. To install the app into your Android device or the Virtual Device, type `mvn android:deploy`
4. From here, you can either start TrAUMa manually from the device or type `mvn android:run` to start it automatically

************************
Help and links
************************
- Java SDK: http://www.oracle.com/technetwork/java/javase/downloads/index.html
- Android sdk: http://developer.android.com/sdk/index.html
- Maven: http://maven.apache.org/
- Git: http://git-scm.com/
- Help with setting environment variables: http://www.itechtalk.com/thread3595.html
- If you need help downloading the project from GitHub, see the Downloading from GitHub section below
- Introduction to Windows Command Promt: http://www.bleepingcomputer.com/tutorials/windows-command-prompt-introduction/

************************
Downloading from GitHub
************************
To download the trauma project from GitHub, follow these steps:
1. Open a command promt and navigate to your desired download directory
2. Make a new folder for the project using the `mkdir trauma` command
3. Navigate to the new folder and execute the `git init` command
4. Add the location of the trauma project to Git using the `git remote add trauma https://github.com/epamrft/trauma.git` command
5. Download the files to your local repo using the `git pull trauma master` command
6. Check out the files from your local repo using the git checkout command