package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Textarea测试页面对象
 */
public class TextareaPage extends BasePage {
    private String pageUrl = "https://demo.guru99.com/test/autoit.html";
    
    // 更新为正确的元素定位符
    private By textareaLocator = By.id("input_6"); // 修改为网页上实际的ID
    private By submitButtonLocator = By.id("input_2"); // 提交按钮ID
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public TextareaPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 打开Textarea测试页面
     * @return 当前页面实例，支持链式调用
     */
    public TextareaPage openPage() {
        driver.get(pageUrl);
        System.out.println("已打开Textarea测试页面: " + pageUrl);
        return this;
    }
    
    /**
     * 在textarea中输入文本
     * @param text 要输入的文本
     * @return 当前页面实例，支持链式调用
     */
    public TextareaPage enterText(String text) {
        try {
            // 点击"Create A Course"按钮显示包含textarea的表单
            WebElement createCourseBtn = driver.findElement(By.id("getjob"));
            createCourseBtn.click();
            System.out.println("已点击'Create A Course'按钮");
            
            // 等待textarea显示
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(textareaLocator));
            
            textarea.clear();
            textarea.sendKeys(text);
            System.out.println("已在textarea中输入文本: " + (text.length() > 30 ? text.substring(0, 27) + "..." : text));
            return this;
        } catch (Exception e) {
            System.err.println("在textarea中输入文本失败: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * 获取textarea的内容
     * @return textarea中的文本
     */
    public String getTextContent() {
        try {
            WebElement textarea = driver.findElement(textareaLocator);
            return textarea.getAttribute("value");
        } catch (Exception e) {
            System.err.println("获取textarea内容失败: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * 提交表单
     * @return 当前页面实例，支持链式调用
     */
    public TextareaPage submitForm() {
        try {
            WebElement submitButton = driver.findElement(submitButtonLocator);
            submitButton.click();
            System.out.println("已点击提交按钮");
            return this;
        } catch (Exception e) {
            System.err.println("提交表单失败: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * 测试textarea验证功能
     * @return 是否显示验证消息
     */
    public boolean isValidationDisplayed() {
        try {
            // 清空textarea
            WebElement textarea = driver.findElement(textareaLocator);
            textarea.clear();
            
            // 点击其他地方触发验证
            driver.findElement(By.tagName("body")).click();
            
            // 检查验证样式是否应用
            return textarea.getAttribute("class").contains("validate-error");
        } catch (Exception e) {
            System.err.println("测试验证功能失败: " + e.getMessage());
            return false;
        }
    }
}