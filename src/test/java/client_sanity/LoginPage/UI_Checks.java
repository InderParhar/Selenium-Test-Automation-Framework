package client_sanity.LoginPage;

import static client_sanity.Utility.UI_reader;
import static client_sanity.Utility.prop_reader;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import client_sanity.BaseTest;

@Listeners({ io.qameta.allure.testng.AllureTestNg.class })
@Test(groups = { "UI_Checks" })
public class UI_Checks extends BaseTest {

        @Test(priority = 1, groups = {
                        "LoginPage" }, description = "Verify session is created and login page loads")
        @io.qameta.allure.Description("Opens login page and validates browser session")
        @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.BLOCKER)
        public void session_check() throws IOException, InterruptedException {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-login-btn")));
                sAssert.assertTrue(
                                driver.getCurrentUrl().contains(prop_reader.getProperty("test.url")) ||
                                                driver.getCurrentUrl().contains("login"));
        }

        @Test(priority = 2, groups = {
                        "LoginPage" }, description = "Validate login page UI elements")
        @io.qameta.allure.Description("Verifies login page image and welcome text")
        @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.CRITICAL)
        public void imagecheck() throws InterruptedException {

                WebElement image = driver.findElement(By.id("gk-EWRQhXZMU"));
                sAssert.assertEquals(prop_reader.getProperty("client.image").replace("\"", ""),
                                image.getAttribute("src") + ";");
                sAssert.assertEquals(driver.findElement(By.id("gk-Col273pn01f")).getText(),
                                UI_reader.getProperty("LoginPage.Welcome"));
        }

}