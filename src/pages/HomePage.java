package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
	private WebDriver driver;
	
	private By searchBox = By.xpath("//input[@id='twotabsearchtextbox']");
	private By searchButton = By.xpath("//input[@id='nav-search-submit-button']");
	
	public HomePage(WebDriver driver) {
		this.driver =driver;
	}
	
	public void searchProduct(String productName) {
		driver.findElement(searchBox).sendKeys(productName);
		driver.findElement(searchButton).click();
	}
}
