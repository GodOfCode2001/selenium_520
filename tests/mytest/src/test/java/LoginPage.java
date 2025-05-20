package pages;

import org.openqa.selenium.*;

/**
 * 保险经纪系统登录页面对象
 */
public class LoginPage extends BasePage {
    // 页面URL
    private static final String PAGE_URL = "https://demo.guru99.com/insurance/v1/index.php";
    
    // 登录表单元素
    private By emailInputLocator = By.id("email");
    private By passwordInputLocator = By.id("password");
    private By loginButtonLocator = By.name("submit");
    private By registerLinkLocator = By.linkText("Register");
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * 打开登录页面
     */
    public LoginPage openPage() {
        driver.get(PAGE_URL);
        System.out.println("已打开登录页面: " + PAGE_URL);
        return this;
    }
    
    /**
     * 输入邮箱
     */
    public LoginPage enterEmail(String email) {
        enterText(emailInputLocator, email);
        System.out.println("已输入邮箱: " + email);
        return this;
    }
    
    /**
     * 输入密码
     */
    public LoginPage enterPassword(String password) {
        enterText(passwordInputLocator, password);
        System.out.println("已输入密码");
        return this;
    }
    
    /**
     * 点击登录按钮
     */
    public void clickLogin() {
        try {
            clickElement(loginButtonLocator);
            System.out.println("已点击登录按钮");
        } catch (Exception e) {
            System.err.println("点击登录按钮失败，尝试使用JavaScript点击: " + e.getMessage());
            jsClick(loginButtonLocator);
        }
    }
    
    /**
     * 执行登录
     */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        
        // 等待登录完成，页面应该变化
        try {
            Thread.sleep(2000); // 短暂等待以确保页面加载
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 点击注册链接
     */
    public void clickRegister() {
        clickElement(registerLinkLocator);
        System.out.println("已点击注册链接");
    }
    
    /**
     * 检查是否在登录页面
     */
    public boolean isOnLoginPage() {
        try {
            return driver.findElement(loginButtonLocator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}