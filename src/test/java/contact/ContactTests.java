package contact;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactTests extends BaseTests {

    private void openContactForm() {
        homePage.clickContact();
        // Wait for modal to appear
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void testContactLinkIsAccessible() {
        WebElement contactLink = driver.findElement(By.id("contact"));
        Assert.assertTrue(contactLink.isDisplayed(), "Contact link is not visible in nav menu");
    }

    @Test(priority = 2)
    public void testContactFormAppearsOnClick() {
        openContactForm();
        WebElement modal = driver.findElement(By.id("exampleModal"));
        Assert.assertTrue(modal.isDisplayed(), "Contact modal did not appear");
    }

    @Test(priority = 3)
    public void testFormValidationRejectsInvalidInput() {
        openContactForm();

        WebElement emailInput = driver.findElement(By.id("recipient-email"));
        WebElement nameInput = driver.findElement(By.id("recipient-name"));
        WebElement messageBox = driver.findElement(By.id("message-text"));

        // Invalid inputs
        nameInput.sendKeys("1234");
        emailInput.sendKeys("invalidemail");
        messageBox.sendKeys("");

        driver.findElement(By.xpath("//button[text()='Send message']")).click();

        // The modal should still be open due to validation failure (mock validation)
        boolean stillVisible = driver.findElement(By.id("exampleModal")).isDisplayed();
        Assert.assertTrue(stillVisible, "Form submitted with invalid data - validation may be broken");
    }

    @Test(priority = 4)
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
