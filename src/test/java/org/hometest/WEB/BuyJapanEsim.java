package org.hometest.WEB;

import org.hometest.BaseTest;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.LandingPage;
import pages.PackageDetailsPage;


@Test
public class BuyJapanEsim extends BaseTest {

    public void BuyJapanEsimAndVerifyPurchaseDetails() {
        LandingPage landingPage = new LandingPage(driver);
        BasePage basePage = new BasePage(driver);
        PackageDetailsPage pd = new PackageDetailsPage(driver);

        //load website
        basePage.load();

        //click in the search box, clear input then type "Japan"
        //wait for elment to apear then click it
        landingPage.searchForAndClickOnJapan("Japan");
        //assert Japan offer page loaded
        softAssert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase("https://www.airalo.com/japan-esim"));
        //select first offer from list
        pd.clickBuyNowButton1();
        softAssert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase("https://www.airalo.com/japan-esim/moshi-moshi-7days-1gb"));
        //verify details
        pd.getOfferTitle();
        softAssert.assertTrue(pd.getOfferTitle().contains("Moshi Moshi"));
        softAssert.assertTrue(pd.getCoverage().contains("Japan"));
        softAssert.assertTrue(pd.getValidity().contains("1 GB"));
        softAssert.assertTrue(pd.getData().contains("7 days"));
        softAssert.assertTrue(pd.getPrice().contains("$4.50"));


    }
}
