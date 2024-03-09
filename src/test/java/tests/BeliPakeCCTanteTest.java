package tests;

import org.testng.annotations.Test;
import pages.*;


import static org.testng.Assert.assertEquals;

public class BeliPakeCCTanteTest extends BaseTest {

    PaymentPopupPage paymentPopupPage;
    RegistrationPage registrationPage;

    String cc_num = "4811111111111114"; // getProperty("cc_number")
    String CVV = "123"; // getProperty("CVV")


    @Test(priority = 1, description = "Navigate to hell")
    public void buyPillow() throws InterruptedException {
        homePage.clickBuyNowButton();

    }

}
