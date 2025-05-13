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
    private By placeOrderBtn = By.xpath("/html/body/div[6]/div/div[2]/button");
    private By totalLabel = By.id("totalp");



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

    public void clickPlaceOrder() {
        driver.findElement(placeOrderBtn).click();
    }

    public HomePage clickHome(){
        wait.until(ExpectedConditions.elementToBeClickable(home)).click();
        return new HomePage(driver);
    }
}
