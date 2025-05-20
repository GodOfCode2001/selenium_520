package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * 文件下载页面对象类
 * 用于处理Guru99演示网站上的文件下载功能
 */
public class FileDownloadPage extends BasePage {
    // 元素定位符
    private By downloadButtonLocator = By.id("messenger-download");
    private String pageUrl = "https://demo.guru99.com/test/yahoo.html";
    private String directDownloadUrl = "https://demo.guru99.com/test/msgr11us.exe";
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public FileDownloadPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 打开文件下载页面
     */
    public void openPage() {
        try {
            driver.get(pageUrl);
            System.out.println("已打开文件下载页面: " + pageUrl);
            
            // 等待页面加载完成
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(downloadButtonLocator));
            
            // 验证页面标题
            String pageTitle = driver.getTitle();
            System.out.println("页面标题: " + pageTitle);
        } catch (Exception e) {
            System.err.println("打开下载页面时发生错误: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * 点击下载链接
     */
    public void clickDownloadLink() {
        try {
            // 等待元素可见和可点击
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement downloadButton = wait.until(
                ExpectedConditions.elementToBeClickable(downloadButtonLocator)
            );
            
            // 输出按钮文本和状态
            System.out.println("下载按钮文本: " + downloadButton.getText());
            System.out.println("下载按钮可见: " + downloadButton.isDisplayed());
            System.out.println("下载按钮可用: " + downloadButton.isEnabled());
            
            // 使用JavaScript点击，避免可能的问题
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", downloadButton);
            
            System.out.println("已使用JavaScript点击下载按钮");
            
            // 查看页面URL变化
            String currentUrl = driver.getCurrentUrl();
            System.out.println("点击后当前URL: " + currentUrl);
        } catch (Exception e) {
            System.err.println("点击下载链接时出错: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * 直接下载文件，绕过点击
     */
    public void directDownload() {
        try {
            System.out.println("尝试直接下载文件: " + directDownloadUrl);
            driver.get(directDownloadUrl);
            System.out.println("已发送直接下载请求");
        } catch (Exception e) {
            System.err.println("直接下载文件时出错: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * 尝试多种下载方法
     * 首先尝试常规点击，如果失败则尝试直接URL
     */
    public void tryMultipleDownloadMethods() {
        try {
            // 尝试常规下载方法
            openPage();
            clickDownloadLink();
            System.out.println("已尝试通过按钮下载");
            
            // 等待一段时间
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // 如果需要，可以在此添加检查下载是否成功的逻辑
            // 如果常规下载失败，尝试直接下载
            directDownload();
            System.out.println("已尝试通过直接URL下载");
        } catch (Exception e) {
            System.err.println("多种下载方法尝试失败: " + e.getMessage());
            // 不抛出异常，因为我们已经尝试了多种方法
        }
    }
    
    /**
     * 等待下载完成
     * @param waitTimeInSeconds 等待时间（秒）
     */
    public void waitForDownload(int waitTimeInSeconds) {
        System.out.println("等待下载完成，等待" + waitTimeInSeconds + "秒...");
        try {
            Thread.sleep(waitTimeInSeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("等待下载过程被中断: " + e.getMessage());
        }
        System.out.println("等待下载完成");
    }
}