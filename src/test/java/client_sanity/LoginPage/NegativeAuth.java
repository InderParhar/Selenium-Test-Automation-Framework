package client_sanity.LoginPage;

import static client_sanity.Utility.UI_reader;
import static client_sanity.Utility.prop_reader;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import client_sanity.BaseTest;

@Test
public class NegativeAuth extends BaseTest {
    
    @BeforeClass
    public void check_Location() {
        if (!driver.getCurrentUrl().contains("auth/login")) {
            System.out.println("Not on login page, navigating...");
            driver.get(prop_reader.getProperty("test.url"));
        }
        wait.until(ExpectedConditions.urlContains("auth/login"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-login-btn")));
    }

    @Test(priority = 1)
    public void login_incorrect() throws InterruptedException {

        driver.findElement(By.id("gk-login-email")).sendKeys(faker.internet().safeEmailAddress());
        driver.findElement(By.id("gk-login-password")).sendKeys(faker.internet().password());
        driver.findElement(By.id("gk-login-btn")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-GjdnZFrwCu")));

        sAssert.assertEquals(UI_reader.getProperty("LoginPage.IncorrectLoginMsg"),
                driver.findElement(By.id("gk-GjdnZFrwCu")).getText());

        driver.navigate().refresh();
    }

    @Test(priority = 2)
    public void login_incorrect_user() throws InterruptedException {

        driver.findElement(By.id("gk-login-email")).sendKeys(prop_reader.getProperty("user.username"));
        driver.findElement(By.id("gk-login-password")).sendKeys(faker.internet().password());
        driver.findElement(By.id("gk-login-btn")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-GjdnZFrwCu")));

        sAssert.assertEquals(UI_reader.getProperty("LoginPage.IncorrectLoginMsg"),
                driver.findElement(By.id("gk-GjdnZFrwCu")).getText());

        driver.navigate().refresh();
    }

    @Test(priority = 3)
    public void login_incorrect_password() throws InterruptedException {

        driver.findElement(By.id("gk-login-email")).sendKeys(faker.internet().safeEmailAddress());
        driver.findElement(By.id("gk-login-password")).sendKeys(prop_reader.getProperty("user.password"));
        driver.findElement(By.id("gk-login-btn")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-GjdnZFrwCu")));

        sAssert.assertEquals(UI_reader.getProperty("LoginPage.IncorrectLoginMsg"),
                driver.findElement(By.id("gk-GjdnZFrwCu")).getText());

        driver.navigate().refresh();
    }
}
