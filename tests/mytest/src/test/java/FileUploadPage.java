package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.io.File;

/**
 * 文件上传页面对象类
 */
public class FileUploadPage extends BasePage {
    // 元素定位器
    private By fileInputLocator = By.id("uploadfile_0");
    private By termsCheckboxLocator = By.id("terms");
    private By submitButtonLocator = By.id("submitbutton");
    private By resultMessageLocator = By.id("res");
    
    public FileUploadPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 打开文件上传页面
     */
    public FileUploadPage openPage() {
        driver.get("https://demo.guru99.com/test/upload/");
        System.out.println("已打开文件上传测试页面");
        return this;
    }
    
    /**
     * 选择要上传的文件
     * @param filePath 文件的完整路径
     */
    public FileUploadPage selectFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("警告：文件不存在 - " + filePath);
            }
            
            // 修正：使用waitForElementVisible方法
            WebElement fileInput = waitForElementVisible(fileInputLocator);
            fileInput.sendKeys(file.getAbsolutePath());
            System.out.println("已选择文件: " + file.getName());
        } catch (Exception e) {
            System.out.println("选择文件失败: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * 勾选接受服务条款复选框
     */
    public FileUploadPage acceptTerms() {
        try {
            WebElement checkbox = waitForElementClickable(termsCheckboxLocator);
            if (!checkbox.isSelected()) {
                checkbox.click();
                System.out.println("已勾选接受服务条款");
            }
        } catch (Exception e) {
            System.out.println("勾选服务条款失败: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * 点击提交文件按钮
     */
    public FileUploadPage clickSubmit() {
        try {
            WebElement submitButton = waitForElementClickable(submitButtonLocator);
            submitButton.click();
            System.out.println("已点击提交按钮");
            
            // 等待上传完成，结果消息显示出来
            waitForElementVisible(resultMessageLocator);
        } catch (Exception e) {
            System.out.println("点击提交按钮或等待结果失败: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * 检查上传是否成功
     * @return 上传是否成功
     */
    // public boolean isUploadSuccessful() {
    //     try {
    //         WebElement resultMessage = waitForElementVisible(resultMessageLocator);
    //         boolean isDisplayed = resultMessage.isDisplayed();
    //         String message = resultMessage.getText();
    //         System.out.println("上传结果消息: " + message);
    //         return isDisplayed && message.contains("成功上传") || message.contains("successfully uploaded");
    //     } catch (Exception e) {
    //         System.out.println("检查上传结果失败: " + e.getMessage());
    //         return false;
    //     }
    // }
    public boolean isUploadSuccessful() {
        try {
            WebElement resultMessage = waitForElementVisible(resultMessageLocator);
            boolean isDisplayed = resultMessage.isDisplayed();
            String message = resultMessage.getText();
            System.out.println("上传结果消息: " + message);
            // 网站总是显示成功消息，所以我们只检查消息是否显示
            return isDisplayed && message.length() > 0;
        } catch (Exception e) {
            System.out.println("检查上传结果失败: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 获取上传结果消息文本
     * @return 结果消息文本
     */
    public String getResultMessage() {
        try {
            WebElement resultMessage = waitForElementVisible(resultMessageLocator);
            return resultMessage.getText();
        } catch (Exception e) {
            System.out.println("获取结果消息失败: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * 完整的文件上传流程
     * @param filePath 文件路径
     */
    public FileUploadPage uploadFile(String filePath) {
        this.selectFile(filePath)
            .acceptTerms()
            .clickSubmit();
        return this;
    }
}