package org.hometest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    protected SoftAssert softAssert;
    public final WebDriverWait explicitWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //Tell webdriver manager to download/install/prepare drivers in System Under Test to be ready for use
    //Before each test suite is run
    @BeforeSuite
    public void setupSuite() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
        WebDriverManager.iedriver().setup();
    }

    //Before test methods are called, prepare browser-specific settings
    // provide a 'browser' value to Maven, so it knows which driver to use ex "mvn test -Dbrowser=chrome"
    @BeforeMethod
    @Parameters(value = "browser")
    public void setUp(String browser) {

        //Firefox options
        final FirefoxOptions foxoptions = new FirefoxOptions();
        foxoptions.setLogLevel(FirefoxDriverLogLevel.FATAL);
        foxoptions.addPreference("log", "FATAL");
        foxoptions.addPreference("extensions.screenshots.disabled", true);
        foxoptions.addPreference("extensions.screenshots.system-disabled", true);
        foxoptions.addPreference("marionette", true);
        foxoptions.setAcceptInsecureCerts(true);

        //Chrome options
        final ChromeOptions ChromeOptions = new ChromeOptions();
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        ChromeOptions.setExperimentalOption("prefs", chromePrefs);
        ChromeOptions.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        ChromeOptions.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        ChromeOptions.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        ChromeOptions.addArguments("--no-default-browser-check");
        ChromeOptions.addArguments("--test-type=browser");
        ChromeOptions.addArguments("--allow-no-sandbox-job");
        ChromeOptions.addArguments("--disable-component-update");
        ChromeOptions.addArguments("--search-engine-choice-country");
        ChromeOptions.setAcceptInsecureCerts(true);

        //handle browser input
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(foxoptions);
        }

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(ChromeOptions);
        }
    }



    @BeforeTest
    public void startTest(){
        System.out.println("Starting test........>> "+getClass());
        softAssert = new SoftAssert();
    }

    //log action, quit the current driver
    @AfterTest
    public void tearDown () {
        System.out.println("teardown.....");
        driver.quit();
    }
}
