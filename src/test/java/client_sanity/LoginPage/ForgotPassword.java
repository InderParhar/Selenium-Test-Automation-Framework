package client_sanity.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import client_sanity.BaseTest;
import static client_sanity.Utility.*;

@Test
@Listeners({ io.qameta.allure.testng.AllureTestNg.class })
@io.qameta.allure.Feature("Authentication")
public class ForgotPassword extends BaseTest {

        @BeforeClass
        public void navigateForgetPassword() {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-forgot-password-btn")));
        }

        @Test(priority = 1, groups = { "LoginPage","ForgotPassword" }, description = "Verify forgot password flow with incorrect email")
        @io.qameta.allure.Feature("Login Page")
        @io.qameta.allure.Story("Forgot Password")
        @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.CRITICAL)
        @io.qameta.allure.Description("Validates error handling and toast message when an invalid email is used for password recovery")
        public void recover_password_incorrect() {
                driver.findElement(By.id("gk-forgot-password-btn")).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-forget-password-submit-btn")));
                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-forget-password-go-back-btn")));

                sAssert.assertEquals(driver.findElement(By.id("gk-ucJXelKeYLm")).getText(),
                                UI_reader.getProperty("ForgotPassword.TitleText"));

                driver.findElement(By.id("gk-forget-password-email")).sendKeys(faker.internet().safeEmailAddress());
                driver.findElement(By.id("gk-forget-password-submit-btn")).click();

                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-GjdnZFrwCu")));

                sAssert.assertEquals(driver.findElement(By.id("gk-GjdnZFrwCu")).getText(),
                                UI_reader.getProperty("ForgotPassword.ToastMsg"));
                driver.navigate().refresh();
        }

        @Test(priority = 2, groups = { "LoginPage",
                        "ForgotPassword" }, description = "Verify forgot password flow with registered email")
        @io.qameta.allure.Feature("Login Page")
        @io.qameta.allure.Story("Forgot Password")
        @io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.BLOCKER)
        @io.qameta.allure.Description("Validates successful password recovery flow using a registered email address")
        public void recover_password_correct() throws InterruptedException {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-forget-password-submit-btn")));
                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-forget-password-go-back-btn")));

                sAssert.assertEquals(driver.findElement(By.id("gk-ucJXelKeYLm")).getText(),
                                UI_reader.getProperty("ForgotPassword.TitleText"));

                driver.findElement(By.id("gk-forget-password-email"))
                                .sendKeys(prop_reader.getProperty("user.username"));
                driver.findElement(By.id("gk-forget-password-submit-btn")).click();

                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-GjdnZFrwCu")));

                sAssert.assertEquals(driver.findElement(By.id("gk-GjdnZFrwCu")).getText(),
                                UI_reader.getProperty("ForgotPassword.ToastMsg"));
                driver.navigate().refresh();
        }

        @AfterClass(alwaysRun = true)
        public void returnLoginPage() {
                wait.until(ExpectedConditions.urlContains("forgot-password"));
                driver.findElement(By.id("gk-forget-password-go-back-btn")).click();

                wait.until(ExpectedConditions.urlContains("auth/login"));
                wait.until(ExpectedConditions.elementToBeClickable(By.id("gk-login-btn")));
        }
}