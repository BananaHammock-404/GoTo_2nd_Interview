package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestHelper;

public class HomePage {

    WebDriverWait wait;
    WebDriver driver;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * So in this File, we're gonna separate the HomePage into 2 separate category.
     * First category is the Homepage before any popups or overlay, which is like the Landing page.
     * Second is after running some commands, so any actions that involve overlays will still be here,
     * but any commands involving some Popups we will separate it into another page (If I'm not lazy).
     */


//  Landing Page
    public boolean isTitleDisplayed() {
        return TestHelper.isElementDisplayed(wait, By.xpath("//div[@class='title']"));
    }

    public String getTitleText() {
        return TestHelper.getTextByXpath(wait,"//div[@class='title']");
    }

    public void clickBuyNowButton() throws InterruptedException {
        TestHelper.clickButtonByXpath(wait,"//a[normalize-space()='BUY NOW']");
    }

    public void clickRegisterButton() throws InterruptedException{
        TestHelper.clickButtonByXpath(wait, "//a[contains(text(),'SIGN UP  →')]");
    }


//  Overlay BuyNow Homepage
    public void setPriceField(String price) {
        TestHelper.setTextByXpath(wait, "//tbody/tr[1]/td[3]", price);
    }

    public void setBuyerNameField(String buyerName) {
        TestHelper.setTextByXpath(wait, "(//tbody/tr[1]/td[2])[2]",buyerName);
    }

    public void setBuyerEmailField(String buyerEmail) {
        TestHelper.setTextByXpath(wait,"(//tbody/tr[2]/td[2])[2]" ,buyerEmail);
    }

    public void setBuyerPhoneField(String buyerPhone) {
        TestHelper.setTextByXpath(wait,"(//tbody/tr[3]/td[2])" ,buyerPhone);
    }

    public void setBuyerCityField(String buyerCity) {
        TestHelper.setTextByXpath(wait,"(//tbody/tr[4]/td[2])" ,buyerCity);
    }

    public void setBuyerAddressField(String buyerAddress) {
        TestHelper.setTextByXpath(wait,"(//tbody/tr[5]/td[2])" ,buyerAddress);
    }

    public void setBuyerPostCodeField(String buyerPostCode) {
        TestHelper.setTextByXpath(wait,"(//tbody/tr[6]/td[2])" ,buyerPostCode);
    }

    public PaymentPopupPage clickCheckoutButton () throws InterruptedException {
        TestHelper.clickButtonByXpath(wait, "//div[@class='cart-checkout']");

        if (TestHelper.isElementDisplayed(wait, By.xpath("//div[@class='test-notice']"))) {
            return new PaymentPopupPage(driver, wait);
        }
        return null; // If Checkout process fail and Error message shows
    }

    public boolean isErrorCheckout() {
        return TestHelper.isElementDisplayed(wait, By.xpath("//div[@class='trans-status trans-error']"));
    }



}
