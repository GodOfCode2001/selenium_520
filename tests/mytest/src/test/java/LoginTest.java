import static org.junit.Assert.*;
import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameLocator = By.id("username");
    private By passwordLocator = By.id("password");
    private By loginButtonLocator = By.className("fa-sign-in");
    private By flashMessageLocator = By.id("flash");
    private By logoutButtonLocator = By.className("icon-signout");

    @Before
    public void setup() {
        // 方法1: 尝试自动匹配Chrome浏览器版本
        WebDriverManager.chromedriver().driverVersion("latest").setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        
        try {
            this.driver = new ChromeDriver(options);
        } catch (SessionNotCreatedException e) {
            // 方法2: 如果Chrome失败，尝试使用Firefox
            WebDriverManager.firefoxdriver().setup();
            this.driver = new FirefoxDriver();
        }
        
        this.driver.manage().window().maximize();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testLoginAndLogout() {
        this.driver.get("http://the-internet.herokuapp.com/login");

        this.driver.findElement(usernameLocator).sendKeys("tomsmith");
        this.driver.findElement(passwordLocator).sendKeys("SuperSecretPassword!");
        this.driver.findElement(loginButtonLocator).click();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessageLocator));
        String successMsg = this.driver.findElement(flashMessageLocator).getText();
        assertTrue(successMsg.contains("You logged into a secure area!"));
        if(successMsg.contains("You logged into a secure area!")){
            System.out.println("Successful Login !!!");
        }   
  
        this.driver.findElement(logoutButtonLocator).click();
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessageLocator));
        String logoutMsg = this.driver.findElement(flashMessageLocator).getText();
        assertTrue(logoutMsg.contains("You logged out of the secure area!"));
        if(logoutMsg.contains("You logged out of the secure area!")){
            System.out.println("Successful Logged out !!!");
        }

        this.driver.findElement(usernameLocator).sendKeys("123456");
        this.driver.findElement(passwordLocator).sendKeys("123456");
        this.driver.findElement(loginButtonLocator).click();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessageLocator));
        String failMsg = this.driver.findElement(flashMessageLocator).getText();
        assertTrue(failMsg.contains("Your username is invalid!"));
        if(failMsg.contains("Your username is invalid!")){
            System.out.println("Username is invalid !!!");
        } 
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}