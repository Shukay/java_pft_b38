package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import java.util.List;

public class HelperBase {
    protected WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
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

    public boolean isElementPresentInList (By locator, String text) {
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

    public boolean isElementPresent (By locator) {
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
}
