package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
	private WebDriver driver;
	
	private By phoneField = By.xpath("//input[@id='ap_email_login']");
	private By signButton = By.xpath("//input[@id='ap_email_login']");
	private By continueButton = By.xpath("//input[@class='a-button-input']");
	private By passwordField = By.xpath("//input[@id='ap_password']");
	private By submitButton = By.xpath("//input[@id='signInSubmit']");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void login(String email,String password) {
		driver.findElement(By.xpath("//span[@class='nav-line-2 ']")).click();
		//driver.findElement(By.xpath("//span[@class='nav-action-inner']"));
		driver.findElement(phoneField).sendKeys(email);
		driver.findElement(continueButton).click();
		driver.findElement(passwordField).sendKeys(password);
		driver.findElement(submitButton).click();
	}

}
