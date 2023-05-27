import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends PageBase {
    private By downArrowLocator = By.xpath("//div//i[contains(@class, 'ui-icon-chevron-down')]");
    private By profileLocator = By.xpath("//div//ul//li//a[contains(@class, 'profile-progress')]");
    private By logoutLocator = By.xpath("//div//ul//li//button[contains(@class, 'btn-as-link')]");

    public DashboardPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/dashboard");
    }

    public UserProfilePage checkProfile() {
        this.waitAndReturnElement(downArrowLocator).click();
        this.waitAndReturnElement(profileLocator).click();
        return new UserProfilePage(this.driver);
    }

    public LogoutPage logout() {
        this.waitAndReturnElement(downArrowLocator).click();
        this.waitAndReturnElement(logoutLocator).click();
        return new LogoutPage(this.driver);
    }
}
