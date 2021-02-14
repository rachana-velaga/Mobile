import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AndroidAppExample {

    public static void main(String[] args) throws Exception {
        final String USERNAME = "YOUR USERNAME";
        final String KEY = "YOUR KEY";
        final String URL = "https://" + USERNAME + ":" + KEY +
                "@ondemand.saucelabs.com/wd/hub";

        // define the capabilities you want
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("platformVersion", "8.0");
        caps.setCapability("app", "http://saucelabs.com/example_files/ContactManager.apk");
        caps.setCapability("deviceOrientation", "portrait");

        // create the driver
        WebDriver driver = new AndroidDriver(new URL("https://prep8.i-doxs.net/testpages.aspx"), caps);

        driver.quit();
    }
}
