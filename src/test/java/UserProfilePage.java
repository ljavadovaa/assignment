import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class UserProfilePage extends PageBase{

    private By profileLocator = By.xpath("//div[@class='summary-edit-button']");
    private By firstnameLocator = By.xpath("//input[@name='personal_first_name']");
    private By lastnameLocator = By.xpath("//input[@name='personal_last_name']");
    private By headlineLocator = By.xpath("//input[@name='jobs_headline']");
    //private By phoneLocator = By.id("phoneNumber");
    //private By countrySelectionArrowLocator = By.xpath("(//div[@class=' css-84jzhn-indicatorContainer'])[1]");
    //private By countrySelectionLocator = By.xpath("(//div[@class=' css-1hz4keq-singleValue'])[1]");
    //private By phoneSelectionArrowLocator = By.xpath("(//div[@class=' css-84jzhn-indicatorContainer'])[2]");
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

    public UserProfilePage editIntro(String firstname, String lastname, String headline) {
        this.waitAndReturnElement(profileLocator).click();

        clearInput(firstnameLocator);
        this.waitAndReturnElement(firstnameLocator).sendKeys(firstname);

        clearInput(lastnameLocator);
        this.waitAndReturnElement(lastnameLocator).sendKeys(lastname);

        clearInput(headlineLocator);
        this.waitAndReturnElement(headlineLocator).sendKeys(headline);

        this.waitAndReturnElement(saveButtonLocator).click();
        return new UserProfilePage(this.driver);
    }

    //       this.wait.until(ExpectedConditions.visibilityOfElementLocated(countrySelectionArrowLocator)).click();
//       List<WebElement> countries = this.driver.findElements(countrySelectionArrowLocator);
//        for(WebElement oneCountry : countries){
//            System.out.println(oneCountry);
//
//            if(oneCountry.getText().trim().equalsIgnoreCase(country)) {
//                By countrySelectionLocator = By.xpath("(//div[@class=' css-1hz4keq-singleValue' and contains(text(), '"+ oneCountry +"')])[1]");
//                this.waitAndReturnElement(countrySelectionLocator).click();
//                //oneCountry.click();
//                System.out.println("yesss");
//            }
//        }
}
