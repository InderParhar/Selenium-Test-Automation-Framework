package client_sanity.LoginPage;

import static client_sanity.Utility.prop_reader;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import client_sanity.BaseTest;
@Test
public class PositiveAuth extends BaseTest{
    @BeforeClass
    public void classSetUp(){
        wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-login-btn")));
    }

    @Test(dataProvider = "browsers")
    public void login() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();

        driver.findElement(By.cssSelector("[name='email']")).sendKeys(prop_reader.getProperty("user.username"));
        driver.findElement(By.cssSelector("[name='password']")).sendKeys(prop_reader.getProperty("user.password"));

        driver.findElement(By.cssSelector(".ui.teal.large.fluid.animated.button")).click();


        softAssert.assertAll();

        Thread.sleep(6000);
    }
}
