package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions; // 添加这一行导入Actions类
import java.util.List; // 添加这一行导入List类
import org.openqa.selenium.Cookie; // 添加这一行导入Cookie类
import utils.*;

/**
 * 所有页面对象的基类
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    /**
     * 等待元素可见
     * @param locator 元素定位器
     * @return 找到的WebElement
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * 等待元素可点击
     * @param locator 元素定位器
     * @return 找到的WebElement
     */
    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * 清除并输入文本
     * @param locator 元素定位器
     * @param text 要输入的文本
     */
    protected void enterText(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * 点击元素
     * @param locator 元素定位器
     */
    protected void clickElement(By locator) {
        waitForElementClickable(locator).click();
    }
    
    /**
     * 获取元素文本
     * @param locator 元素定位器
     * @return 元素文本
     */
    protected String getElementText(By locator) {
        return waitForElementVisible(locator).getText();
    }
    
    /**
     * 获取页面标题
     * @return 页面标题
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * 获取当前URL
     * @return 当前URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * 等待URL包含指定文本
     * @param urlFragment URL片段
     */
    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }
    
    /**
     * 使用JavaScript点击元素
     * @param locator 元素定位器
     */
    protected void jsClick(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
    /**
     * 执行拖放操作
     * @param sourceLocator 源元素定位符
     * @param targetLocator 目标元素定位符
     */
    protected void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = waitForElementVisible(sourceLocator);
        WebElement target = waitForElementVisible(targetLocator);
        
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
    }

    /**
     * 在元素上执行鼠标悬停操作
     * @param locator 要悬停的元素定位符
     */
    protected void hoverOverElement(By locator) {
        WebElement element = waitForElementVisible(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    /**
     * 浏览器后退
     */
    protected void browserBack() {
        driver.navigate().back();
    }

    /**
     * 浏览器前进
     */
    protected void browserForward() {
        driver.navigate().forward();
    }

    /**
     * 刷新页面
     */
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * 添加Cookie
     * @param name Cookie名称
     * @param value Cookie值
     */
    protected void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        driver.manage().addCookie(cookie);
    }

    /**
     * 获取Cookie值
     * @param name Cookie名称
     * @return Cookie的值，如果不存在则返回null
     */
    protected String getCookieValue(String name) {
        Cookie cookie = driver.manage().getCookieNamed(name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 删除指定的Cookie
     * @param name 要删除的Cookie名称
     */
    protected void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
    }
}