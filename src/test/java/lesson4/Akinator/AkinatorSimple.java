package lesson4.Akinator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AkinatorSimple {

    static private By playButton = By.xpath("//a[@href='/game']/span");
    static private By questionText = By.xpath("//p[@class='question-text']");
    static private By questionNumberText = By.xpath("//p[@class='question-number']");
    static private By answersText = By.xpath("(//div[@class='database-selection selector dialog-box']//ul/li)");
    static private By proposeBuble = By.xpath("//div[@class='bubble-propose bubble']");
    static private By proposeTitle = By.xpath("//span[@class='proposal-title']");
    static private By proposeYes = By.xpath("//a[@id='a_propose_yes']");
    static private By proposeNo = By.xpath("//a[@id='a_propose_no']");

    public static void main(String[] args) {

        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", "C:/webDrivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.navigate().to("https://ru.akinator.com/");

        Scanner scan = new Scanner(System.in);
        System.out.println("Akinator Game, lets start");
        System.out.println();
        WebDriverWait wait = new WebDriverWait(driver, 2, 100);
        driver.findElement(playButton).click();
        for (int i = 1; true; i++) {
            String iStr = String.valueOf(i);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(questionNumberText, iStr));
            //new FluentWait<>(driver)
            // .pollingEvery(Duration.ofMillis(100))
            //.withTimeout(Duration.ofSeconds(5))
            //.until(d -> {
            //if (questionText.equals(previuosQuestionText1)) {
            //  return(questionText);
            //}
            //});
            String questionNumberValue = driver.findElement(questionNumberText).getText();
            String questionValue = driver.findElement(questionText).getText();
            System.out.println(questionNumberValue + ". " + questionValue);

            System.out.println();
            wait.until(d -> {
                List<WebElement> answers = driver.findElements(answersText);
                if (answers.size() == 5) {
                    int k = 1;
                    for (WebElement elem : answers) {
                        System.out.println(k + ". " + elem.getText());
                        k++;
                    }
                    return true;
                } else {
                    return false;
                }
            });

//                new FluentWait<>(driver)
//                        .pollingEvery(Duration.ofMillis(100))
//                        .withTimeout(Duration.ofSeconds(5))
//                        .ignoreAll(Arrays.asList(NoSuchElementException.class, WebDriverException.class))
//                        .until(d ->
//                                driver.findElements(By.xpath("//div[@class='database-selection selector dialog-box']//ul/li")).size() == 5
//                        );

//            IntStream.rangeClosed(1, 5).forEach(answerNumber ->
//                    for (int j = 0; i < 5; i++) {
//                        new FluentWait<>(driver)
//                                .pollingEvery(Duration.ofMillis(200))
//                                .withTimeout(Duration.ofSeconds(2))
//                                .ignoring(NoSuchElementException.class, WebDriverException.class)
//                                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class='database-selection selector dialog-box']//ul/li)[" + j + "]")));
//
//                //  d -> {
//                //WebElement answer = driver.findElement(By.xpath("(//div[@class='database-selection selector dialog-box']//ul/li)[" + answerNumber + "]"));
//                //System.out.println("Answer #" + answerNumber + " :" + answer.getText());            //Здесь избыточная проверка??
//                //return true;
//                //})
//                    }
            System.out.println();
            System.out.print("Please enter your answer: ");
            int userInputAnswer = scan.nextInt();

            WebElement element = driver.findElement(By.xpath("(//div[@class='database-selection selector dialog-box']//ul/li)[" + userInputAnswer + "]"));
            new Actions(driver).moveToElement(element).perform();
            element = driver.findElement(By.xpath("(//div[@class='database-selection selector dialog-box']//ul/li)[" + userInputAnswer + "]/a[@class='current']"));
            new FluentWait<>(driver)
                    .pollingEvery(Duration.ofMillis(100))
                    .withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            if (driver.findElements(proposeBuble).size() > 0) {
                System.out.println("I think this: " + driver.findElement(proposeTitle).getText());
                System.out.println("Am I right?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Enter your answer: ");
                byte gameFinishUserAnswer = scan.nextByte();
                if (gameFinishUserAnswer == 1) {
                    WebElement yesElement = driver.findElement(proposeYes);
                    wait.until(ExpectedConditions.elementToBeClickable(yesElement));
                    yesElement.click();
                    break;
                } else {
                    WebElement noElement = driver.findElement(proposeNo);
                    wait.until(ExpectedConditions.elementToBeClickable(noElement));
                    noElement.click();
                }
            }
        }
        driver.quit();
    }
}