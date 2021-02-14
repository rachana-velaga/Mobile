import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import io.appium.java_client.MobileBy;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Base {
	
	public AndroidDriver driver = null;
    private WebDriverWait wait = null;
	private String url = null;
	private String saucelabsUser = System.getProperty("saucelabsUser");
	private String saucelabsKey = System.getProperty("saucelabsKey");
	private String saucelabsAppUrl = System.getProperty("saucelabsAppUrl");
	private String appPackage = System.getProperty("appPackage");
	private String appWaitActivity = System.getProperty("appWaitActivity");

	private int waitForElementInSeconds = 10;

	/**
	 * Setup the appium server locally or launch it on Sauce Labs
	 * @param capabilities, the capabilities to use (local vs saucelabs, web vs app
	 * @param environment, local or saucelabs
	 * @return the AndroidDriver
	 * @throws MalformedURLException
	 */
    public AndroidDriver setupAppiumServer(DesiredCapabilities capabilities, String environment) throws MalformedURLException {

    	if (environment.equals("local")) {
    		url = "http://127.0.0.1:4723/wd/hub";
    	} else if (environment.equals("saucelabs")) {
    		url = "https://" + saucelabsUser + ":" + saucelabsKey +"@ondemand.saucelabs.com/wd/hub";
    	} else {
    		System.out.println("Not a valid environment");
    	
    	}

    	this.driver = new AndroidDriver(new URL(url), capabilities);
        this.wait = new WebDriverWait(driver, waitForElementInSeconds);
		return driver;
        
    }

    /**
     * Get the DesiredCapabilities for running app tests on a local android device
     * @param apkFileName, the file name of the apk
     * @return capabilities, the DesiredCapabilities
     */
    public DesiredCapabilities getLocalAndroidAppCapabilities(String apkFileName) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appWaitActivity" , appWaitActivity);
        capabilities.setCapability("app", "src/main/resources/artifacts/apks/" + apkFileName);
     	capabilities.setCapability("automationName", "UiAutomator2"); 
  
        return capabilities;
    }

    public DesiredCapabilities getAndroidPlayStoreCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appWaitPackage", appPackage);
        capabilities.setCapability("appWaitActivity" , appWaitActivity);
        capabilities.setCapability("appActivity" , appWaitActivity);
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("newCommandTimeOut", "100000");
        return capabilities;
    }

    /**
     * Get the DesiredCapabilities for running web tests on a local android device
     * @return capabilities, the DesiredCapabilities
     */
    public DesiredCapabilities getLocalAndroidWebCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("browserName", "Chrome");

        return capabilities;
    }
    
    /**
     * Get the DesiredCapabilities for running android app tests on Sauce Labs
     * @return capabilities, the DesiredCapabilities
     */
    public DesiredCapabilities getSauceLabsAndroidAppCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("app", saucelabsAppUrl);
        capabilities.setCapability("deviceOrientation", "portrait");

        return capabilities;
    }
    
    /**
     * Get the DesiredCapabilities for running web tests on Sauce Labs
     * @return capabilities, the DesiredCapabilities
     */
    public DesiredCapabilities getSauceLabsAndroidWebCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("browserName", "Chrome");

        return capabilities;
    }

    /**
     * Wait # seconds for an element to be present
     * @param element, the element to wait for
     * @return true, if the element is present, false if it is not present
     */
    public Boolean waitForElementToBePresent(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, waitForElementInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));

        return element.isDisplayed();
    }
    public WebElement getAndroidElementById(String id) {
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\""+id+"\")")));
        return driver.findElementByAndroidUIAutomator("UiSelector().resourceId(\""+id+"\")");     
    }
    
    public WebElement getAndroidElementByText(String text) {
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().text(\""+text+"\")")));
        return driver.findElementByAndroidUIAutomator("UiSelector().text(\""+text+"\")");     
    }

    /**
     * Get an elment by ID
     * @param id, the element's ID
     * @return the element
     */
    public WebElement getElementById(String id) {
        //waitForElementToBePresent(driver.findElementById(id));
        return driver.findElementById(id);
    }

    /**
     * Get an element by xpath
     * @param xpath, the element's xpath
     * @return the element
     */
    public WebElement getElementByXpath(String xpath) {
        waitForElementToBePresent(driver.findElementByXPath(xpath));
        return driver.findElementByXPath(xpath);
    }

    /**
     * Get an element by link text
     * @param linkText, the element's link text
     * @return the element
     */
    public WebElement getElementByLinkText(String linkText) {
        waitForElementToBePresent(driver.findElementByLinkText(linkText));
        return driver.findElementByLinkText(linkText);
    }

    /**
     * Enter text into a text field
     * @param element, the element to enter text into
     * @param text, the text to enter
     */
    public void inputText(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    public AndroidDriver getDriver(){
        return driver;
    }

    public static String getPassword(String program, String app, String env, String user) {
        Map<String, String> credentials = KeePass.getCredentials(program, app, env, user);
        return credentials.get("password");
    }

    public void downloadApkFromArtifactory(String apkFile) {
        try {
            ArtifactoryDownloader.getArtifact("qa-binaries", apkFile,
                    "..\\Cookiez\\src\\main\\resources\\artifacts\\apks");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unable to get KeePass file for test");
        }
    }

}
