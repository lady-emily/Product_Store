package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductSGS6 {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productName = By.cssSelector(".name");
    private By price = By.cssSelector(".price-container");
    private By addToCartButton = By.xpath("//a[text()='Add to cart']");
    private By cart = By.cssSelector("#navbarExample > ul > li:nth-child(4) > a");
    private By home = By.cssSelector("#navbarExample > ul > li.nav-item.active > a");

    public ProductSGS6(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    public String getPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(price)).getText();
    }

    public void clickAddToCart() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();

        // Wait for alert and accept
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        Thread.sleep(2000);

        driver.navigate().back();
    }

    public CartPage clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
        return new CartPage(driver);
    }

    public HomePage clickHome(){
        wait.until(ExpectedConditions.elementToBeClickable(home)).click();
        return new HomePage(driver);
    }

    public boolean isProductTitleVisible() {
        return driver.findElement(By.cssSelector(".name")).isDisplayed(); // adjust selector
    }

    public boolean isAddToCartVisible() {
        return driver.findElement(By.cssSelector(".btn.btn-success.btn-lg")).isDisplayed(); // adjust if needed
    }

}
