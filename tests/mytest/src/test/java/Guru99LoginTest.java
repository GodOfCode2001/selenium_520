import static org.junit.Assert.*;
import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class Guru99LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    // 定位器
    private By emailLocator = By.id("email");
    private By passwordLocator = By.id("passwd");
    private By loginButtonLocator = By.id("SubmitLogin");
    private By forgotPasswordLocator = By.xpath("//a[contains(text(),'Forgot your password?')]");

    @Before
    public void setup() {
        // 设置WebDriver
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        
        try {
            this.driver = new ChromeDriver(options);
        } catch (SessionNotCreatedException e) {
            // 如果Chrome失败，尝试使用Firefox
            WebDriverManager.firefoxdriver().setup();
            this.driver = new FirefoxDriver();
        }
        
        this.driver.manage().window().maximize();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testSuccessfulLogin() {
        // 访问登录页面
        this.driver.get("https://demo.guru99.com/test/login.html");
        System.out.println("已打开登录页面");
        
        try {
            // 等待页面元素加载完成
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator));
            
            // 输入登录信息
            this.driver.findElement(emailLocator).sendKeys("test@example.com");
            System.out.println("已输入邮箱");
            
            this.driver.findElement(passwordLocator).sendKeys("password123");
            System.out.println("已输入密码");
            
            // 点击登录按钮
            this.driver.findElement(loginButtonLocator).click();
            System.out.println("已点击登录按钮");
            
            // 验证是否成功登录（重定向到success.html）
            this.wait.until(ExpectedConditions.urlContains("success"));
            String currentUrl = this.driver.getCurrentUrl();
            assertTrue("应该跳转到成功页面", currentUrl.contains("success.html"));
            System.out.println("测试成功：已跳转到成功页面");
            
        } catch (Exception e) {
            System.out.println("测试失败：" + e.getMessage());
            fail("登录测试失败：" + e.getMessage());
        }
    }
    
    @Test
    public void testInvalidLogin() {
        // 这个网站没有真正的验证，所有登录都会重定向到success页面
        // 此测试可以验证表单的存在性和可交互性
        this.driver.get("https://demo.guru99.com/test/login.html");
        System.out.println("已打开登录页面");
        
        try {
            // 等待页面元素加载完成
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator));
            
            // 验证输入字段可用
            WebElement emailField = this.driver.findElement(emailLocator);
            emailField.sendKeys("invalid@example.com");
            assertEquals("应该能够输入邮箱", "invalid@example.com", emailField.getAttribute("value"));
            
            WebElement passwordField = this.driver.findElement(passwordLocator);
            passwordField.sendKeys("wrongpassword");
            assertEquals("应该能够输入密码", "wrongpassword", passwordField.getAttribute("value"));
            
            // 验证登录按钮可用
            WebElement loginButton = this.driver.findElement(loginButtonLocator);
            assertTrue("登录按钮应该可用", loginButton.isEnabled());
            
            System.out.println("表单验证测试成功");
            
        } catch (Exception e) {
            System.out.println("测试失败：" + e.getMessage());
            fail("表单验证测试失败：" + e.getMessage());
        }
    }
    
    @Test
    public void testForgotPassword() {
        // 访问登录页面
        this.driver.get("https://demo.guru99.com/test/login.html");
        System.out.println("已打开登录页面");
        
        try {
            // 等待页面元素加载完成
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordLocator));
            
            // 验证"忘记密码"链接存在且可点击
            WebElement forgotPasswordLink = this.driver.findElement(forgotPasswordLocator);
            assertTrue("忘记密码链接应该可见", forgotPasswordLink.isDisplayed());
            assertTrue("忘记密码链接应该可用", forgotPasswordLink.isEnabled());
            
            System.out.println("忘记密码链接测试成功");
            
        } catch (Exception e) {
            System.out.println("测试失败：" + e.getMessage());
            fail("忘记密码链接测试失败：" + e.getMessage());
        }
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}