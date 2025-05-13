package pages;
//import base.BaseTests;
import pages.CartPage;

//import org.testng.Assert;
//import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    //fields
    private WebDriver driver;
    private WebDriverWait wait;

    //locators
    private By contact = By.xpath("/html/body/nav/div[1]/ul/li[2]/a");
    private By aboutUs = By.xpath("/html/body/nav/div[1]/ul/li[3]/a");
    private By cart = By.cssSelector("#navbarExample > ul > li:nth-child(4) > a");
    private By addToCartButton = By.xpath("/html/body/div[5]/div/div[2]/div[2]/div/a");
    private By logIn = By.xpath("/html/body/nav/div[1]/ul/li[5]/a");
    private By signUp = By.xpath("/html/body/nav/div[1]/ul/li[8]/a");
    private By phones = By.xpath("/html/body/div[5]/div/div[1]/div/a[2]");
    private By laptops = By.xpath("/html/body/div[5]/div/div[1]/div/a[3]");
    private By monitors = By.xpath("/html/body/div[5]/div/div[1]/div/a[4]");
    private By samsungGalaxyS6 = By.xpath("/html/body/div[5]/div/div[2]/div/div[1]/div/div/h4/a");
    private By samsungPicture = By.xpath("/html/body/div[5]/div/div[2]/div/div[1]/div/a/img");
    private By samsungPrice = By.name("$360");

    //constructor
    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    //methods
    public ProductSGS6 clickSamsungGalaxyS6() {
        wait.until(ExpectedConditions.elementToBeClickable(samsungGalaxyS6)).click();
        return new ProductSGS6(driver);
    }

    public CartPage clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
        return new CartPage(driver);
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();

        // Wait for alert and accept
        driver.switchTo().alert().accept();
        driver.navigate().back(); // Return to home page after adding
    }

}
