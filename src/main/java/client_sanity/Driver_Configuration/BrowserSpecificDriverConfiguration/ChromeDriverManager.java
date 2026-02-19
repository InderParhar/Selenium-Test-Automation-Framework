package client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import client_sanity.Driver_Configuration.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class ChromeDriverManager extends DriverManager {

    protected WebDriver createDriver() {
        System.out.println("Creating Chrome Driver");
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();

        return new ChromeDriver(getChromeOptions());
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--no-sandbox");
        // TODO:Chrome proxy soxy options only for WFH cases (Remove when at work)
        options.addArguments("--proxy-server=socks5://127.0.0.1:8100");

        return options;
    }
}
