package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LandingPage extends BasePage {

    public LandingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    //page elements
    @FindBy(xpath = "//span[contains(@class,'airalo-icon-search')]") private WebElement searchButtonIcon;
    @FindBy(xpath = "//input[contains(@class,'inp-search mobile-w-100')]") private WebElement searchBox;
    @FindBy(xpath = " //div[@data-testid='Japan-name'][contains(.,'Japan')]")    private WebElement countryJapan;


    public void searchForAndClickOnJapan(String value) {
        wait.until(ExpectedConditions.visibilityOf(searchButtonIcon));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(value);
        wait.until(ExpectedConditions.visibilityOf(countryJapan)).click();
    }


}
