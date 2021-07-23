import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {
    protected static WebDriver driver = null;
    protected static WebDriverWait wait = null;
    protected static JavascriptExecutor js = null;

    private static Properties props = new Properties();

    @BeforeAll
    public static void setUp() throws IOException {
        InputStream is = BaseTest.class.getResourceAsStream("/browser.properties");
        props.load(is);

        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(props.getProperty("chrome.arguments"));

        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, 10);

        driver.manage().window().setSize(new Dimension(900, 900));

        js = (JavascriptExecutor)driver;
    }

    @AfterAll
    public static void cleanup() {
        driver.quit();
    }

    @Attachment("screenshot")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}