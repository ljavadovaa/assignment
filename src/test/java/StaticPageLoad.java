import org.openqa.selenium.WebDriver;

public class StaticPageLoad extends PageBase{
    private String url;

    public StaticPageLoad(WebDriver driver, String url) {
        super(driver);
        this.url = url;
    }

    public void navigateToPage() {
        driver.get(url);
    }
}
