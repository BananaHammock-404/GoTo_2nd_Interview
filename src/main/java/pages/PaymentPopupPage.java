package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestHelper;

public class PaymentPopupPage {

    WebDriverWait wait;
    WebDriver driver;

    public PaymentPopupPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * As said in HomePage.java I will put most Popup methods here
     * It will be separated by comments down below for every Popup
     */

//  Choose Payment Method

    public void clickCreditDebitButton() throws InterruptedException {
        TestHelper.scrollToElement(driver, By.xpath("(//a[@href='#/credit-card'])[1]"));
        TestHelper.clickButtonByXpath(wait, "(//a[@href='#/credit-card'])[1]");
    }

    public void clickGoPayButton() throws InterruptedException {
        TestHelper.scrollToElement(driver, By.xpath("(//a[@href='#/gopay-qris'])[1]"));
        TestHelper.clickButtonByXpath(wait, "(//a[@href='#/gopay-qris'])[1]");
    }

    public void clickClosePopupButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//div[@class='close-snap-button clickable']//*[name()='svg']");
    }


//  Credit/Debit Card

    public void clickBackToPayMethod() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//img[@alt='back']");
    }

    public void setCardNumberField(String cardNumber) {
        TestHelper.setTextByXpath(wait, "//input[@autocomplete='cc-number']" ,cardNumber);
    }

    public void setCardExpireField(String expireDate) {
        TestHelper.setTextByXpath(wait, "//input[@id='card-expiry']" ,expireDate);
    }

    public void setCVVField(String CVV) {
        TestHelper.setTextByXpath(wait, "//input[@id='card-cvv']" ,CVV);
    }

    public void clickPromoFlashButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//label[@for='690']//span");
    }

    public void clickPromoTestingButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//label[@for='628']//span");
    }

    public void clickNoPromoButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//label[@for='no-promo']//span");
    }

    public void clickPayNowButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[normalize-space()='Pay now']");
    }

    public boolean isWarningCardNumDisplayed() {
        return TestHelper.isElementDisplayed(wait, By.xpath("(//div[@class='card-warning text-failed'])[1]")); // this is not a good xpath but I'm lazy
    }

    public boolean isWarningExpireDateDisplayed() {
        return TestHelper.isElementDisplayed(wait, By.xpath("(//div[@class='card-warning text-failed'])[2]")); // this is not a good xpath but I'm lazy
    }

    public boolean isWarningCVVDisplayed() {
        return TestHelper.isElementDisplayed(wait, By.xpath("(//div[@class='card-warning text-failed'])[3]")); // this is not a good xpath but I'm lazy
    }


//  Bank Authentication

    public void clickOkErrorPaymentButton()  throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@class='btn full primary']");
        /**
         * The Rule here is, if the text says Back, it will close the Popup and take you back to Homepage.
         * If it says OK, it will only take you back to Credit Card Payment Popup
         */
        if (TestHelper.getTextByXpath(wait,"//button[@class='btn full primary']") == "OK") {
            return;
        }
    }

    public void clickOkAuthButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@name='ok']");
    }

    public void clickCancelAuthButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@title='Abort authentication']");
    }

    public void clickResendAuthButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[@title='Reset the timer']");
    }

    public void setOtpAuthField(String otp) {
        TestHelper.setTextByXpath(wait, "//input[@id='otp']", otp);
    }



}
