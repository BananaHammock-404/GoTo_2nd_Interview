package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import utils.TestHelper;

import java.util.Objects;

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

    public boolean isTestNoticeDisplayed() {
        return TestHelper.isElementPresent(wait, By.xpath("//div[@class='test-notice']"));
    }

    public void switchToPopup() {
        WebElement popupElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//iframe[@id='snap-midtrans']")));
        driver.switchTo().frame(popupElement);
    }




//  Choose Payment Method

    public void clickCreditDebitButton() throws InterruptedException {
        WebElement  creditDebitButton = TestHelper.clickButtonByXpathIframe(wait, "(//a[@href='#/credit-card'])[1]");
    }

    public void clickGoPayButton() throws InterruptedException {
        TestHelper.scrollToElement(driver, By.xpath("(//a[@href='#/gopay-qris'])[1]"));
        TestHelper.clickButtonByXpath(wait, "(//a[@href='#/gopay-qris'])[1]");
    }

    public void clickClosePopupButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//div[@class='close-snap-button clickable']//*[name()='svg']");
    }


//  Credit/Debit Card

    public String getPopupTitle() {
        return TestHelper.getTextByXpath(wait, "//div[@class='page-title']");
    }

    public void clickBackToPayMethod() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//img[@alt='back']");
    }

    public void clickConfirmBackToHome() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[normalize-space()='Yes, cancel']");
    }

    public boolean isErrorInvalidPaymentDataDisplayed() {
        return TestHelper.isElementPresent(wait, By.xpath("//div[@class='cancel-modal-title']"));
    }

    public HomePage clickReturnToMerchantPage() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[contains(text(),'Return to merchant’s page')]");
        return new HomePage(driver, wait);
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
        TestHelper.clickButtonByXpath(wait, "//*[@id=\"application\"]/div/div/div[1]/div[4]/div[4]/div");
    }

    public void clickPayNowButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[normalize-space()='Pay now']");
    }

    public BankAuthPage clickPayNowSuccessButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//button[normalize-space()='Pay now']");
        return new BankAuthPage(driver, wait);
    }

    public boolean isWarningCardNumDisplayed() {
        return TestHelper.isElementPresent(wait, By.xpath("(//div[@class='card-warning text-failed'])[1]")); // this is not a good xpath but I'm lazy
    }

    public boolean isWarningExpireDateDisplayed() {
        return TestHelper.isElementPresent(wait, By.xpath("(//div[@class='card-warning text-failed'])[2]")); // this is not a good xpath but I'm lazy
    }

    public boolean isWarningCVVDisplayed() {
        return TestHelper.isElementPresent(wait, By.xpath("(//div[@class='card-warning text-failed'])[3]")); // this is not a good xpath but I'm lazy
    }

    public HomePage clickOkErrorPaymentButton()  throws InterruptedException {
        /**
         * The Rule here is, if the text says Back, it will close the Popup and take you back to Homepage.
         * If it says OK, it will only take you back to Credit Card Payment Popup
         */
        if (Objects.equals(TestHelper.getTextByXpath(wait, "//button[@class='btn full primary']"), "OK")) {
            TestHelper.clickButtonByXpath(wait, "//button[@class='btn full primary']");
            return null;
        }else {
            TestHelper.clickButtonByXpath(wait, "//button[@class='btn full primary']");
            return new HomePage(driver, wait);
        }

    }

}
