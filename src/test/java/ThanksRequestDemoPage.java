import org.openqa.selenium.WebDriver;

public class ThanksRequestDemoPage extends PageBase{
    public ThanksRequestDemoPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hackerrank.com/request-demo-ty/");
    }
}
