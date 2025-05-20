package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * 拖放操作页面对象
 */
public class DragAndDropPage extends BasePage {
    private String pageUrl = "https://demo.guru99.com/test/drag_drop.html";
    
    // 源元素定位符
    private By bankButtonLocator = By.xpath("//li[@id='credit2']/a");
    private By salesButtonLocator = By.xpath("//li[@id='credit1']/a");
    private By amount5000Locator = By.xpath("//li[@id='fourth']/a");
    
    // 目标元素定位符
    private By bankAccountDropLocator = By.xpath("//ol[@id='bank']");
    private By salesAccountDropLocator = By.xpath("//ol[@id='loan']");
    private By amountDebitDropLocator = By.xpath("//ol[@id='amt7']");
    private By amountCreditDropLocator = By.xpath("//ol[@id='amt8']");
    
    // 验证元素
    private By perfectButtonLocator = By.xpath("//div[@id='equal']/a");
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public DragAndDropPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * 打开拖放演示页面
     * @return 当前对象实例，支持链式调用
     */
    public DragAndDropPage openPage() {
        driver.get(pageUrl);
        System.out.println("已打开拖放演示页面: " + pageUrl);
        return this;
    }
    
    /**
     * 执行拖放操作
     * @param source 源元素定位符
     * @param target 目标元素定位符
     * @return 当前对象实例，支持链式调用
     */
    public void dragAndDrop(By source, By target) {
        try {
            WebElement sourceElement = waitForElementVisible(source);
            WebElement targetElement = waitForElementVisible(target);
            
            Actions actions = new Actions(driver);
            actions.dragAndDrop(sourceElement, targetElement).perform();
            
            System.out.println("已执行拖放操作: 从 " + source + " 到 " + target);
        } catch (Exception e) {
            System.err.println("拖放操作失败: " + e.getMessage());
            
            // 尝试使用JavaScript方法执行拖放
            try {
                WebElement sourceElement = driver.findElement(source);
                WebElement targetElement = driver.findElement(target);
                
                String js = "function createEvent(typeOfEvent) {\n" +
                        "    var event = document.createEvent(\"CustomEvent\");\n" +
                        "    event.initCustomEvent(typeOfEvent, true, true, null);\n" +
                        "    event.dataTransfer = {\n" +
                        "        data: {},\n" +
                        "        setData: function(key, value) {\n" +
                        "            this.data[key] = value;\n" +
                        "        },\n" +
                        "        getData: function(key) {\n" +
                        "            return this.data[key];\n" +
                        "        }\n" +
                        "    };\n" +
                        "    return event;\n" +
                        "}\n" +
                        "\n" +
                        "function dispatchEvent(element, event, transferData) {\n" +
                        "    if (transferData !== undefined) {\n" +
                        "        event.dataTransfer = transferData;\n" +
                        "    }\n" +
                        "    if (element.dispatchEvent) {\n" +
                        "        element.dispatchEvent(event);\n" +
                        "    } else if (element.fireEvent) {\n" +
                        "        element.fireEvent(\"on\" + event.type, event);\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "function simulateHTML5DragAndDrop(element, target) {\n" +
                        "    var dragStartEvent = createEvent('dragstart');\n" +
                        "    dispatchEvent(element, dragStartEvent);\n" +
                        "    var dropEvent = createEvent('drop');\n" +
                        "    dispatchEvent(target, dropEvent, dragStartEvent.dataTransfer);\n" +
                        "    var dragEndEvent = createEvent('dragend');\n" +
                        "    dispatchEvent(element, dragEndEvent, dropEvent.dataTransfer);\n" +
                        "}\n" +
                        "\n" +
                        "var source = arguments[0];\n" +
                        "var target = arguments[1];\n" +
                        "simulateHTML5DragAndDrop(source, target);";
                
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript(js, sourceElement, targetElement);
                System.out.println("已使用JavaScript执行拖放操作");
            } catch (Exception ex) {
                System.err.println("JavaScript拖放也失败: " + ex.getMessage());
            }
        }
    }
    
    /**
     * 完成拖放操作
     */
    public void completeAllDragAndDrop() {
        // 拖放Bank到账户列
        dragAndDrop(bankButtonLocator, bankAccountDropLocator);
        
        // 拖放5000到借方金额列
        dragAndDrop(amount5000Locator, amountDebitDropLocator);
        
        // 拖放Sales到贷方账户列
        dragAndDrop(salesButtonLocator, salesAccountDropLocator);
        
        // 拖放5000到贷方金额列
        dragAndDrop(amount5000Locator, amountCreditDropLocator);
    }
    
    /**
     * 检查是否显示"Perfect!"按钮
     * @return 是否显示完成按钮
     */
    public boolean isPerfectButtonDisplayed() {
        try {
            return driver.findElement(perfectButtonLocator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}