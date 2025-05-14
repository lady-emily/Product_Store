package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By cartRows = By.cssSelector("tr.success");
    private By home = By.cssSelector("#navbarExample > ul > li.nav-item.active > a");
    private By deleteLink = By.linkText("Delete");
    private By placeOrderBtn = By.cssSelector("#page-wrapper > div > div.col-lg-1 > button");
    private By orderForm = By.cssSelector("#orderModal > div > div > div.modal-body > form");
    private By totalLabel = By.id("totalp");
    private By purchaseBtn = By.cssSelector("#orderModal > div > div > div.modal-footer > button.btn.btn-primary");



    public int getItemCount() {
        List<WebElement> rows = driver.findElements(cartRows);
        return rows.size();
    }

    public int getTotalCost() {
        WebElement totalElement = driver.findElement(totalLabel);
        return Integer.parseInt(totalElement.getText().trim());
    }

    public void deleteFirstItem() {
        List<WebElement> deleteLinks = driver.findElements(deleteLink);
        if (!deleteLinks.isEmpty()) {
            deleteLinks.get(0).click();
        }
    }

    public boolean isPlaceOrderButtonVisible() {
        return driver.findElement(placeOrderBtn).isDisplayed();
    }

    public void clickPlaceOrderButton() {
        driver.findElement(placeOrderBtn).click();
    }

    public boolean isOrderFormDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderForm));
        return driver.findElement(orderForm).isDisplayed();
    }

    public void clickPurchaseButton(){
        driver.findElement(purchaseBtn).click();
    }

    public HomePage clickHome(){
        wait.until(ExpectedConditions.elementToBeClickable(home)).click();
        return new HomePage(driver);
    }
}
