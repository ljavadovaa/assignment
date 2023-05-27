import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

class MainPage extends PageBase {
    private By footerBy = By.xpath("//a[contains(text(), 'Copyright')]");
    private By accessAccountBy = By.xpath("//a[@href='/access-account/']");

    private By subMenuContainerBy = By.xpath("//*[@id='menu-main-navigation']/li[1]/div[1]");

    private By requestDemoLocator = By.xpath("(//a[@href='/request-demo/'])[1]");
    private By signUpLocator = By.xpath("(//a[@href='/get-started/'])[2]");

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/");
    }

    public String getFooterText() {
        return this.waitAndReturnElement(footerBy).getText();
    }

    public AccessAccountPage accessAccount() {
        this.waitAndReturnElement(accessAccountBy).click();
        return new AccessAccountPage(this.driver);
    }

    public void hoverOverSubMenu() {
        WebElement subMenuContainer = this.waitAndReturnElement(subMenuContainerBy);
        Actions actions = new Actions(this.driver);
        actions.moveToElement(subMenuContainer).build().perform();
    }

    public RequestDemoPage requestDemo() {
        this.waitAndReturnElement(requestDemoLocator).click();
        return new RequestDemoPage(this.driver);
    }

    public GetStartedPage signUp() {
        this.waitAndReturnElement(signUpLocator).click();
        return new GetStartedPage(this.driver);
    }
}
