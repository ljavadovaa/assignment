import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccessAccountPage extends PageBase {
    private By loginBy = By.xpath("//a[contains(@class,'hr_button') and @href='/login/']");

    public AccessAccountPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/access-account/");
    }

    public LoginPage login() {
        this.waitAndReturnElement(loginBy).click();
        return new LoginPage(this.driver);
    }
}
