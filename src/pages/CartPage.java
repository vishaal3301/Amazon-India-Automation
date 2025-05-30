package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private WebDriver driver;

    // Locators
  //  private By addToCartBtn = By.xpath("(//input[@id='add-to-cart-button'])[2]"); // fallback in case productPage didn't click it
    private By addToCartBtn = By.xpath("//input[@id='buy-now-button']");
    private By cartButton = By.xpath("//input[@id='buy-now-button']");
    private By cartTitle = By.cssSelector("div.sc-your-amazon-cart-is-empty h1, h1.a-spacing-mini");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Optional: click add to cart if not already done
    public void addToCart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

            // 1. Try normal "Add to Cart" button
            if (driver.findElements(By.xpath("//input[@id='buy-now-button']")).size() > 0) {
                WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@id='buy-now-button']")));
                addToCartBtn.click();
                System.out.println("✅ Product added to cart via 'add-to-cart-button'");//input[@id='buy-now-button']
                return;
            }

            // 2. Try "See All Buying Options" flow
            if (driver.findElements(By.id("buybox-see-all-buying-choices-announce")).size() > 0) {
                WebElement seeAllOptionsBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("buybox-see-all-buying-choices-announce")));
                seeAllOptionsBtn.click();
                System.out.println("🔄 Clicked 'See All Buying Options'");

                // Now try to click add to cart inside options
                WebElement altAddToCart = wait.until(ExpectedConditions.elementToBeClickable(By.name("submit.addToCart")));
                altAddToCart.click();
                System.out.println("✅ Product added to cart via buying options");
                return;
            }

            System.out.println("⚠️ 'Add to Cart' not found by any known selector.");
        } catch (Exception e) {
            System.out.println("❌ Exception while clicking Add to Cart: " + e.getMessage());
        }
    }
    // Go to cart
    public void goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-cart")));
        cartIcon.click();
        System.out.println("🛒 Navigated to cart page.");
    }

    // Verify cart is not empty
    public boolean isCartNotEmpty() {
        try {
            WebElement title = driver.findElement(cartTitle);
            String text = title.getText().toLowerCase();
            if (text.contains("empty")) {
                System.out.println("❌ Cart is empty.");
                return false;
            } else {
                System.out.println("✅ Cart contains items.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("✅ Cart contains items (no empty message found).");
            return true;
        }
    }
}
