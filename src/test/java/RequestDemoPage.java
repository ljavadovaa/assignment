import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class RequestDemoPage extends PageBase{
    private By firstnameLocator = By.xpath("//input[@id='FirstName']");
    private By lastnameLocator = By.xpath("//input[@id='LastName']");
    private By workEmailLocator = By.xpath("//input[@name='Email']");
    private By companyLocator = By.xpath("//input[@name='Company']");
    private By jobTitleLocator = By.xpath("//input[@name='Title']");
    private By companySizeLocator = By.xpath("//select[@id='Company_Size__c']");
    private By phoneLocator = By.xpath("//input[@name='Phone']");
    private By countryLocator = By.xpath("//select[@id='Country']");
    private By consentLabelLocator = By.xpath("//label[@id='LblmktoCheckbox_32138_0']");
    private By buttonLocator = By.xpath("//div[@class='mktoButtonRow']");

    public RequestDemoPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/request-demo/");
    }

    public ThanksRequestDemoPage fillRequestDemoForm(String firstname, String lastname, String workEmail, String company,
                                                     String jobTitle, String companySize, String phone, String country) {

        this.waitAndReturnElement(firstnameLocator).sendKeys(firstname);
        this.waitAndReturnElement(lastnameLocator).sendKeys(lastname);
        this.waitAndReturnElement(workEmailLocator).sendKeys(workEmail);
        this.waitAndReturnElement(companyLocator).sendKeys(company);
        this.waitAndReturnElement(jobTitleLocator).sendKeys(jobTitle);

        this.waitAndReturnElement(companySizeLocator).click();
        this.waitAndReturnElement(By.xpath("//option[@value='"+companySize+"']")).click();

        this.waitAndReturnElement(phoneLocator).sendKeys(phone);

        this.waitAndReturnElement(countryLocator).click();
        this.waitAndReturnElement(By.xpath("//option[@value='"+country+"']")).click();

        this.waitAndReturnElement(consentLabelLocator).click();

        WebElement webElement = this.waitAndReturnElement(buttonLocator);
        Actions actions = new Actions(driver); actions.moveToElement(webElement).click().build().perform();

        return new ThanksRequestDemoPage(this.driver);
    }


}
