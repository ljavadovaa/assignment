import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    private WebDriver driver;
    private MainPage mainPage;
    private Properties properties;
    private String[] staticPages;

    @BeforeMethod
    public void initTest() {
        System.setProperty("webdriver.chrome.driver", "/Users/lemanjavadova/Selenium/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        this.driver = new ChromeDriver(options);
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        this.driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPage = new MainPage(this.driver);
    }

    public DashboardPage login(){
        AccessAccountPage accessAccountPage = mainPage.accessAccount();
        LoginPage loginPage = accessAccountPage.login();
        String email = properties.getProperty("user.email");
        String password = properties.getProperty("user.password");
        return loginPage.logIn(email, password);
    }

    @Test
    public void testStaticPageLoad() {
        String staticPagesString = properties.getProperty("static.pages");
        staticPages = staticPagesString.split(",");
        String staticPageTitle = properties.getProperty("static.page.title");

        for (String url : staticPages) {
            StaticPageLoad staticPage = new StaticPageLoad(this.driver, url);
            staticPage.navigateToPage();
            Assert.assertTrue(staticPage.getBodyText().contains(staticPageTitle));
        }
    }

    // Hover test
    @Test
    public void testHoverOverSubMenu() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.hoverOverSubMenu();
        Assert.assertTrue(this.driver.findElement(By.xpath("//*[@id='menu-main-navigation']/li[1]/div[1]")).isDisplayed());
    }

    // Send a form
    @Test
    public void testRequestDemoPage() {
        MainPage mainPage = new MainPage(this.driver);
        RequestDemoPage requestDemoPage = mainPage.requestDemo();
        String firstname = properties.getProperty("demo.firstname");
        String lastname = properties.getProperty("demo.lastname");
        String workEmail = properties.getProperty("demo.workEmail");
        String company = properties.getProperty("demo.company");
        String jobTitle = properties.getProperty("demo.jobTitle");
        String companySize = properties.getProperty("demo.companySize");
        String phone = properties.getProperty("demo.phone");
        String country = properties.getProperty("demo.country");
        ThanksRequestDemoPage thanksDemoPage = requestDemoPage.fillRequestDemoForm(
                firstname, lastname, workEmail, company, jobTitle, companySize, phone, country);
        String demoTextContains = properties.getProperty("demo.text.contains");
        Assert.assertTrue(thanksDemoPage.getBodyText().contains(demoTextContains));
    }

    // Registration form
    @Test
    public void testRegistrationPage() {
        MainPage mainPage = new MainPage(this.driver);
        GetStartedPage getStartedPage = mainPage.signUp();
        RegistrationPage registrationPage = getStartedPage.createAccount();
        String name = properties.getProperty("user.registration.name");
        String email = properties.getProperty("user.registration.email");
        String password = properties.getProperty("user.registration.password");
        OnBoardingPage accountForRegistration = registrationPage.createAccountForRegistration(name, email, password);
        DashboardPage dashboardPage = accountForRegistration.onboard();
        Assert.assertTrue(dashboardPage.getBodyText().contains("Welcome "+name.split(" ")[0]+"!"));
    }

    @Test
    public void testPageTitle() {
        String pageTitleContains = properties.getProperty("page.title.contains");
        Assert.assertTrue(mainPage.getPageTitle().contains(pageTitleContains));
    }

    @Test
    public void testMainFooterText() {
        String footerTextContains = properties.getProperty("footer.text.contains");
        Assert.assertTrue(mainPage.getFooterText().contains(footerTextContains));
    }

    @Test
    public void testInvalidLogin() {
        LoginPage loginPage = mainPage.accessAccount().login();
        String email = "user.email@gmail.com";
        String password = "user.password";
        DashboardPage dashboardPage = loginPage.logIn(email, password);
        String dashboardBodyTextContains = properties.getProperty("dashboard.body.text.contains");
        Assert.assertTrue(dashboardPage.getBodyText().contains(dashboardBodyTextContains));
    }

    @Test(dependsOnMethods = "testMainFooterText")
    public void testLogin() {
        DashboardPage dashboardPage = login();
        String dashboardBodyTextContains = properties.getProperty("dashboard.body.text.contains");
        Assert.assertTrue(dashboardPage.getBodyText().contains(dashboardBodyTextContains));
    }

    @Test(dependsOnMethods = "testLogin")
    public void testUserProfile() {
        DashboardPage dashboardPage = login();
        String firstname = properties.getProperty("user.profile.edit.first.name");
        String lastname = properties.getProperty("user.profile.edit.last.name");
        String headline = properties.getProperty("user.profile.edit.headline");
        String website = properties.getProperty("user.profile.edit.website");
        String phone = properties.getProperty("user.profile.edit.phone");
        String linkedIn = properties.getProperty("user.profile.edit.linkedIn");
        UserProfilePage userProfilePage = dashboardPage.checkProfile();
        UserProfilePage userProfileEditIntroPage = userProfilePage.editIntro(
                firstname, lastname, headline, website, phone, linkedIn);
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(firstname));
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(lastname));
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(headline));
    }

    @Test(dependsOnMethods = {"testLogin", "testUserProfile"})
    public void testLogout() {
        // Logout
        DashboardPage dashboardPage = login();
        LogoutPage logoutPage = dashboardPage.logout();
        String logoutBodyTextContains = properties.getProperty("logout.body.text.contains");
        Assert.assertTrue(logoutPage.getBodyText().contains(logoutBodyTextContains));

        // History test (browser back button)
        logoutPage.navigateBackToDashboardPage();
        String dashboardPageTitle = properties.getProperty("dashboard.page.title");
        Assert.assertEquals(dashboardPageTitle, this.driver.getTitle());
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
