import io.appium.java_client.android.AndroidDriver;

public class HomePage extends Base {

    String applyFilterLink = "linkFilterPanel";
    String filterAccountNumberField = "main_txtAccountNumber";
    String applyFilterButton = "main_btnApply";
    String results = "main_lblResults";

    public HomePage(AndroidDriver driver){
        super.driver = driver;
    }

    public void applyFilter(String item) {
        getElementById(applyFilterLink).click();
        inputText(getElementById(filterAccountNumberField), item);
        getElementById(applyFilterButton).click();
    }


    public String getResults() {
        return getElementById(results).getText();
    }
}
