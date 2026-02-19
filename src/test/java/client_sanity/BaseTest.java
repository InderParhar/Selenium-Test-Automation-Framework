package client_sanity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.github.javafaker.Faker;

import client_sanity.Driver_Configuration.DriverManager;

public class BaseTest {

    protected Utility baseUtility = new Utility();

    protected WebDriver driver;
    protected FluentWait<WebDriver> wait;
    protected SoftAssert sAssert;
    protected Faker faker;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() throws Exception {
        Utility.utilitiesSetup();
        baseUtility.DBConnection_Setup();
    }

    @BeforeClass
    @Parameters("browser")
    public void setupClassDriver(@Optional("chrome") String browser) {

        driver = baseUtility.setup_Driver(browser);
        wait = Utility.getWait();
        faker = Utility.faker;

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals("data:,") || currentUrl.isEmpty()) {
            System.out.println("Navigating to initial page");
            driver.get(Utility.prop_reader.getProperty("test.url"));
        } else {
            System.out.println("Continuing from: " + currentUrl);
        }
    }

    @BeforeMethod
    public void setupMethod() {
        driver = DriverManager.getExistingDriver();
        wait = Utility.getWait();
        faker = Utility.faker;
        sAssert = new SoftAssert();
    }

    @AfterSuite
    public void suiteTeardown() {
        baseUtility.teardown_Driver();
        DriverManager.cleanup();
    }

    @AfterMethod
    public void closeAssert() {
        if (sAssert != null) {
            sAssert.assertAll();
        }
    }
}