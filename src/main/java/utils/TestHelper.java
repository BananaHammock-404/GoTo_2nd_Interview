package utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.InputStream;
import java.util.Properties;

public class TestHelper {

    /* Input Text to a textbox that has a placeholder xpath where we can recognize is */
    public static void setTextByXpath(@NotNull WebDriverWait wait, String xpath, String inputText) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).sendKeys(inputText);
    }

    /* Clicking a button that has an xpath on it */
    public static void clickButtonByXpath(@NotNull WebDriverWait wait, String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    /* Getting a text by Xpath */
    public static String getTextByXpath(@NotNull WebDriverWait wait, String xpath) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).getText();
    }

    public static void scrollToElement(@NotNull WebDriver driver, By element) {
        // identify element
        WebElement m = driver.findElement(element);
        // Javascript executor
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", m);
    }

    /*  Check if a button is displayed, if yes then do something if no then skip.
        it can take abt 1 to 4 seconds so be warned */
    public static boolean isElementDisplayed(WebDriverWait wait, By xpath) {
        try {
            boolean displayed = wait.until(ExpectedConditions.elementToBeClickable(xpath)).isDisplayed();
            System.out.println(displayed);
            return displayed; // Return True
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static final String CONFIG_FILE = "config.properties";
    private static String getProperty(String key) {
        Properties properties = new Properties();
        try (InputStream input = TestHelper.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            properties.load(input);
            return properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading configuration file.");
        }
    }
}
