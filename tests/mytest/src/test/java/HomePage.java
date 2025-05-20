package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * 保险经纪系统主页对象(登录后)
 */
public class HomePage extends BasePage {
    // 页面元素定位符
    private By loggedInEmailLocator = By.cssSelector("div.content h4");
    private By logoutButtonLocator = By.xpath("//input[@value='Log out']");
    private By homeTabLocator = By.id("ui-id-1");
    private By requestQuotationTabLocator = By.id("ui-id-2");
    private By retrieveQuotationTabLocator = By.id("ui-id-3");
    private By profileTabLocator = By.id("ui-id-4");
    private By editProfileTabLocator = By.id("ui-id-5");
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 获取已登录用户邮箱
     * @return 用户邮箱
     */
    public String getLoggedInEmail() {
        try {
            String text = waitForElementVisible(loggedInEmailLocator).getText();
            System.out.println("已获取登录用户邮箱: " + text);
            if (text.startsWith("Welcome")) {
                return text.replace("Welcome ", "").trim();
            }
            return text;
        } catch (Exception e) {
            System.err.println("获取登录用户邮箱失败: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * 执行登出操作
     */
    public void logout() {
        try {
            // 等待登出按钮出现并点击
            wait.until(ExpectedConditions.elementToBeClickable(logoutButtonLocator));
            WebElement logoutButton = driver.findElement(logoutButtonLocator);
            logoutButton.click();
            System.out.println("已点击登出按钮");
            
            // 修改：等待任何页面变化，不强制要求特定URL
            try {
                Thread.sleep(2000); // 给页面转换一些时间
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            System.err.println("登出失败，尝试使用JavaScript点击: " + e.getMessage());
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                WebElement logoutButton = driver.findElement(logoutButtonLocator);
                js.executeScript("arguments[0].click();", logoutButton);
                
                // 等待页面变化
                Thread.sleep(2000);
            } catch (Exception ex) {
                System.err.println("JavaScript点击也失败: " + ex.getMessage());
            }
        }
    }

    /**
     * 验证是否已成功登录
     * @return 是否已登录
     */
    public boolean isLoggedIn() {
        try {
            // 增加等待时间，确保页面加载完成
            wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButtonLocator));
            boolean result = driver.findElement(logoutButtonLocator).isDisplayed();
            System.out.println("检查是否已登录: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("未找到登出按钮，判断未登录: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 点击主页选项卡
     */
    public void clickHomeTab() {
        clickElement(homeTabLocator);
        System.out.println("已点击主页选项卡");
    }
    
    /**
     * 点击请求报价选项卡
     */
    public void clickRequestQuotationTab() {
        clickElement(requestQuotationTabLocator);
        System.out.println("已点击请求报价选项卡");
    }
    
    /**
     * 点击检索报价选项卡
     */
    public void clickRetrieveQuotationTab() {
        clickElement(retrieveQuotationTabLocator);
        System.out.println("已点击检索报价选项卡");
    }
    
    /**
     * 点击个人资料选项卡
     */
    public void clickProfileTab() {
        clickElement(profileTabLocator);
        System.out.println("已点击个人资料选项卡");
    }
    
    /**
     * 点击编辑个人资料选项卡
     */
    public void clickEditProfileTab() {
        clickElement(editProfileTabLocator);
        System.out.println("已点击编辑个人资料选项卡");
    }
}