package pages;

import org.openqa.selenium.*;

/**
 * Guru99登录页面对象
 */
public class LoginPage extends BasePage {
    // 定位器
    private By emailLocator = By.id("email");
    private By passwordLocator = By.id("passwd");
    private By loginButtonLocator = By.id("SubmitLogin");
    private By forgotPasswordLocator = By.xpath("//a[contains(text(),'Forgot your password?')]");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 打开登录页面
     */
    public LoginPage openPage() {
        driver.get("https://demo.guru99.com/test/login.html");
        waitForElementVisible(emailLocator);
        System.out.println("已打开登录页面");
        return this;
    }
    
    /**
     * 输入邮箱
     */
    public LoginPage enterEmail(String email) {
        enterText(emailLocator, email);
        System.out.println("已输入邮箱: " + email);
        return this;
    }
    
    /**
     * 输入密码
     */
    public LoginPage enterPassword(String password) {
        enterText(passwordLocator, password);
        System.out.println("已输入密码");
        return this;
    }
    
    /**
     * 点击登录按钮
     */
    public SuccessPage clickLoginButton() {
        clickElement(loginButtonLocator);
        System.out.println("已点击登录按钮");
        return new SuccessPage(driver);
    }
    
    /**
     * 检查忘记密码链接是否可用
     */
    public boolean isForgotPasswordLinkDisplayed() {
        return waitForElementVisible(forgotPasswordLocator).isDisplayed();
    }
    
    /**
     * 完整登录流程
     */
    public SuccessPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickLoginButton();
    }
}