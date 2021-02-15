import io.appium.java_client.android.AndroidDriver;

public class LoginPage extends Base {

    String userNameTextField = "main_UID";
    String passwordTextField = "main_PWD";
    String signInButton = "main_btnSubmit";

    public LoginPage(AndroidDriver driver){
        super.driver = driver;
    }

    /**
     * Log into Kuba
     * @param userName
     * @param password
     */
    public void logIn(String userName, String password) {
        inputText(getElementById(userNameTextField), userName);
        inputText(getElementById(passwordTextField), password);

        getElementById(signInButton).click();
    }

    public void goToLoginPage(String url) {
        goToUrl("www.macys.com");
    }

}
