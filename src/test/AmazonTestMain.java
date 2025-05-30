package test;

import org.openqa.selenium.WebDriver;

import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utils.DriverFactory;

public class AmazonTestMain {

	public static void main(String[] args) {
		
		String driverpath = "F:\\AllSeleniumRelated\\WebDriver\\FireFoxWebDriver\\geckodriver-v0.33.0-win64\\geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", driverpath);
		
		 
		WebDriver driver = DriverFactory.getDriver();
		
		try {
		 driver.get("https://www.amazon.in/");
		 
		 LoginPage loginPage = new LoginPage(driver);
         loginPage.login("8329118980", "Vishal@1611");
         
         HomePage homePage = new HomePage(driver);
         homePage.searchProduct("oneplus 13");
         
         ProductPage productPage = new ProductPage(driver);
        // ProductPage productPage = new ProductPage(driver);
         productPage.clickSecondVisibleProduct();
         productPage.addToCart();
         productPage.goToCart();
         
         // Add to cart and view cart
         /**CartPage cartPage = new CartPage(driver);
         cartPage.addToCart();
         cartPage.goToCart();
         cartPage.isCartNotEmpty();*/
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//driver.close();
		}
	}

}
