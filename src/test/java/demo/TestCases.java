package demo;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static demo.wrappers.Wrappers.*;

import java.util.logging.Level;
import org.openqa.selenium.interactions.Actions;

public class TestCases {
    ChromeDriver driver;

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Starting test case 01...");
       // 1. Open Browser
       openUrl(driver, "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");
        waitSometime(driver);
        System.out.println("Step 1: URL opened successfully.");
        // 2. Enter  Crio Learner
        type(driver, By.xpath("//input[@type='text']"), "Crio Learner");
        waitSometime(driver);
        // 3. Display msg with epoch time
        long epochTime = System.currentTimeMillis() / 1000;
        String msg = "I want to be the best QA Engineer! " + epochTime;
        type(driver, By.xpath("//textarea[contains(@class, 'KHxj8b')]"), msg);
        waitSometime(driver);
        // 4. choose experience from radio button
        click(driver, By.xpath("//div[@role='radiogroup']//div[@id='i16']"));
        waitSometime(driver);
        // 5. choose java selenium testng from checkbox
        click(driver, By.xpath("//span[text()='Java']"));
        click(driver, By.xpath("//span[text()='Selenium']"));
        click(driver, By.xpath("//span[text()='TestNG']"));
        waitSometime(driver);
        //6. Choose identification from dropdown
        WebElement dropdown = driver.findElement(By.xpath("//div[contains(@class,'MocG8c')]"));
        dropdown.click();
        Thread.sleep(500); // tiny animation delay

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement option = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//div[@role='option']//span[normalize-space()='Ms']")));

        new Actions(driver).moveToElement(option).click().perform();
        waitSometime(driver);
        System.out.println("Step 6: Identification details submitted");
        //7. Provide current date i.e, dynamically calculated
        LocalDate date = LocalDate.now().minusDays(7);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        type(driver, By.xpath("//input[@type='date']"), formattedDate);
        waitSometime(driver);

        //8. Provide time that is hard coded.
        type(driver, By.xpath("//input[@aria-label='Hour']"), "07");
        type(driver, By.xpath("//input[@aria-label='Minute']"), "30");
        waitSometime(driver);
        System.out.println("Step 8: Time submitted successfully");

        click(driver, By.xpath("//span[contains(text(),'Submit')]"));
        WebElement sucesMsg = driver.findElement(By.xpath("//div[contains(text(), 'Thanks for your response, Automation Wizard!')]"));
        waitSometime(driver);
        System.out.println("Success Message= " + sucesMsg.getText());
        waitSometime(driver);
    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}
