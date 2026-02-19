package client_sanity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.github.javafaker.Faker;

import client_sanity.DB_Configuration.DB_Connection;
import client_sanity.Driver_Configuration.DriverFactory;
import client_sanity.Driver_Configuration.DriverManager;
import client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration.ChromeDriverManager;
import client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration.EdgeDriverManager;
import client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration.FirefoxDriverManager;
import client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration.SafariDriverManager;

public class Utility {
    public static Properties prop_reader = null;
    public static Properties UI_reader = null;
    public static Properties DB_Config_reader = null;
    static Logger Samwise = null;
    public static Faker faker;
    private static ThreadLocal<FluentWait<WebDriver>> wait = new ThreadLocal<>();

    DB_Connection Skynet;

    ChromeDriverManager chrome = new ChromeDriverManager();
    EdgeDriverManager edge = new EdgeDriverManager();
    FirefoxDriverManager firefox = new FirefoxDriverManager();
    SafariDriverManager safari = new SafariDriverManager();

    private ThreadLocal<DriverManager> currentManager = new ThreadLocal<>();

    public static Properties read_properties(String filename) throws IOException {
        Properties prop_reader = new Properties();

        try (InputStream input = Utility.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new FileNotFoundException(filename + " not found");
            }
            prop_reader.load(input);
            return prop_reader;
        }
    }

    public static void utilitiesSetup() throws Exception {
        prop_reader = read_properties("config/Data.properties");
        UI_reader = read_properties("UI_text.properties");
        DB_Config_reader = read_properties("dbconfig.properties");

        faker = new Faker();
        Samwise = Logger.getLogger(Utility.class.getName());
    }

    public static FluentWait<WebDriver> getWait() {
        return wait.get();
    }

    public static void setWait(WebDriver driver) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver);
        fluentWait.withTimeout(Duration.ofMillis(10000));
        fluentWait.pollingEvery(Duration.ofMillis(500));
        fluentWait.ignoring(NoSuchElementException.class);
        wait.set(fluentWait);
    }

    public void DBConnection_Setup() {
        Skynet = DB_Connection.getInstance();
    }

    public WebDriver setup_Driver(String browser_name) {

        if (DriverManager.isDriverInitialized()) {
            return DriverManager.getExistingDriver();
        }
        System.out.println("new " + browser_name + "driver");

        DriverManager manager;
        switch (browser_name.toLowerCase()) {
            case "chrome":
                manager = DriverFactory.Chrome.getDriverManager();
                break;
            case "edge":
                manager = DriverFactory.Edge.getDriverManager();
                break;
            case "firefox":
                manager = DriverFactory.Firefox.getDriverManager();
                break;
            case "safari":
                manager = DriverFactory.Safari.getDriverManager();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browser_name);
        }

        currentManager.set(manager);
        WebDriver driver = manager.getDriver();

        DriverManager.setDriverInitialized(true);
        DriverManager.setCurrentBrowser(browser_name);

        Utility.setWait(driver);

        return driver;
    }

    public void teardown_Driver() {
        DriverManager manager = currentManager.get();
        if (manager != null) {
            manager.quitDriver();
            currentManager.remove();
        }
        wait.remove();
        // Utility.removeWait();
    }

}
