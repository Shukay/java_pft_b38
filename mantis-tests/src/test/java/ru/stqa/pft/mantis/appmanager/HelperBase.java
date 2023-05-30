package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.*;

import java.io.File;
import java.util.List;

public class HelperBase {

    protected ApplicationManager app;
    protected WebDriver wd;

    public HelperBase(ApplicationManager app) {
        this.app = app;
        this.wd = app.getDriver();
    }

    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = wd.findElement(locator).getAttribute("value");
            if (! text.equals(existingText)){
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void attach(By locator, File file) {
        if (file != null) {
            wd.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    public boolean isElementPresentInList(By locator, String text) {
        click(locator);
        List<WebElement> Elements = wd.findElements(locator);
        boolean flag = false;
        for (WebElement element: Elements) {
            if (element.getText().contains(text)) {
                flag = true;
                break;
            }
        }
    return flag;
    }

    public boolean isElementPresent(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void adminLogin(){
        login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    }

    public void login(String name, String password){
        type(By.name("username"), name);
        click(By.cssSelector("input[value=\"Войти\"]"));
        type(By.name("password"), password);
        click(By.cssSelector("input[value=\"Войти\"]"));
    }
}
