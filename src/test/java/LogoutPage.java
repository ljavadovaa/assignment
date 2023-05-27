import org.openqa.selenium.WebDriver;

public class LogoutPage extends PageBase {
    public LogoutPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/lcavadova?hr_r=1");
    }

    public DashboardPage navigateBackToDashboardPage() {
        driver.navigate().back();
        return new DashboardPage(driver);
    }
}
