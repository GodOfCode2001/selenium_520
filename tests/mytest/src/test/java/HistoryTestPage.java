package pages;

import org.openqa.selenium.*;

/**
 * 浏览器历史测试页面对象
 */
public class HistoryTestPage extends BasePage {
    private String firstPageUrl = "https://demo.guru99.com/test/";
    private String secondPageUrl = "https://demo.guru99.com/test/drag_drop.html";
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public HistoryTestPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 访问第一个页面
     * @return 当前对象实例，支持链式调用
     */
    public HistoryTestPage visitFirstPage() {
        driver.get(firstPageUrl);
        System.out.println("已访问第一个页面: " + firstPageUrl);
        return this;
    }
    
    /**
     * 访问第二个页面
     * @return 当前对象实例，支持链式调用
     */
    public HistoryTestPage visitSecondPage() {
        driver.get(secondPageUrl);
        System.out.println("已访问第二个页面: " + secondPageUrl);
        return this;
    }
    
    /**
     * 点击浏览器的后退按钮
     * @return 当前对象实例，支持链式调用
     */
    public HistoryTestPage goBack() {
        driver.navigate().back();
        System.out.println("已点击浏览器的后退按钮");
        
        // 等待页面加载
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return this;
    }
    
    /**
     * 点击浏览器的前进按钮
     * @return 当前对象实例，支持链式调用
     */
    public HistoryTestPage goForward() {
        driver.navigate().forward();
        System.out.println("已点击浏览器的前进按钮");
        
        // 等待页面加载
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return this;
    }
    
    /**
     * 刷新当前页面
     * @return 当前对象实例，支持链式调用
     */
    public HistoryTestPage refresh() {
        driver.navigate().refresh();
        System.out.println("已刷新当前页面");
        
        // 等待页面加载
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return this;
    }
    
    /**
     * 获取当前页面URL
     * @return 当前页面URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        System.out.println("当前页面URL: " + url);
        return url;
    }
    
    /**
     * 检查当前页面是否是第一个页面
     * @return 是否是第一个页面
     */
    public boolean isOnFirstPage() {
        return driver.getCurrentUrl().startsWith(firstPageUrl);
    }
    
    /**
     * 检查当前页面是否是第二个页面
     * @return 是否是第二个页面
     */
    public boolean isOnSecondPage() {
        return driver.getCurrentUrl().startsWith(secondPageUrl);
    }
}