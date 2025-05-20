package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

/**
 * Radio Button & Checkbox Demo页面对象类
 */
public class FormPage extends BasePage {
    // 单选按钮定位器
    private By radioOption1Locator = By.id("vfb-7-1");
    private By radioOption2Locator = By.id("vfb-7-2");
    private By radioOption3Locator = By.id("vfb-7-3");
    
    // 复选框定位器
    private By checkbox1Locator = By.id("vfb-6-0");
    private By checkbox2Locator = By.id("vfb-6-1");
    private By checkbox3Locator = By.id("vfb-6-2");
    
    public FormPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 打开表单页面
     */
    public FormPage openPage() {
        driver.get("https://demo.guru99.com/test/radio.html");
        System.out.println("已打开单选按钮和复选框测试页面");
        return this;
    }
    
    /**
     * 选择单选按钮
     * @param option 选项编号 (1, 2, 或 3)
     */
    public FormPage selectRadioButton(int option) {
        By locator;
        switch (option) {
            case 1:
                locator = radioOption1Locator;
                break;
            case 2:
                locator = radioOption2Locator;
                break;
            case 3:
                locator = radioOption3Locator;
                break;
            default:
                throw new IllegalArgumentException("无效的选项编号: " + option);
        }
        
        try {
            WebElement radioButton = waitForElementClickable(locator);
            if (!radioButton.isSelected()) {
                radioButton.click();
            }
            System.out.println("已选择选项 " + option);
        } catch (Exception e) {
            System.out.println("选择单选按钮失败: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * 勾选或取消勾选复选框
     * @param checkboxNumber 复选框编号 (1, 2, 或 3)
     * @param check 是否勾选
     */
    public FormPage toggleCheckbox(int checkboxNumber, boolean check) {
        By locator;
        switch (checkboxNumber) {
            case 1:
                locator = checkbox1Locator;
                break;
            case 2:
                locator = checkbox2Locator;
                break;
            case 3:
                locator = checkbox3Locator;
                break;
            default:
                throw new IllegalArgumentException("无效的复选框编号: " + checkboxNumber);
        }
        
        try {
            WebElement checkbox = waitForElementClickable(locator);
            // 只在当前状态与目标状态不一致时点击
            if ((check && !checkbox.isSelected()) || (!check && checkbox.isSelected())) {
                checkbox.click();
            }
            System.out.println("已" + (check ? "勾选" : "取消勾选") + "复选框 " + checkboxNumber);
        } catch (Exception e) {
            System.out.println("操作复选框失败: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * 检查单选按钮是否被选中
     * @param option 选项编号 (1, 2, 或 3)
     */
    public boolean isRadioButtonSelected(int option) {
        By locator;
        switch (option) {
            case 1:
                locator = radioOption1Locator;
                break;
            case 2:
                locator = radioOption2Locator;
                break;
            case 3:
                locator = radioOption3Locator;
                break;
            default:
                throw new IllegalArgumentException("无效的选项编号: " + option);
        }
        
        try {
            return driver.findElement(locator).isSelected();
        } catch (NoSuchElementException e) {
            System.out.println("未找到单选按钮: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 检查复选框是否被选中
     * @param checkboxNumber 复选框编号 (1, 2, 或 3)
     */
    public boolean isCheckboxSelected(int checkboxNumber) {
        By locator;
        switch (checkboxNumber) {
            case 1:
                locator = checkbox1Locator;
                break;
            case 2:
                locator = checkbox2Locator;
                break;
            case 3:
                locator = checkbox3Locator;
                break;
            default:
                throw new IllegalArgumentException("无效的复选框编号: " + checkboxNumber);
        }
        
        try {
            return driver.findElement(locator).isSelected();
        } catch (NoSuchElementException e) {
            System.out.println("未找到复选框: " + e.getMessage());
            return false;
        }
    }
}