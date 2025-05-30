package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductPage {

    private WebDriver driver;
    private JavascriptExecutor js;

    By productList = By.cssSelector("div.s-main-slot div[data-component-type='s-search-result']");
    //private By addToCartButton = By.xpath("(//input[@id='add-to-cart-button'])[2]");
   // private By addToCartButton = By.xpath("//input[@id='buy-now-button']");
   private By addToCartButton = By.xpath("//input[@id='buy-now-button']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    public void clickSecondVisibleProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productList));
        System.out.println("🔍 Total products found: " + products.size());

        int visibleIndex = 0;
        for (WebElement product : products) {
            if (product.isDisplayed()) {
                if (visibleIndex == 1) { // 2nd visible product
                    WebElement link = null;
                    try {
                        // Try to find product link
                        try {
                            link = product.findElement(By.cssSelector("h2 a"));
                        } catch (Exception e1) {
                            link = product.findElement(By.cssSelector("a.a-link-normal.s-no-outline"));
                        }

                        // Scroll with offset (to avoid sticky header)
                        js.executeScript("window.scrollBy(0, arguments[0].getBoundingClientRect().top - 150);", link);
                        
                        // Small wait for page to stabilize
                        Thread.sleep(500);

                        // Try clicking normally
                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(link)).click();
                        } catch (Exception clickException) {
                            System.out.println("⚠️ Selenium click failed, trying JS click.");
                            // fallback to JS click
                            js.executeScript("arguments[0].click();", link);
                        }

                        System.out.println("✅ Clicked 2nd product.");
                    } catch (Exception e) {
                        System.out.println("❌ Could not click 2nd product: " + e.getMessage());
                    }
                    break;
                }
                visibleIndex++;
            }
        }

        // Switch to new tab if opened
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) {
            driver.switchTo().window(tabs.get(1));
            System.out.println("🔄 Switched to product tab: " + driver.getTitle());
        } else {
            System.out.println("❌ New tab not found.");
        }
    }
    public void addToCart() {
    	  try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    	        System.out.println("🔄 Page Title: " + driver.getTitle());
    	        System.out.println("🔗 URL: " + driver.getCurrentUrl());

    	        // 1. Try standard Add to Cart button
    	        if (driver.findElements(By.xpath("//input[@id='buy-now-button']")).size() > 0) {
    	            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='buy-now-button']")));
    	            addToCartBtn.click();
    	            System.out.println("✅ Product added to cart.");
    	            return;
    	        }

    	        // 2. Handle iframe-based Add to Cart
    	        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
    	        System.out.println("🧭 Number of iframes on page: " + iframes.size());

    	        for (WebElement iframe : iframes) {
    	            driver.switchTo().frame(iframe);
    	            List<WebElement> iframeAddBtns = driver.findElements(By.xpath("//input[@id='buy-now-button']"));
    	            if (!iframeAddBtns.isEmpty()) {
    	                iframeAddBtns.get(0).click();
    	                System.out.println("✅ Clicked 'Add to Cart' inside iframe.");
    	                driver.switchTo().defaultContent();
    	                return;
    	            }
    	            driver.switchTo().defaultContent();
    	        }

    	        // 3. Try alternate buy options
    	        try {
    	            WebElement seeAllOptions = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='buy-now-button']")));
    	            seeAllOptions.click();
    	            System.out.println("🧾 Clicked on 'See all buying options'");
    	        } catch (Exception e) {
    	            System.out.println("❌ Failed alternate buying flow: " + e.getMessage());
    	        }

    	        // 4. Fallback: generic 'Add to Cart' input buttons
    	        List<WebElement> fallbackBtns = driver.findElements(By.xpath("//input[@type='submit' and contains(@aria-labelledby, 'add-to-cart-button')]"));
    	        if (!fallbackBtns.isEmpty()) {
    	            fallbackBtns.get(0).click();
    	            System.out.println("✅ Fallback: clicked generic 'Add to Cart' button.");
    	            return;
    	        }

    	        System.out.println("⚠️ 'Add to Cart' button not found by any known selector.");
    	    } catch (Exception e) {
    	        System.out.println("❌ Failed to click 'Add to Cart': " + e.getMessage());
    	    }
    }

    public void goToCart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart")));
            cartIcon.click();
            System.out.println("🛒 Navigated to cart page.");
        } catch (Exception e) {
            System.out.println("❌ Could not navigate to cart: " + e.getMessage());
        }
    }
}
