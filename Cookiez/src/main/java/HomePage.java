import io.appium.java_client.android.AndroidDriver;

public class HomePage extends Base {

    String applyFilterLink = "linkFilterPanel";
    String filterAccountNumberField = "main_txtAccountNumber";
    String applyFilterButton = "main_btnApply";
    String resetFilterButton = "main_lnkClear";
    String showStatus = "main_lblAccountsShowingStatus";

    public HomePage(AndroidDriver driver){
        super.driver = driver;
    }

    public void applyAccountFilter(String accountNumber) {
        getElementById(applyFilterLink).click();
        inputText(getElementById(filterAccountNumberField), accountNumber);
        getElementById(applyFilterButton).click();
    }

    public void resetAccountFilter() {
        getElementById(applyFilterLink).click();
        getElementById(resetFilterButton).click();
    }

    public String getAccountShowingStatus() {
        return getElementById(showStatus).getText();
    }
}
