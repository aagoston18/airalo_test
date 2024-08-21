package pages;

import Utils.Constants;
import org.openqa.selenium.WebDriver;

public class BasePage {

    public final WebDriver driver;

    public BasePage(final WebDriver driver) {
        this.driver = driver;
    }

    public void load(){
        driver.get(Constants.WEB_URL);
    }
}
