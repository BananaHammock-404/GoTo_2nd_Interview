package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestHelper;

import java.util.Objects;

public class BankAuthPage {
    WebDriverWait wait;
    WebDriver driver;

    public BankAuthPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public void switchToOtp() {
        WebElement otpElement = driver.findElement(By.xpath("//*[@id=\"application\"]/iframe"));
        driver.switchTo().frame(otpElement);
    }


//  Bank Authentication

    public HomePage clickOkErrorPaymentButton()  throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@class='btn full primary']");
        /**
         * The Rule here is, if the text says Back, it will close the Popup and take you back to Homepage.
         * If it says OK, it will only take you back to Credit Card Payment Popup
         */
        if (Objects.equals(TestHelper.getTextByXpath(wait, "//button[@class='btn full primary']"), "OK")) {
            return null;
        }
        return new HomePage(driver, wait);
    }

    public PaymentPopupPage clickOkAuthButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@name='ok']");
        return new PaymentPopupPage(driver, wait);
    }

    public HomePage clickOkAuthSuccessButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@name='ok']");
        return new HomePage(driver, wait);
    }

    public void clickCancelAuthButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@title='Abort authentication']");
    }

    public void clickResendAuthButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@title='Reset the timer']");
    }

    public void setOtpAuthField(String otp) {
        TestHelper.setTextByXpath(wait, "//*[@id=\"otp\"]", otp);
    }


}
