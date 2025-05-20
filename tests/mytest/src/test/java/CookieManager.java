package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

/**
 * Cookie管理工具类
 */
public class CookieManager {
    private WebDriver driver;
    
    /**
     * 构造函数
     * @param driver WebDriver实例
     */
    public CookieManager(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * 添加Cookie
     * @param name Cookie名称
     * @param value Cookie值
     */
    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        driver.manage().addCookie(cookie);
        System.out.println("已添加Cookie: " + name + "=" + value);
    }
    
    /**
     * 获取Cookie值
     * @param name Cookie名称
     * @return Cookie值，如果不存在则返回null
     */
    public String getCookieValue(String name) {
        Cookie cookie = driver.manage().getCookieNamed(name);
        return cookie != null ? cookie.getValue() : null;
    }
    
    /**
     * 删除特定Cookie
     * @param name Cookie名称
     */
    public void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
        System.out.println("已删除Cookie: " + name);
    }
    
    /**
     * 删除所有Cookie
     */
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
        System.out.println("已删除所有Cookie");
    }
    
    /**
     * 打印所有Cookie
     */
    public void printAllCookies() {
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println("当前有 " + cookies.size() + " 个Cookie:");
        for (Cookie cookie : cookies) {
            System.out.println("  " + cookie.getName() + ": " + cookie.getValue());
        }
    }
    
    /**
     * 添加禁用同意弹窗的Cookie
     */
    public void addConsentCookie() {
        // 这里根据实际网站调整Cookie名称和值
        addCookie("cookieconsent_status", "dismiss");
        addCookie("consent", "accepted");
        System.out.println("已添加禁用同意弹窗的Cookie");
    }
}