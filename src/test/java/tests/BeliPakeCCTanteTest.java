package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;


import static org.testng.Assert.assertEquals;

public class BeliPakeCCTanteTest extends BaseTest {

    PaymentPopupPage paymentPopupPage;
    BankAuthPage bankAuthPage;
    RegistrationPage registrationPage;


    private String title = "Midtrans Pillow";

//  Credit Card Information
    private String cc_num = "4811111111111114"; // getProperty("cc_number")
    private String errCC_num = "4811111111111184";
    private String expireDate = "1231";
    private String cvv = "123"; // getProperty("cvv")
    private String errCvv = "201";
    private String bankOtp = "112233"; // getProperty("bank_otp")
    private String errBankOtp = "332211";

//  Shopping Cart & Customer Details
    private String price = "50000";
    private String errHighPrice = "100000000000"; // 99.999.999.999 is the max value it will accept
    private String errLowPrice = "500"; // If discount is Added, system should be able to handle
    private String buyerEmail = "GoTo@goto.com";
    private String errBuyerEmail = "N0TVAL!D";

    @Test(priority = 1, description = "Navigate to Shopping Cart")
    public void navigateShopCart() throws InterruptedException {
        Assert.assertTrue(homePage.isTitleDisplayed(),"[ERROR] Page title is not displayed. Tough luck!");
        Assert.assertEquals(homePage.getTitleText(),title, "[ERROR] You might be in the wrong page buddy, come here often?");
        homePage.clickBuyNowButton();
        Assert.assertTrue(homePage.isOverlayDisplayed(), "[ERROR] Overlay was not shown correctly");
    }

    @Test(priority = 2, description = "Input & Checkout with unreasonably high price")
    public void checkoutInvalidPriceHigh() throws InterruptedException {
        // homePage.clickCancelCheckout();
        homePage.setPriceField(errHighPrice);
        homePage.clickCheckoutButton();
        Assert.assertTrue(homePage.isErrorCheckout(), "[ERROR] code did not show. Price is insane and we will not accept it!");
        // homePage.clickCloseErrMsg();  // This button is unclickable. Interesting..
    }

    @Test(priority = 3, description = "Input & Checkout w/Invalid email format")
    public void checkoutInvalidEmail() throws InterruptedException {
        homePage.clickBuyNowButton();
        homePage.setPriceField(errLowPrice);  // Set price too Low for further negative case
        homePage.setBuyerEmailField(errBuyerEmail);
        homePage.clickCheckoutButton();
//        Assert.assertFalse(homePage.isOverlayDisplayed());
        Assert.assertTrue(homePage.isErrorCheckout(), "[ERROR] code did not show. I guess email bodong fine laaa~");
    }

    @Test(priority = 4, description = "Navigate to Payment via CC/Debit")
    public void navigatePaymentCC() throws InterruptedException {
        homePage.clickBuyNowButton();
        homePage.setBuyerEmailField(buyerEmail);

        paymentPopupPage = new PaymentPopupPage(driver, wait);
        paymentPopupPage = homePage.clickCheckoutSuccessButton();

        paymentPopupPage.switchToPopup();

        paymentPopupPage.clickCreditDebitButton();
        Assert.assertEquals(paymentPopupPage.getPopupTitle(),"Credit/debit card");
    }

    @Test(priority = 5, description = "Pay without entering Mandatory Field AND Invalid Card Number")
    public void payWithFieldsNull() throws InterruptedException {
        paymentPopupPage.clickPayNowButton();
        Assert.assertTrue(paymentPopupPage.isWarningCVVDisplayed(),"[ERROR] Mandatory CVV is Null and no warning");
        Assert.assertTrue(paymentPopupPage.isWarningExpireDateDisplayed(),"[ERROR] Mandatory Expiry Date is Null and no warning");
        Assert.assertTrue(paymentPopupPage.isWarningCardNumDisplayed(),"[ERROR] Mandatory Card Number is Null and no warning");
        paymentPopupPage.setCardNumberField(errCC_num);
        Assert.assertTrue(paymentPopupPage.isWarningCardNumDisplayed(),"[ERROR] Card Number not Valid and no warning");
        paymentPopupPage.clickBackToPayMethod();
        paymentPopupPage.clickConfirmBackToHome();
    }

    @Test(priority = 6, description = "Price is minus (under Rp 0,00 because of discount)")
    public void discountPriceTooLow() throws InterruptedException {
        paymentPopupPage.clickCreditDebitButton();

        paymentPopupPage.setCardNumberField(cc_num);
        paymentPopupPage.setCardExpireField(expireDate);
        paymentPopupPage.setCVVField(cvv);
        paymentPopupPage.clickPromoFlashButton();
        paymentPopupPage.clickPayNowButton();
        Assert.assertTrue(paymentPopupPage.isErrorInvalidPaymentDataDisplayed());

        homePage = paymentPopupPage.clickReturnToMerchantPage();
        homePage.switchToMainContent();
        Thread.sleep(2000);
    }

    @Test(priority = 7, description = "Wrong OTP")
    public void wrongBankOtp() throws InterruptedException {
        homePage.clickBuyNowButton();
        homePage.setPriceField(price);  // Fix price error
        paymentPopupPage = homePage.clickCheckoutSuccessButton();

        paymentPopupPage.switchToPopup();
        paymentPopupPage.clickCreditDebitButton();

        paymentPopupPage.setCardNumberField(cc_num);
        paymentPopupPage.setCardExpireField(expireDate);
        paymentPopupPage.setCVVField(cvv);
        paymentPopupPage.clickPromoFlashButton();
        bankAuthPage = paymentPopupPage.clickPayNowSuccessButton();  // First Try
        bankAuthPage.switchToOtp();

        bankAuthPage.setOtpAuthField(errBankOtp);
        paymentPopupPage = bankAuthPage.clickOkAuthButton();
        paymentPopupPage.switchToPopup();

        paymentPopupPage.clickOkErrorPaymentButton();
    }

    @Test(priority = 8, description = "Fail Auth OTP 4 times")
    public void wrong4TimesOtp() throws InterruptedException {
        bankAuthPage = paymentPopupPage.clickPayNowSuccessButton();  // Second Try
        bankAuthPage.switchToOtp();

        bankAuthPage.setOtpAuthField(errBankOtp);
        paymentPopupPage = bankAuthPage.clickOkAuthButton();
        paymentPopupPage.switchToPopup();

        paymentPopupPage.clickOkErrorPaymentButton();

        bankAuthPage = paymentPopupPage.clickPayNowSuccessButton();  // Third Try
        bankAuthPage.switchToOtp();

        bankAuthPage.setOtpAuthField(errBankOtp);
        paymentPopupPage = bankAuthPage.clickOkAuthButton();
        paymentPopupPage.switchToPopup();

        paymentPopupPage.clickOkErrorPaymentButton();

        bankAuthPage = paymentPopupPage.clickPayNowSuccessButton();  // Fourth and Last Try
        bankAuthPage.switchToOtp();

        bankAuthPage.setOtpAuthField(errBankOtp);
        paymentPopupPage = bankAuthPage.clickOkAuthButton();
        paymentPopupPage.switchToPopup();

        homePage = paymentPopupPage.clickOkErrorPaymentButton();
        homePage.switchToMainContent();
    }

    @Test(priority = 9, description = "SUCCESS ALL DATA CORRECT")
    public void endToEndSuccess() throws InterruptedException {
        homePage.clickBuyNowButton();
        paymentPopupPage = homePage.clickCheckoutSuccessButton();
        paymentPopupPage.switchToPopup();

        paymentPopupPage.clickCreditDebitButton();
        paymentPopupPage.setCardNumberField(cc_num);
        paymentPopupPage.setCardExpireField(expireDate);
        paymentPopupPage.setCVVField(cvv);

        bankAuthPage = paymentPopupPage.clickPayNowSuccessButton();
        bankAuthPage.switchToOtp();

        bankAuthPage.setOtpAuthField(bankOtp);
        homePage = bankAuthPage.clickOkAuthSuccessButton();
        homePage.switchToMainContent();
        Assert.assertTrue(homePage.isSuccessPaymentVisible(), "Payment wasnt successful");
        // Assert the price bought with promo or no
        // Assert the success messages
        // Asseert everything I guess..
    }

}
