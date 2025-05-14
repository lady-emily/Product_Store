package homepage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductSGS6;
import base.BaseTests;

public class HomePageTests extends BaseTests {

    private HomePage homePage;

    @BeforeMethod
    public void initHomePage() {
        homePage = new HomePage(driver);
    }

    @Test(priority = 1)
    public void testHomepageLoadsWithoutLogin() {
        driver.get("https://www.demoblaze.com");
        Assert.assertTrue(homePage.areProductsVisible(), "Homepage did not load products");
    }

    @Test(priority = 2)
    public void testNavigationLinksAreVisible() {
        Assert.assertTrue(homePage.isContactVisible(), "Contact link not visible");
        Assert.assertTrue(homePage.isCartVisible(), "Cart link not visible");
        Assert.assertTrue(homePage.isLoginVisible(), "Login link not visible");
    }

    @Test(priority = 3)
    public void testAllProductsShowDetails() {
        Assert.assertTrue(homePage.doAllProductsHaveDetails(), "Some products are missing name, price, or image");
    }

    @Test(priority = 4)
    public void testClickingProductNavigatesToDetailsPage() {
        ProductSGS6 productPage = homePage.clickSamsungGalaxyS6();
        Assert.assertTrue(productPage.isProductTitleVisible(), "Product title not visible on details page");
        Assert.assertTrue(productPage.isAddToCartVisible(), "Add to Cart button not visible on details page");
    }
}
