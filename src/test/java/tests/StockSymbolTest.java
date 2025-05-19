package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test Suite for Altruist QA Assessment
 *
 * This test opens Google Finance, verifies the page title, retrieves the stock symbols under the
 * "You may be interested in" section, and compares them with a given list of expected stock symbols.
 *
 * NOTE: Per the assessment, the actual stock symbols displayed under "You may be interested in" are dynamic
 * and can change based on recent searches, trends, or personalization. Therefore, there will be mismatches between the UI and the
 * expected list.
 */

public class StockSymbolTest {

    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Immutable list of expected stock symbols for validation against the UI.
     * Marked private static final for clarity, safety, and efficiency.
     * Using ALL_CAPS and List.of() to enforce Java best practices for constants.
     */
    private static final List<String> EXPECTED_STOCK_SYMBOLS = List.of(
            "AAPL", "GOOG", "TSLA", "MSFT", "AMZN", "NVDA", "NFLX", "META", "AMD"
    );

    // Optional: Use Java Logger instead of System.out (for production scenarios)
    // private static final Logger logger = Logger.getLogger(StockSymbolTest.class.getName());

    /**
     * Initializes the Chrome WebDriver and WebDriverWait once per test class.
     * Using @BeforeClass is efficient here since a single browser session is sufficient
     * for all tests in this suite and speeds up execution compared to @BeforeMethod.
     */
    @BeforeClass
    public void setUp() {
        // Initialize ChromeOptions (can add headless, custom flags, etc. for CI/CD or scaling)
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        // Maximize the browser window for consistent UI automation
        driver.manage().window().maximize();

        // Explicit wait for element presence, robust against dynamic web content
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Validates that the "You may be interested in" stock symbols match the expected list.
     * Reports extra and missing symbols. Asserts equality (failures are expected for dynamic data).
     */
    @Test
    public void validateStocksSymbols() {
        try{

            // 1. Open Google Finance
            driver.get("https://www.google.com/finance");

            // 2. Verify page is loaded by asserting the page title contains "Google Finance"
            String title = driver.getTitle();
            Assert.assertTrue(title.contains("Google Finance"), "Page title mismatch!");

            // 3. Wait for "You may be interested in" section to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@id='smart-watchlist-title' and contains(.,'You may be interested in')]")
            ));

            // 4. Find all stock symbol elements under "You may be interested in" section
            List<WebElement> stockSymbolElements = driver.findElements(By.xpath(
                    "//section[@aria-labelledby='smart-watchlist-title']//div[contains(@class,'COaKTb')]"
            ));

            // 5. Extract the actual symbols as strings
            List<String> actualStockSymbols = new ArrayList<>();
            for (WebElement el : stockSymbolElements) {
                String symbol = el.getText().trim();
                if (!symbol.isEmpty()) {
                    actualStockSymbols.add(symbol);
                }
            }

            // Output for assessment (replace with logger for prod)
            System.out.println("Actual symbols from UI: " + actualStockSymbols);
            // logger.info("Actual symbols from UI: " + actualStockSymbols);

            // 6. Print extra symbols found in UI, but not expected
            List<String> extraSymbols = new ArrayList<>(actualStockSymbols);
            extraSymbols.removeAll(EXPECTED_STOCK_SYMBOLS);
            System.out.println("Extra symbols (in UI, not expected): " + extraSymbols);
            // logger.info("Extra symbols (in UI, not expected): " + extraSymbols);

            // 7. Print missing symbols (expected but not in UI)
            List<String> missingSymbols = new ArrayList<>(EXPECTED_STOCK_SYMBOLS);
            missingSymbols.removeAll(actualStockSymbols);
            System.out.println("Missing symbols (expected but not in UI): " + missingSymbols);
            // logger.info("Missing symbols (expected but not in UI): " + missingSymbols);

            // 8. Assert both lists are identical (test will fail if lists don't match, as expected for dynamic data)
            Assert.assertTrue(extraSymbols.isEmpty(), "Unexpected extra symbols found: " + extraSymbols);
            Assert.assertTrue(missingSymbols.isEmpty(), "Missing expected symbols: " + missingSymbols);

        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for 'You may be interested in' section: " + e.getMessage());
            Assert.fail("Timeout waiting for section.");
        } catch (StaleElementReferenceException e) {
            System.out.println("Stale element encountered during symbol extraction: " + e.getMessage());
            Assert.fail("DOM changed during symbol extraction.");
        }
    }

    /**
     * Quits the WebDriver and closes the browser after all tests have run.
     * Using @AfterClass ensures resources are properly released once per test class.
     */
    @AfterClass
    public void tearDown() {
        // Clean up: Close the browser and end the WebDriver session
        if (driver != null) driver.quit();
    }
}