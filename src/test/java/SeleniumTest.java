import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import org.junit.*;

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

//    @Before
//    public void setup() {
//        System.setProperty("webdriver.chrome.driver", "/Users/lemanjavadova/Selenium/chromedriver");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-notifications");
//
//        this.driver = new ChromeDriver(options);
//        this.driver.manage().window().maximize();
//        this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS); // Set page load timeout to 30 seconds
//        this.driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS); // Set script execution timeout to 10 seconds
//        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // Set implicit wait timeout to 5 seconds
//
////        this.mainPage = new MainPage(this.driver);
////        this.properties = new Properties();
////        try (InputStream input = new FileInputStream("config.properties")) {
////            properties.load(input);
////            properties.list(System.out);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }

    @BeforeMethod
    public void initTest() {
        System.setProperty("webdriver.chrome.driver", "/Users/lemanjavadova/Selenium/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        this.driver = new ChromeDriver(options);
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
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

    //@Test
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
    //@Test
    public void testHoverOverSubMenu() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.hoverOverSubMenu();
        Assert.assertTrue(this.driver.findElement(By.xpath("//*[@id='menu-main-navigation']/li[1]/div[1]")).isDisplayed());
    }

    // Send a form
    //@Test
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
    //@Test
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

//    @Test
//    public void testMainFooter() {
//        //mainPage = new MainPage(driver);
//        MainPage mainPage = new MainPage(this.driver);
//        properties = new Properties();
//        try (InputStream input = new FileInputStream("config.properties")) {
//            properties.load(input);
//            properties.list(System.out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String footerTextContains = properties.getProperty("footer.text.contains");
//        Assert.assertTrue(mainPage.getFooterText().contains(footerTextContains));
//    }


    @Test
    public void testPageTitle() {
        String pageTitleContains = properties.getProperty("page.title.contains");
        Assert.assertTrue(mainPage.getPageTitle().contains(pageTitleContains));
    }


    @Test(dependsOnMethods = "testUpdateUserInfoPage")
    public void testUserProfilePage(UserProfilePage userProfileEditIntroPage) {
        // Logout
        LogoutPage logoutPage = userProfileEditIntroPage.logout();
        String logoutBodyTextContains = properties.getProperty("logout.body.text.contains");
        Assert.assertTrue(logoutPage.getBodyText().contains(logoutBodyTextContains));

        // History test (browser back button)
        logoutPage.navigateBackToDashboardPage();
        String dashboardPageTitle = properties.getProperty("dashboard.page.title");
        Assert.assertEquals(dashboardPageTitle, this.driver.getTitle());

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String javascriptCode = "alert('Test completed.');";
        jsExecutor.executeScript(javascriptCode);
    }

    @Test(dependsOnMethods = "testUserProfilePage")
    public void testUpdateUserInfoPage(UserProfilePage userProfilePage) {
        // Form sending with user
        String firstname = properties.getProperty("user.profile.edit.first.name");
        String lastname = properties.getProperty("user.profile.edit.last.name");
        String headline = properties.getProperty("user.profile.edit.headline");
        UserProfilePage userProfileEditIntroPage = userProfilePage.editIntro(firstname, lastname, headline);
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(firstname));
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(lastname));
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(headline));
    }

    @Test(dependsOnMethods = "testDashboardPage")
    public void testUserProfilePage(DashboardPage dashboardPage) {
        UserProfilePage userProfilePage = dashboardPage.checkProfile();
        String userProfileBodyTextContains = properties.getProperty("user.profile.body.text.contains");
        Assert.assertTrue(userProfilePage.getBodyText().contains(userProfileBodyTextContains));
    }

    @Test(dependsOnMethods = "testLoginPage")
    public void testDashboardPage(LoginPage loginPage) {
        // Fill simple form and send (eg. Login)
        String email = properties.getProperty("user.email");
        String password = properties.getProperty("user.password");
        DashboardPage dashboardPage = loginPage.logIn(email, password);
        String dashboardBodyTextContains = properties.getProperty("dashboard.body.text.contains");
        Assert.assertTrue(dashboardPage.getBodyText().contains(dashboardBodyTextContains));
    }

    @Test(dependsOnMethods = "testAccessAccountPage")
    public void testLoginPage(AccessAccountPage accessAccountPage) {
        // Assert login body text
        LoginPage loginPage = accessAccountPage.login();
        String loginBodyTextContains = properties.getProperty("login.body.text.contains");
        Assert.assertTrue(loginPage.getBodyText().contains(loginBodyTextContains));
    }

    @Test(dependsOnMethods = "testMainFooterText")
    public void testAccessAccountPage() {
        // Assert access account body text
        AccessAccountPage accessAccountPage = mainPage.accessAccount();
        String accessAccountBodyTextContains = properties.getProperty("access.account.body.text.contains");
        Assert.assertTrue(accessAccountPage.getBodyText().contains(accessAccountBodyTextContains));
    }

    @Test
    public void testMainFooterText() {
        String footerTextContains = properties.getProperty("footer.text.contains");
        Assert.assertTrue(mainPage.getFooterText().contains(footerTextContains));
    }



    //@Test
    public void LoggedInUserTests() {
        MainPage mainPage = new MainPage(this.driver);

        String footerTextContains = properties.getProperty("footer.text.contains");
        Assert.assertTrue(mainPage.getFooterText().contains(footerTextContains));

        // Reading the page title
        String pageTitleContains = properties.getProperty("page.title.contains");
        Assert.assertTrue(mainPage.getPageTitle().contains(pageTitleContains));

        // Assert access account body text
        AccessAccountPage accessAccountPage = mainPage.accessAccount();
        String accessAccountBodyTextContains = properties.getProperty("access.account.body.text.contains");
        Assert.assertTrue(accessAccountPage.getBodyText().contains(accessAccountBodyTextContains));

        // Assert login body text
        LoginPage loginPage = accessAccountPage.login();
        String loginBodyTextContains = properties.getProperty("login.body.text.contains");
        Assert.assertTrue(loginPage.getBodyText().contains(loginBodyTextContains));

        // Fill simple form and send (eg. Login)
        String email = properties.getProperty("user.email");
        String password = properties.getProperty("user.password");
        DashboardPage dashboardPage = loginPage.logIn(email, password);
        String dashboardBodyTextContains = properties.getProperty("dashboard.body.text.contains");
        Assert.assertTrue(dashboardPage.getBodyText().contains(dashboardBodyTextContains));

        UserProfilePage userProfilePage = dashboardPage.checkProfile();
        String userProfileBodyTextContains = properties.getProperty("user.profile.body.text.contains");
        Assert.assertTrue(userProfilePage.getBodyText().contains(userProfileBodyTextContains));

        // Form sending with user
        String firstname = properties.getProperty("user.profile.edit.first.name");
        String lastname = properties.getProperty("user.profile.edit.last.name");
        String headline = properties.getProperty("user.profile.edit.headline");
        UserProfilePage userProfileEditIntroPage = userProfilePage.editIntro(firstname, lastname, headline);
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(firstname));
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(lastname));
        Assert.assertTrue(userProfileEditIntroPage.getBodyText().contains(headline));

        // Logout
        LogoutPage logoutPage = userProfileEditIntroPage.logout();

        String logoutBodyTextContains = properties.getProperty("logout.body.text.contains");
        Assert.assertTrue(logoutPage.getBodyText().contains(logoutBodyTextContains));

        // History test (browser back button)
        logoutPage.navigateBackToDashboardPage();
        String dashboardPageTitle = properties.getProperty("dashboard.page.title");
        Assert.assertEquals(dashboardPageTitle, this.driver.getTitle());

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String javascriptCode = "alert('Test completed.');";
        jsExecutor.executeScript(javascriptCode);

    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
