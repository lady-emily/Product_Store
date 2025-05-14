package cart;

import base.BaseTests;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductSGS6;

public class CartTests extends BaseTests {

    @Test
    public void testAddProductToCartAndVerifyTotal() throws InterruptedException {
        homePage = new pages.HomePage(driver);
        ProductSGS6 productPage = homePage.clickSamsungGalaxyS6();
        productPage.clickAddToCart();
        cartPage = homePage.clickCart();
        Assert.assertTrue(cartPage.getItemCount() >= 1, "Item was not added to the cart");
        Assert.assertTrue(cartPage.getTotalCost() > 0, "Total cost not calculated");
    }

    @Test(dependsOnMethods = "testAddProductToCartAndVerifyTotal")
    public void testDeleteProductFromCart() throws InterruptedException {
        int originalCount = cartPage.getItemCount();
        cartPage.deleteFirstItem();

        // Wait for UI to update
        Thread.sleep(2000);

        int newCount = cartPage.getItemCount();
        Assert.assertTrue(newCount < originalCount, "Product was not deleted");


        cartPage.clickHome(); // "navigate to home
        Thread.sleep(2000);
    }

    @Test(dependsOnMethods = "testDeleteProductFromCart")
    public void testAddSameProductTwiceIncreasesQuantity() throws InterruptedException {
        homePage = new pages.HomePage(driver);

        // First add
        ProductSGS6 productPage = homePage.clickSamsungGalaxyS6();
        productPage.clickAddToCart();

        // Go back to home before adding again
        productPage.clickHome(); //
        Thread.sleep(2000);

        productPage = homePage.clickSamsungGalaxyS6(); // Now safe to click again
        productPage.clickAddToCart();
        Thread.sleep(2000);

        cartPage = productPage.clickCart();
        Assert.assertTrue(cartPage.getItemCount() >= 2, "Item count did not increase with duplicate add");
    }


    @Test(dependsOnMethods = "testAddSameProductTwiceIncreasesQuantity")
    public void testPlaceOrderButtonExists() {
        Assert.assertTrue(driver.getPageSource().contains("Place Order"), "Place Order button missing");
    }
}
