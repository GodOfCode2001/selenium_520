package pages;

import org.openqa.selenium.*;

/**
 * 登录成功页面对象
 */
public class SuccessPage extends BasePage {
    // 定位器
    private By successMessageLocator = By.tagName("h3");
    
    public SuccessPage(WebDriver driver) {
        super(driver);
        waitForElementVisible(successMessageLocator);
    }
    
    /**
     * 获取成功消息
     */
    public String getSuccessMessage() {
        return getElementText(successMessageLocator);
    }
    
    /**
     * 验证URL包含成功标识
     */
    public boolean isOnSuccessPage() {
        return driver.getCurrentUrl().contains("success.html");
    }
}