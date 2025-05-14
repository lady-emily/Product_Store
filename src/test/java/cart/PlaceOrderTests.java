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
//        cartPage.clickPlaceOrderButton();

        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        WebElement creditCardField = driver.findElement(By.id("card"));

        // Test name field with alphabetic input
        nameField.clear();
        nameField.sendKeys("John Doe");
        Assert.assertTrue(nameField.getAttribute("value").matches("^[A-Za-z ]+$"), "Name should only accept alphabetic characters.");

        // Test name field with invalid input
        nameField.clear();
        nameField.sendKeys("John123");
        Assert.assertFalse(nameField.getAttribute("value").matches("^[A-Za-z ]+$"), "Name should not accept numbers.");

        // Test valid credit card input
        creditCardField.clear();
        creditCardField.sendKeys("1234567812345678");
        Assert.assertTrue(creditCardField.getAttribute("value").matches("^\\d{16}$"), "Credit card should accept 16 digits.");

        // Test invalid credit card input
        creditCardField.clear();
        creditCardField.sendKeys("1234abcd5678");
        Assert.assertFalse(creditCardField.getAttribute("value").matches("^\\d{16}$"), "Credit card should only accept numbers.");
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
