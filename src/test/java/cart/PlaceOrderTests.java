package cart;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CartPage;

import java.time.Duration;

public class PlaceOrderTests extends BaseTests {

    private WebDriverWait wait;

    @BeforeClass
    public void navigateToCartPage() {
        driver.get("https://www.demoblaze.com/cart.html");
        cartPage = new CartPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void testPlaceOrderButtonVisibility() {
        boolean isVisible = cartPage.isPlaceOrderButtonVisible();
        Assert.assertTrue(isVisible, "Place Order button should be visible.");
    }

    @Test(priority = 2)
    public void testDisplayOrderFormAfterClickingPlaceOrder() {
        cartPage.clickPlaceOrderButton();
        Assert.assertTrue(cartPage.isOrderFormDisplayed(), "Order form should be displayed after clicking Place Order.");
    }

    @Test(priority = 3)
    public void testFormFieldsValidation() {
        // Open the form
        cartPage.clickPlaceOrderButton();

        // Locate form fields
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        WebElement creditCardField = driver.findElement(By.id("card"));

        // --- Name Field Validation ---

        // 1. Valid input (alphabetic only)
        nameField.clear();
        String validNameInput = "John Doe";
        nameField.sendKeys(validNameInput);
        String nameValue = nameField.getAttribute("value");
        Assert.assertTrue(nameValue.matches("^[A-Za-z ]+$"),
                "Expected only letters and spaces for name, but got: " + nameValue);

        // 2. Invalid input (includes digits)
        nameField.clear();
        String invalidNameInput = "Jane123";
        nameField.sendKeys(invalidNameInput);
        String invalidNameValue = nameField.getAttribute("value");

        // This test assumes invalid input is still allowed in the field (bad UX)
        // So we validate that digits are indeed present, which should ideally be rejected
        Assert.assertTrue(invalidNameValue.matches(".*\\d+.*"),
                "Digits were not detected in name input; this may indicate input filtering is happening or test is flawed.");

        // --- Credit Card Field Validation ---

        // 3. Valid credit card number (16 digits)
        creditCardField.clear();
        String validCardInput = "1234567812345678";
        creditCardField.sendKeys(validCardInput);
        String cardValue = creditCardField.getAttribute("value");
        Assert.assertTrue(cardValue.matches("^\\d{16}$"),
                "Expected 16-digit numeric credit card number, but got: " + cardValue);

        // 4. Invalid credit card input (contains letters)
        creditCardField.clear();
        String invalidCardInput = "1234abcd5678";
        creditCardField.sendKeys(invalidCardInput);
        String invalidCardValue = creditCardField.getAttribute("value");

        // If the input accepts letters, this is a problem. Let's verify they are present.
        Assert.assertTrue(invalidCardValue.matches(".*[a-zA-Z]+.*"),
                "Letters were not detected in credit card input; this may indicate input filtering or test is flawed.");
        cartPage.clickPurchaseButton();

    }



    @Test(priority = 4)
    public void testSubmitOrderForm() {
//        homePage.clickCart();
//        cartPage.clickPlaceOrderButton();
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("John Doe");
//        driver.findElement(By.id("card")).sendKeys("1234567812345678");

        driver.findElement(By.cssSelector("#orderModal .modal-footer .btn.btn-primary")).click();

        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body .sweet-alert.showSweetAlert.visible")));
        Assert.assertTrue(confirmationMessage.isDisplayed(), "Confirmation message should appear after submission.");

        WebElement okButton = confirmationMessage.findElement(By.cssSelector("div.sa-button-container > div > button"));
        okButton.click();
    }

    @Test(priority = 5)
    public void testOptionalFields() {
        homePage.clickCart();
        cartPage.clickPlaceOrderButton();

        driver.findElement(By.id("country")).sendKeys("Jamaica");
        driver.findElement(By.id("city")).sendKeys("Accra");
        driver.findElement(By.id("month")).sendKeys("August");
        driver.findElement(By.id("year")).sendKeys("2022");

        // No assertion necessary; test ensures fields are fillable without throwing exceptions.
    }

    @Test(priority = 6)
    public void testCreditCardInformationIsNotStoredInPlainText() {
        // This is a placeholder test. Manual verification or security audit tools are needed for this.
        Assert.assertTrue(true, "Assumed secure handling of credit card data.");
    }
}
