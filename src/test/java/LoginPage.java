import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends PageBase {
    private By usernameFieldLocator = By.xpath("//input[@name='username']");
    private By passwordFieldLocator = By.xpath("//input[@name='password']");
    private By clickLocator = By.xpath("//div[@class='checkbox-wrap']");
    private By loginButtonLocator = By.xpath("//span[@class='ui-text' and contains(text(), 'Log In')]");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/auth/login");
    }

    public DashboardPage logIn() {
        return new DashboardPage(this.driver);
    }

    public DashboardPage logIn(String username, String password) {
        this.waitAndReturnElement(usernameFieldLocator).sendKeys(username);
        this.waitAndReturnElement(passwordFieldLocator).sendKeys(password);
        this.waitAndReturnElement(clickLocator).click();
        this.waitAndReturnElement(loginButtonLocator).click();
        return new DashboardPage(this.driver);
    }
}
