import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends PageBase{

    private By nameLocator = By.xpath("//input[@aria-label='First & Last name']");
    private By emailLocator = By.xpath("//input[@aria-label='Email']");
    private By passwordLocator = By.xpath("//input[@aria-label='Your password']");
    private By checkboxLocator = By.xpath("//div[@class='checkbox-wrap']");
    private By createAccountLocator = By.xpath("//button[@value='request']");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public OnBoardingPage createAccountForRegistration(String name, String email, String password) {
        this.waitAndReturnElement(nameLocator).sendKeys(name);
        this.waitAndReturnElement(emailLocator).sendKeys(email);
        this.waitAndReturnElement(passwordLocator).sendKeys(password);
        this.waitAndReturnElement(checkboxLocator).click();
        this.waitAndReturnElement(createAccountLocator).click();
        return new OnBoardingPage(this.driver);
    }
}
