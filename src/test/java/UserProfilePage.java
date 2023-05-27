import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class UserProfilePage extends PageBase{

    private By profileLocator = By.xpath("//div[@class='summary-edit-button']");
    private By firstnameLocator = By.xpath("//input[@name='personal_first_name']");
    private By lastnameLocator = By.xpath("//input[@name='personal_last_name']");
    private By headlineLocator = By.xpath("//input[@name='jobs_headline']");
    private By websiteLocator = By.xpath("//input[@name='website']");
    private By phoneLocator = By.xpath("//input[@name='phoneNumber']");
    private By linkedInLocator = By.xpath("//input[@name='linkedin_url']");
    private By saveButtonLocator = By.xpath("//button[@type='submit']");


    public UserProfilePage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/lcavadova?hr_r=1");
    }

    public void clearInput(By locator) {
        while(this.waitAndReturnElement(locator).getAttribute("value").length() > 0) {
            this.waitAndReturnElement(locator).sendKeys(Keys.BACK_SPACE);
        }
    }

    public UserProfilePage editIntro(String firstname, String lastname, String headline, String website, String phone, String linkedIn) {
        this.waitAndReturnElement(profileLocator).click();

        clearInput(firstnameLocator);
        this.waitAndReturnElement(firstnameLocator).sendKeys(firstname);

        clearInput(lastnameLocator);
        this.waitAndReturnElement(lastnameLocator).sendKeys(lastname);

        clearInput(headlineLocator);
        this.waitAndReturnElement(headlineLocator).sendKeys(headline);

        clearInput(websiteLocator);
        this.waitAndReturnElement(websiteLocator).sendKeys(website);

        clearInput(phoneLocator);
        this.waitAndReturnElement(phoneLocator).sendKeys(phone);

        clearInput(linkedInLocator);
        this.waitAndReturnElement(linkedInLocator).sendKeys(linkedIn);

        this.waitAndReturnElement(saveButtonLocator).click();
        return new UserProfilePage(this.driver);
    }
}
