package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	
	 private static WebDriver driver;

	    public static WebDriver getDriver() {
	        if (driver == null) {
	            driver = new FirefoxDriver();
	            driver.manage().window().maximize();
	        }
	        return driver;
	    }

	    public static void quitDriver() {
	        if (driver != null) {
	            driver.quit();
	            driver = null;
	        }
	    }
}
