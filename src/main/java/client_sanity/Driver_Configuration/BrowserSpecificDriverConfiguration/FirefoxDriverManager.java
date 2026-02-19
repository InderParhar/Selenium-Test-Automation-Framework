package client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import client_sanity.Driver_Configuration.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class FirefoxDriverManager extends DriverManager{
    
    protected WebDriver createDriver(){

        System.out.println("Creating Firefox Driver");
        WebDriverManager.getInstance(DriverManagerType.FIREFOX);

        return new FirefoxDriver(getFirefoxOptions());
    }

    private FirefoxOptions getFirefoxOptions(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("start-maximized");

        return options;
    }
}
