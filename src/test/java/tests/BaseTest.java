package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;  // Using 'Method' since it's a small project, and only need to run once, even for negative cases
import org.testng.annotations.BeforeSuite;
import pages.HomePage;
import utils.WebDriverFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    HomePage homePage;
    protected WebDriverWait wait;
    protected WebDriver driver;

    @BeforeSuite
    public void setUp() {
        // Initialize WebDriver (choose your browser)
        driver = WebDriverFactory.getDriver("edge");
        // Default timeout: 10 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Initialize PageObjects
        homePage = new HomePage(driver, wait);

        // Make the browser fullscreen (optional)
        driver.manage().window().maximize();

        // Navigate to the application URL
        driver.get("https://demo.midtrans.com/#");
    }

    @AfterSuite
    public void tearDown() {
        // Quit the WebDriver instance
        WebDriverFactory.quitDriver();
    }

    private void clearBrowserCache() {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
            ((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
            ((JavascriptExecutor) driver).executeScript("document.cookie.split(';').forEach(function(c) { document.cookie = c.replace(/^\\s+/,'').replace(/=.*/, '=;expires=' + new Date().toUTCString() + ';path=/'); });");
        } else {
            System.out.println("Warning: WebDriver does not support JavaScript execution. Unable to clear browser cache.");
        }
    }
}
