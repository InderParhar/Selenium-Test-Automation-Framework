package client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import client_sanity.Driver_Configuration.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class EdgeDriverManager extends DriverManager{
    
    protected WebDriver createDriver(){
        System.out.println("Creating Edge Driver");
        WebDriverManager.getInstance(DriverManagerType.EDGE);

        return new EdgeDriver(getEdgeOptions());
    }

    private EdgeOptions getEdgeOptions(){
        EdgeOptions options = new EdgeOptions();
        options.addArguments("start-maximized");
        return options;
    }
}
