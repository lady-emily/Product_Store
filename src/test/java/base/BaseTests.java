package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.CartPage;
import pages.HomePage;
import pages.ProductSGS6;

import java.sql.SQLOutput;
import java.time.Duration;

public class BaseTests {
    protected WebDriver driver;
    protected HomePage homePage;
    protected CartPage cartPage;

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); //Run in headless mode
        options.addArguments("--incognito"); //Disable GPU acceleration

        //WebDriver Initialisation
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver(options);
        System.out.println("Initialising Chrome Browser...");
        driver.get("https://www.demoblaze.com/index.html");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println(driver.getTitle());

        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);


    }

    @AfterClass
    public void terminateSession(){
        if (driver != null){
            driver.quit();
            System.out.println("Closing Browser...");
        }
    }
}

