package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PackageDetailsPage  extends BasePage{

    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    public PackageDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //page elements
    @FindBy(xpath = "//div[@class='c--sim_item-row sim-item-row light'][contains(.,'VALIDITY         7 Days')]") private WebElement sevenDayOffer;
    @FindBy(css = ".gap-30 > div:nth-child(1) > a:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > button:nth-child(1)") private WebElement buyNowButton1;
    @FindBy(xpath = "//div[@class='sim-detail-operator']//p[contains(text(),'Moshi Moshi')]") private WebElement offerTitle;
    @FindBy(xpath = "//ul[@data-testid='sim-detail-info-list']//p[@data-testid='COVERAGE-value']") private WebElement coverage;
    @FindBy(xpath = "//ul[@data-testid='sim-detail-info-list']//p[@data-testid='DATA-value']") private WebElement data;
    @FindBy(xpath = "//ul[@data-testid='sim-detail-info-list']//p[@data-testid='VALIDITY-value']") private WebElement validity;
    @FindBy(xpath = "//ul[@data-testid='sim-detail-info-list']//p[@data-testid='PRICE-value']") private WebElement price;

    public void clickBuyNowButton1(){
        wait.until(ExpectedConditions.visibilityOf(buyNowButton1)).click();
    }

    public String getOfferTitle(){
        return wait.until(ExpectedConditions.visibilityOf(offerTitle)).getText();
    }

    public String getCoverage(){
        return wait.until(ExpectedConditions.visibilityOf(coverage)).getText();
    }

    public String getData(){
        return wait.until(ExpectedConditions.visibilityOf(data)).getText();
    }

    public String getValidity(){
        return wait.until(ExpectedConditions.visibilityOf(validity)).getText();

    }

    public String getPrice(){
        return wait.until(ExpectedConditions.visibilityOf(price)).getText();

    }
}
