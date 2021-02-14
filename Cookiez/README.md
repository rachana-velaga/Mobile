# Setup Instructions

###### Setup Android SDK + Android emulators + Environment variables
1. Install Android Studios:<a href="https://developer.android.com/studio"> https://developer.android.com/studio </a>
2. Launch Android Studios and create a new project
3. Create an emulator by going to: Tools > AVD Manager > Create a Virtual Device
4. Add an environment variable for ANDROID_HOME (C:\Users\<username>\AppData\Local\Android\Sdk)
5. Add an environment PATH variable to %ANDROID_HOME%\platform-tools
6. Add an environment PATH variable to %ANDROID_HOME%\tools
7. Test environment variables by typing into a terminal, adb devices. You should see "List of devices attached"


###### Install the Appium Server

Reference: http://appium.io/docs/en/about-appium/getting-started/

1. Install Node.js:<a href="https://nodejs.org/en/">https://nodejs.org/en/</a>
2. In a terminal run, npm install -g appium-chromedriver --chromedriver_version="2.42"
3. To bring up appium server, type, appium


###### Run Variables

Run locally (Android app)

<br>-Dcapabilities="androidapp"
<br>-Denvironment="local"
<br>-DappWaitActivity="com.connectedbits.spot.ui.DashboardActivity"

Run local Android web

<br>-Dcapabilities="androidweb"
<br>-Denvironment="local"

Run on Sauce Labs Android app

<br>-Dcapabilities="androidapp"
<br>-Denvironment="saucelabs"
<br>-DappWaitActivity="com.connectedbits.spot.ui.DashboardActivity"
<br>-DsaucelabsKey="692c1975-0446-4064-8912-aab7522cb490"
<br>-DsaucelabsUser="exta02"
<br>-DsaucelabsAppUrl="http://saucelabs.com/example_files/ContactManager.apk"
