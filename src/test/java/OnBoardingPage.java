import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OnBoardingPage extends PageBase{

    private By prepareRadioLocator = By.xpath("(//main[@class='onboarding-survey-main'])//span[contains(text(),'Prepare for Job Interviews')]");
    private By skipLocator = By.xpath("//div[@class='ui-content has-icon align-icon-right']");
    private By closeButtonLocator = By.xpath("//button[@data-balloon='Close']");

    public OnBoardingPage(WebDriver driver) {
        super(driver);
    }

    public DashboardPage onboard() {
        this.waitAndReturnElement(prepareRadioLocator).click();
        this.waitAndReturnElement(skipLocator).click();
        this.waitAndReturnElement(closeButtonLocator).click();
        return new DashboardPage(this.driver);
    }
}
