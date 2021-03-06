package lesson8.HomeWork.KismiaTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KismiaTestHelper {

    WebDriver driver;

    public String url = "https://kismia.com/";                      //Site URL decalration

    public By loginContainer = By.cssSelector("form.js_signInForm");                        //login page locators
    public By emailInputCss = By.cssSelector("input.js_emailField");
    public By passwordInputCss = By.cssSelector("input.js_passwordField");
    public By submitButtonCss = By.cssSelector("a.js_submit");
    public By validationErrorMessage = By.xpath("//p[@class='home-page-form__error js_validationErrorMsg']");
    public By serverErrorMessage = By.xpath("//p[@class='home-page-form__error js_serverErrorMsg']");

    public By profileExpandIcon = By.cssSelector("i.icon--header-sub");                     //main page locators
    public By logoutExpandButton = By.xpath("//a[@onclick='App.auth.out();']");
    public By profileExpandButton = By.xpath("//a[@href='/profile/settings']");
    public By messagesButton = By.cssSelector("a.new-header-main-nav__link--messages");

    public By contactsListTopMessage = By.xpath("//ul[@id='standard-threads-container']/*[1]");     //message page locators
    public By mesageTextArea = By.xpath("//textarea[@name='message']");
    public By sendMesageButton = By.xpath("//button [@type='button']");
    public By sendedMesage = By.xpath("//p[@class='chat__message chat__message--from']");



    public By editProfileButton = By.cssSelector("a.js-edit-profile");                      //settings page locators
    public By profileButton = By.xpath("//div[@data-tab='profile']/span");
    public By saveSetingsBlock = By.xpath("//div[@class='settings-button-block settings-button-block--blue js_saveBlock']");
    public By maleIcon = By.xpath("//input[@value='m'][@name='gender']/..");
    public By femaleIcon = By.xpath("//input[@value='f'][@name='gender']/..");
    public By saveSatingsButton = By.xpath("(//button[contains(@class, 'js_save')])[1]");
    public By daySelectLocator = By.xpath("//select[@name='day']");
    public By monthSelectLocator = By.xpath("//select[@id='month']");
    public By yearSelectLocator = By.xpath("//select[@id='year']");


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

//    public boolean isElementPresent(By locator) {
//        try {
//            driver.findElement(locator);
//            return true;
//        } catch (NoSuchElementException ex) {
//            return false;
//        }
//    }

    public void loginKismia (String login, String password) {
        WebElement loginSection = driver.findElement(loginContainer);
        loginSection.findElement(emailInputCss).clear();
        loginSection.findElement(emailInputCss).sendKeys(login);
        loginSection.findElement(passwordInputCss).clear();
        loginSection.findElement(passwordInputCss).sendKeys(password);
        loginSection.findElement(submitButtonCss).click();
    }

    public boolean getMaleValue(By locator) {
        new WebDriverWait(driver, 5, 100)
                .until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
        driver.findElement(locator).click();
        if (driver.findElements(saveSetingsBlock).size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public void pressSaveMaleButton (By locator) throws InterruptedException {
        driver.findElement(locator).click();
        new WebDriverWait(driver, 5, 100)
                .until(ExpectedConditions.elementToBeClickable(driver.findElement(saveSatingsButton)));
        driver.findElement(saveSatingsButton).click();
        Thread.sleep(500);
    }
    public void chooseDropDowns(By locator, String index) {
        new Select(driver.findElement(locator)).selectByVisibleText(index);
    }
    public List<String> getAllOptions(By by) {                                              //Получение элементов из выпадающего списка!!
        List<String> options = new ArrayList<String>();
        for (WebElement option : new Select(driver.findElement(by)).getOptions()) {
            String txt = option.getText();
            if (option.getAttribute("value") != "") options.add(option.getText());
        }
        return options;
    }
}
