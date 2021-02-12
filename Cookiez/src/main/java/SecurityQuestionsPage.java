import io.appium.java_client.android.AndroidDriver;

public class SecurityQuestionsPage extends Base {

    String answerTextField = "main_txtAnswer";
    String backButton = "main_btnBack";
    String submitButton = "main_btnSubmit";

    public SecurityQuestionsPage(AndroidDriver driver){
        super.driver = driver;
    }

    /**
     * Answer the security question
     * @param answer, the answer to the security question
     */
    public void answerSecurityQuestion(String answer) {
        inputText(getElementById(answerTextField), answer);
        getElementById(submitButton).click();
    }
}
