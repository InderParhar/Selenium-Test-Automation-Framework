package client_sanity.Driver_Configuration;

import client_sanity.Driver_Configuration.BrowserSpecificDriverConfiguration.*;

public enum DriverFactory {
    
    Chrome{
        public ChromeDriverManager getDriverManager(){
            return new ChromeDriverManager();
        }
    },
        Firefox{
        public FirefoxDriverManager getDriverManager(){
            return new FirefoxDriverManager();
        }
    },
    Edge{
        public EdgeDriverManager getDriverManager(){
            return new EdgeDriverManager();
        }
    },
    Safari{
        public SafariDriverManager getDriverManager(){
            return new SafariDriverManager();
        }
    };

    public abstract DriverManager getDriverManager();
}
