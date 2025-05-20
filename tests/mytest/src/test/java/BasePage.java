package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

/**
 * 所有页面对象的基类
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    /**
     * 获取页面标题
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * 等待元素可见
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * 等待元素可点击
     */
    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * 输入文本
     */
    protected void enterText(By locator, String text) {
        waitForElementVisible(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }
    
    /**
     * 点击元素
     */
    protected void clickElement(By locator) {
        waitForElementClickable(locator).click();
    }
    
    /**
     * 获取元素文本
     */
    protected String getElementText(By locator) {
        return waitForElementVisible(locator).getText();
    }
    
    /**
     * 选择下拉选项
     */
    protected void selectDropdownByText(By locator, String text) {
        Select dropdown = new Select(waitForElementVisible(locator));
        dropdown.selectByVisibleText(text);
    }
    
    /**
     * 选择单选按钮
     */
    protected void selectRadioButton(By locator) {
        WebElement radioButton = waitForElementClickable(locator);
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }
    
    /**
     * 勾选复选框
     */
    protected void checkCheckbox(By locator, boolean check) {
        WebElement checkbox = waitForElementClickable(locator);
        if (check && !checkbox.isSelected() || !check && checkbox.isSelected()) {
            checkbox.click();
        }
    }
}