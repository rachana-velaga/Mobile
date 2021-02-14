import io.appium.java_client.ios.IOSDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class iOSAppExample {

    public static void main(String[] args) throws Exception {
    	final String USERNAME = "YOUR USERNAME";
        final String KEY = "YOUR KEY";
        final String URL = "https://" + USERNAME + ":" + KEY +
                "@ondemand.saucelabs.com/wd/hub";

        // define the capabilities you want
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("deviceName", "iPhone 6");
        caps.setCapability("platformVersion", "11.2");
        caps.setCapability("app", "https://s3.amazonaws.com/appium/TestApp8.4.app.zip");
        caps.setCapability("deviceOrientation", "portrait");

        // create the driver
        WebDriver driver = new IOSDriver(new URL(URL), caps);
        driver.get("http://www.google.com/");
        driver.findElement(By.id("searchIcon")).click();
        driver.findElement(By.id("searchInput")).sendKeys("Hey this is really working?!?!?!");
        driver.findElement(By.id("searchButton")).click();

        Assert.assertEquals(driver.findElement(By.id("inputSearch")).getAttribute("value"), "Hey this is really working?!?!?!");

        driver.quit();
    }
}
