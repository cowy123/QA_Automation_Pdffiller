package lesson8.HomeWork;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class KismiaTestHelper {

    WebDriver driver;

    public String url = "https://kismia.com/";                      //Site URL decalration

    public By loginContainer = By.cssSelector("form.js_signInForm");      //locators declarating
    //    public By emailInputXpath = By.xpath(".//input[@class='home-page-form__input js_emailField']");
    //    public By passwordInputXpath = By.xpath(".//input[@class='home-page-form__input js_passwordField']");
    //    public By submitButtonXpath = By.xpath(".//a[@class='home-page-form__submit js_submit']");
    public By emailInputCss = By.cssSelector("input.js_emailField");
    public By passwordInputCss = By.cssSelector("input.js_passwordField");
    public By submitButtonCss = By.cssSelector("a.js_submit");
    public By validationErrorMessage = By.cssSelector("//p[@class='home-page-form__error js_validationErrorMsg']");
    public By serverErrorMessage = By.cssSelector("//p[@class='home-page-form__error js_serverErrorMsg']");
    public By profileExpandIcon = By.cssSelector("i.icon--header-sub");
    public By logoutExpandButton = By.xpath("//a[@onclick='App.auth.out();']");


    @BeforeClass
    public void init() {
        System.setProperty("webdriver.chrome.driver", "C:/webDrivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //loginPage = new LoginPageHelper(driver);                           //Transfer created driver object in LoginPageHelper class by constructor
        //wait = new WebDriverWait(driver, 10);
        driver.navigate().to(url);
        loginKismia("RISPIHALMA@DESOZ.COM", "feedwteks");
        Assert.assertEquals(driver.getCurrentUrl(), "https://kismia.com/matches#p=1");
    }

    @AfterClass
    public void stop() {
        driver.quit();
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void loginKismia (String login, String password) {
        WebElement loginSection = driver.findElement(loginContainer);
        loginSection.findElement(emailInputCss).clear();
        loginSection.findElement(emailInputCss).sendKeys(login);
        loginSection.findElement(passwordInputCss).clear();
        loginSection.findElement(passwordInputCss).sendKeys(password);
        loginSection.findElement(submitButtonCss).click();
    }

}