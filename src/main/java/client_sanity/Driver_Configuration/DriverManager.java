package client_sanity.Driver_Configuration;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<Boolean> isInitialized = new ThreadLocal<>();
    private static ThreadLocal<String> currentBrowser = new ThreadLocal<>();

    protected abstract WebDriver createDriver();

    public WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(this.createDriver());
        }
        return driver.get();
    }

    public void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
        isInitialized.remove();
        currentBrowser.remove();
    }

    public static boolean isDriverInitialized(){
        Boolean initialized = isInitialized.get();
        if (initialized != null && initialized) {
            return true;
        }else{
            return false;
        }
    }

    public static void setDriverInitialized(boolean value){
        isInitialized.set(value);
    }

    public static String getCurrentBrowser(){
        return currentBrowser.get();
    }

    public static void setCurrentBrowser(String browser){
        currentBrowser.set(browser);
    }

    public static WebDriver getExistingDriver(){
        return driver.get();
    }

    public static void cleanup(){
        driver.remove();
        isInitialized.remove();
        currentBrowser.remove();
    }
}