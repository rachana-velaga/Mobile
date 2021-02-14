import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

public class AppTests extends Base{

	Base base = new Base();
	AndroidDriver driver = null;
	private AppiumDriverLocalService appiumService;
	private ExecutionListener executionListener;
	final String testAppName = "finditfixit";
	private static long INSTALL_DURATION_IN_SECONDS = 60L;
	private long explicitWaitTimeoutInSeconds = 10L;
	private WebDriverWait wait;
	final String testAppPackage = "gov.seattle.searequests";

	@BeforeTest
	public void beforeSuite() throws IOException, InterruptedException {

		executionListener = new ExecutionListener();
		DesiredCapabilities caps = null;
		String environment = System.getProperty("environment");
		String capabilities = environment + System.getProperty("capabilities");
		String apkFileName = System.getProperty("apkfilename");



		// Download TestAccounts.kdbx
		//executionListener.onExecutionStart();

		// Download apk from Artifactory
		//downloadApkFromArtifactory(apkFileName);

		if (capabilities.equals("localandroidweb")) {
			caps = base.getLocalAndroidWebCapabilities();

		} else if (capabilities.equals("localandroidapp")) {
			caps = base.getLocalAndroidAppCapabilities(apkFileName);

		} else if (capabilities.equals("saucelabsandroidweb")) {
			caps = base.getSauceLabsAndroidWebCapabilities();

		} else if (capabilities.equals("saucelabsandroidapp")) {
			caps = base.getSauceLabsAndroidAppCapabilities();

		} else if (capabilities.equals("localandroidplaystore")) {
			caps = base.getAndroidPlayStoreCapabilities();
		}

		appiumService = AppiumDriverLocalService.buildDefaultService();
		if (appiumService.isRunning()) {
			appiumService.stop();
		}
		appiumService.start();

		this.driver = setupAppiumServer(caps, environment);
		// this.driver = base.setupAppiumServer(caps, environment);
		this.wait = new WebDriverWait(driver, explicitWaitTimeoutInSeconds);

		uninstallApp(testAppPackage);
	}

	@Test
	public void testInstallFromGooglePlayStore() throws InterruptedException, MalformedURLException {

		getAndroidElementById("com.android.vending:id/search_box_idle_text").click();
		getAndroidElementById("com.android.vending:id/search_box_text_input").sendKeys(testAppName);
		((AndroidDriver<MobileElement>) driver).pressKeyCode(66);
		getAndroidElementById("com.android.vending:id/li_title").click();
		getAndroidElementByText("INSTALL").click();
		while(!driver.isAppInstalled(testAppPackage)) {
			System.out.println("Not installed yet !!!");
			Thread.sleep(5000);
		}
		System.out.println("Installed yay !!!");
		String lastActivityName = driver.currentActivity();
		System.out.println(""+lastActivityName);
		driver.startActivity(testAppPackage,"com.connectedbits.spot.ui.DashboardActivity") ; 
		getAndroidElementById("gov.seattle.searequests:id/btn_dashboard_preferences").click();
	}


	//uninstall the app if it's already installed
	private void uninstallApp(String appPackage) throws IOException, InterruptedException {
		final Process p = Runtime.getRuntime().exec("adb uninstall " + appPackage);

		new Thread(new Runnable() {
			public void run() {
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;

				try {
					while ((line = input.readLine()) != null)
						System.out.println(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

		p.waitFor();
	}




	@AfterSuite
	public void afterSuite() {
		appiumService.stop();
		driver.quit();
		executionListener.onExecutionFinish();
	}
}