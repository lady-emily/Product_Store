package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By contactLink = By.linkText("Contact");
    private By aboutUsLink = By.linkText("About us");
    private By cartLink = By.cssSelector("#navbarExample > ul > li:nth-child(4) > a");
    private By loginLink = By.linkText("Log in");
    private By signUpLink = By.linkText("Sign up");
    private By cart = By.cssSelector("#navbarExample > ul > li:nth-child(4) > a");
    private By productCards = By.cssSelector(".card.h-100");
    private By productNames = By.cssSelector(".card-title a");
    private By productPrices = By.cssSelector(".card-block .price-container");
    private By productThumbnails = By.cssSelector(".card-img-top");

    private By samsungGalaxyS6 = By.xpath("//a[text()='Samsung galaxy s6']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isContactVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(contactLink)).isDisplayed();
    }

    public boolean isCartVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartLink)).isDisplayed();
    }

    public boolean isLoginVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginLink)).isDisplayed();
    }

    public boolean areProductsVisible() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards)).size() > 0;
    }

    public boolean doAllProductsHaveDetails() {
        // Wait until the entire list of product containers is visible
        List<WebElement> productCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tbodyid > div")));

        boolean allValid = true;
        int index = 1;

        for (WebElement card : productCards) {
            try {
                // Add an inner wait for each individual card’s components
                WebElement name = wait.until(ExpectedConditions.visibilityOf(card.findElement(By.cssSelector("div > div > h4"))));
                WebElement price = wait.until(ExpectedConditions.visibilityOf(card.findElement(By.cssSelector("div > div > h5"))));
                WebElement description = wait.until(ExpectedConditions.visibilityOf(card.findElement(By.cssSelector("div > div > #article"))));
                WebElement image = wait.until(ExpectedConditions.visibilityOf(card.findElement(By.cssSelector("div > a > img"))));

                boolean isValid = true;

                if (name.getText().isEmpty()) {
                    System.out.println("❌ Product " + index + " is missing name");
                    isValid = false;
                }
                if (price.getText().isEmpty()) {
                    System.out.println("❌ Product " + index + " is missing price");
                    isValid = false;
                }
                if (description.getText().isEmpty()) {
                    System.out.println("❌ Product " + index + " is missing description");
                    isValid = false;
                }
                if (!image.isDisplayed()) {
                    System.out.println("❌ Product " + index + " image is not displayed");
                    isValid = false;
                }

                if (!isValid) {
                    allValid = false;
                }

            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("⚠️ Product " + index + " is missing one or more elements entirely: " + e.getMessage());
                allValid = false;
            }

            index++;
        }

        return allValid;
    }

    public ProductSGS6 clickSamsungGalaxyS6() {
        wait.until(ExpectedConditions.elementToBeClickable(samsungGalaxyS6)).click();
        return new ProductSGS6(driver);
    }

    public CartPage clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
        return new CartPage(driver);
    }

    public void clickContact() {
        driver.findElement(By.id("contact")).click();
    }
}
