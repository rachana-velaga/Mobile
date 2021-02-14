import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import java.net.URL;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AndroidWebAppExample {

    public static void main(String[] args) throws Exception {
        final String USERNAME = "YOUR USERNAME";
        final String KEY = "YOUR KEY";
        final String URL = "https://" + USERNAME + ":" + KEY +
                "@ondemand.saucelabs.com/wd/hub";

        // define the capabilities you want
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "8.0");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("browserName", "Chrome");

        // create the driver
        AppiumDriver<MobileElement> driver = new AndroidDriver<>(new URL(URL), caps);
        driver.get(URL);
        driver.findElement(By.linkText("Pay as Guest")).click();

        driver.quit();
    }
}
