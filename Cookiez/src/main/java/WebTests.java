import java.net.MalformedURLException;

import gov.seattle.itd.automation.listeners.ExecutionListener;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import gov.seattle.itd.base.TestRunnerBase;

public class WebTests extends Base {

	LoginPage loginPage = null;
	SecurityQuestionsPage securityQuestionsPage = null;
	HomePage homePage = null;
	AndroidDriver driver = null;
    private AppiumDriverLocalService appiumService;
	private ExecutionListener executionListener;
	private String program = "CIS";
	private String app = "Kubra";
	private String env = "QA";
	private String user = "reddy678";

    @BeforeTest
	public void beforeSuite() throws MalformedURLException {

		executionListener = new ExecutionListener();
		executionListener.onExecutionStart();
		
		DesiredCapabilities caps = null;
		String environment = System.getProperty("environment");
		String capabilities = environment + System.getProperty("capabilities");
		String apkFileName = System.getProperty("apkfilename");

        environment = environment == null ? "local" : environment;
        capabilities = capabilities.equals("nullnull") ? "localandroidweb" : capabilities;

		if (capabilities.equals("localandroidweb")) {
			caps = getLocalAndroidWebCapabilities();
			
		} else if (capabilities.equals("localandroidapp")) {
			caps = getLocalAndroidAppCapabilities(apkFileName);
			
		} else if (capabilities.equals("saucelabsandroidweb")) {
			caps = getSauceLabsAndroidWebCapabilities();
			
		} else if (capabilities.equals("saucelabsandroidapp")) {
			caps = getSauceLabsAndroidAppCapabilities();
			
		}

        appiumService = AppiumDriverLocalService.buildDefaultService();
		if (appiumService.isRunning()) {
		    appiumService.stop();
        }
        appiumService.start();
		
        this.driver = setupAppiumServer(caps, environment);
	}
    
    @Test
    public void testLogin() {
		loginPage = new LoginPage(driver);

    	// Go to the login page and click "Pay as Guest"
		loginPage.goToLoginPage("https://prep8.i-doxs.net/SeattleUtilities/SignIn.aspx");
    	loginPage.logIntoKubra("t_aboda", getPassword(program, app, env, user));

        Assert.assertEquals(driver.getTitle(), "Sign-in Protection - SeattleUtilities");
    }

    @Test
    public void searchAndFilterAccount() {
        loginPage = new LoginPage(driver);
        securityQuestionsPage = new SecurityQuestionsPage(driver);
        homePage = new HomePage(driver);

        loginPage.goToLoginPage("https://prep8.i-doxs.net/SeattleUtilities/SignIn.aspx");
        loginPage.logIntoKubra("t_aboda", getPassword(program, app, env, user));

        securityQuestionsPage.answerSecurityQuestion("movie");

        // Apply account filter and verify results
        homePage.applyAccountFilter("4389110000");
        Assert.assertEquals(homePage.getAccountShowingStatus(), "Record 1 of 1");

        // Reset Account filter and verify results
        homePage.resetAccountFilter();
		Assert.assertEquals(homePage.getAccountShowingStatus(), "Record 5 of 450");
    }

    
    @AfterSuite
	public void afterSuite() {
		appiumService.stop();
		driver.quit();
		executionListener.onExecutionFinish();

	}
}