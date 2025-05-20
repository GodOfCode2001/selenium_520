package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List; // 添加这一行导入List类
import utils.*;

/**
 * 鼠标悬停测试页面对象
 */
public class HoverPage extends BasePage {
    private String pageUrl = "https://demo.guru99.com/test/tooltip.html";
    
    // 使用更多定位方式增加稳定性
    private By downloadButtonLocator = By.xpath("//a[@id='download']"); // 使用XPath而不是By.id
    private By tooltipTextLocator = By.xpath("//div[@class='tooltip']/span"); // 调整工具提示的定位符
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public HoverPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 打开工具提示测试页面
     */
    public HoverPage openPage() {
        driver.get(pageUrl);
        System.out.println("已打开工具提示测试页面");
        
        // 增加页面加载等待时间
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return this;
    }
    
    /**
     * 检查页面是否包含下载按钮
     */
    public boolean hasDownloadButton() {
        try {
            // 确保页面加载完成
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
            
            // 打印页面源码以便调试
            System.out.println("页面源码包含'download'字符串: " + 
                               driver.getPageSource().contains("download"));
            
            // 尝试多种定位方式
            try {
                WebElement button = driver.findElement(By.id("download"));
                System.out.println("通过ID找到下载按钮");
                return true;
            } catch (NoSuchElementException e1) {
                try {
                    WebElement button = driver.findElement(By.linkText("Download now"));
                    System.out.println("通过链接文本找到下载按钮");
                    return true;
                } catch (NoSuchElementException e2) {
                    try {
                        WebElement button = driver.findElement(By.className("download"));
                        System.out.println("通过类名找到下载按钮");
                        return true;
                    } catch (NoSuchElementException e3) {
                        // 最后尝试XPath
                        try {
                            WebElement button = driver.findElement(By.xpath("//a[contains(@href,'download')]"));
                            System.out.println("通过包含download的href找到下载按钮");
                            return true;
                        } catch (NoSuchElementException e4) {
                            System.out.println("无法找到下载按钮");
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("检查下载按钮时出错: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 对下载按钮执行悬停操作
     */
    public HoverPage hoverOverDownloadButton() {
        try {
            // 检查是否有iframe
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            if (!iframes.isEmpty()) {
                System.out.println("页面中包含 " + iframes.size() + " 个iframe，尝试切换");
                for (int i = 0; i < iframes.size(); i++) {
                    try {
                        driver.switchTo().frame(i);
                        if (driver.findElements(downloadButtonLocator).size() > 0) {
                            System.out.println("在iframe " + i + " 中找到下载按钮");
                            break;
                        }
                        driver.switchTo().defaultContent();
                    } catch (Exception e) {
                        driver.switchTo().defaultContent();
                    }
                }
            }
            
            // 尝试不同的定位策略
            WebElement downloadButton = null;
            try {
                downloadButton = driver.findElement(By.id("download"));
            } catch (NoSuchElementException e1) {
                try {
                    downloadButton = driver.findElement(By.xpath("//a[contains(@href,'download')]"));
                } catch (NoSuchElementException e2) {
                    try {
                        downloadButton = driver.findElement(By.linkText("Download now"));
                    } catch (NoSuchElementException e3) {
                        throw new NoSuchElementException("无法找到下载按钮，尝试了多种定位方式");
                    }
                }
            }
            
            if (downloadButton != null) {
                Actions actions = new Actions(driver);
                actions.moveToElement(downloadButton).perform();
                System.out.println("已在下载按钮上执行悬停");
                
                // 等待提示框显示
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
        } catch (Exception e) {
            System.err.println("悬停操作失败: " + e.getMessage());
            // 打印页面源码以便调试
            System.out.println("页面源码: " + driver.getPageSource());
        }
        return this;
    }
    
    /**
     * 获取工具提示文本
     */
    public String getTooltipText() {
        try {
            // 尝试多种方式查找工具提示
            WebElement tooltipText = null;
            try {
                tooltipText = driver.findElement(tooltipTextLocator);
            } catch (NoSuchElementException e1) {
                try {
                    tooltipText = driver.findElement(By.cssSelector(".tooltip"));
                } catch (NoSuchElementException e2) {
                    try {
                        tooltipText = driver.findElement(By.xpath("//*[contains(@class,'tooltip')]"));
                    } catch (NoSuchElementException e3) {
                        System.err.println("找不到工具提示元素");
                        return "";
                    }
                }
            }
            
            if (tooltipText != null) {
                String text = tooltipText.getText();
                System.out.println("工具提示文本: " + text);
                return text;
            }
            return "";
        } catch (Exception e) {
            System.err.println("获取工具提示文本失败: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * 检查工具提示是否可见
     */
    public boolean isTooltipVisible() {
        try {
            // 尝试多种方式查找工具提示
            List<WebElement> tooltips = driver.findElements(By.xpath("//*[contains(@class,'tooltip')]"));
            if (!tooltips.isEmpty()) {
                for (WebElement tooltip : tooltips) {
                    if (tooltip.isDisplayed()) {
                        System.out.println("找到可见的工具提示");
                        return true;
                    }
                }
            }
            
            // 如果没找到，尝试其他定位方式
            tooltips = driver.findElements(By.cssSelector(".tooltip"));
            if (!tooltips.isEmpty()) {
                for (WebElement tooltip : tooltips) {
                    if (tooltip.isDisplayed()) {
                        return true;
                    }
                }
            }
            
            return false;
        } catch (Exception e) {
            System.err.println("检查工具提示可见性失败: " + e.getMessage());
            return false;
        }
    }
}