package client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import client_sanity.Driver_Configuration.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class SafariDriverManager extends DriverManager {

    protected WebDriver createDriver() {
        System.out.println("Creating Safari driver");

        WebDriverManager.getInstance(DriverManagerType.SAFARI);
        return new SafariDriver();
    }
}
