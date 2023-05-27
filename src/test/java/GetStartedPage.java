import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GetStartedPage extends PageBase{

    private By signupLocator = By.xpath("//a[@data-url='/signup']");
    private By createAccountLocator = By.xpath("//a[@href='/signup']");

    public GetStartedPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/get-started/");
    }

    public RegistrationPage createAccount() {
        this.waitAndReturnElement(signupLocator).click();
        this.waitAndReturnElement(createAccountLocator).click();
        return new RegistrationPage(this.driver);
    }
}
