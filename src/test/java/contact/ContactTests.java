package contact;

import base.BaseTests;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ContactTests extends BaseTests {

    private void openContactForm() {
        homePage.clickContact();
        // Wait for modal to appear
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void testContactFormAppearsOnClick() throws InterruptedException {
        homePage.clickContact();
        WebElement modal = driver.findElement(By.xpath("//*[@id=\"exampleModal\"]/div/div/div[2]"));
        Thread.sleep(2000);
        Assert.assertTrue(modal.isDisplayed(), "Contact modal did not appear");
    }

    @Test(priority = 2)
    public void testFormValidationRejectsInvalidInput() {
        openContactForm();

        WebElement emailInput = driver.findElement(By.id("recipient-email"));
        WebElement nameInput = driver.findElement(By.id("recipient-name"));
        WebElement messageBox = driver.findElement(By.id("message-text"));

        // Provide invalid inputs
        nameInput.sendKeys("1234"); // Invalid name (if name must be alphabetical)
        emailInput.sendKeys("invalidemail"); // Missing '@' or domain
        messageBox.sendKeys(""); // Empty message

        driver.findElement(By.xpath("//button[text()='Send message']")).click();

        // Handle potential alert
        try {
            // Wait briefly to see if alert shows up
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            // If alert is present, it means the form accepted invalid input - this is a test failure
            String alertText = alert.getText();
            alert.accept(); // Close the alert

            // Fail the test because form should not have been accepted
            Assert.fail("Unexpected alert shown: " + alertText + " - form accepted invalid input.");
        } catch (TimeoutException e) {
            // No alert was shown - this is expected for invalid input
        }
    }


    @Test(priority = 3)
    public void testFormAcceptsValidInput() throws InterruptedException {
        openContactForm();

        WebElement emailInput = driver.findElement(By.id("recipient-email"));
        WebElement nameInput = driver.findElement(By.id("recipient-name"));
        WebElement messageBox = driver.findElement(By.id("message-text"));

        emailInput.clear();
        nameInput.clear();
        messageBox.clear();

        nameInput.sendKeys("Afia Mensah");
        emailInput.sendKeys("afia@example.com");
        messageBox.sendKeys("This is a valid feedback message.");

        driver.findElement(By.xpath("//button[text()='Send message']")).click();

        // Confirmation alert
        Thread.sleep(1000);
        String alertText = driver.switchTo().alert().getText();
        Assert.assertTrue(alertText.contains("Thanks") || alertText.length() > 0, "No confirmation alert received");
        driver.switchTo().alert().accept();
    }
}
