package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.time.Duration;

public class ApplicationManager {
    private String browser;
    protected WebDriver wd;

    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void init() {
        if (browser.equals(Browser.CHROME.browserName())) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            wd = new ChromeDriver(options);
        } else if (browser.equals(Browser.EDGE.browserName())){
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--remote-allow-origins=*");
            wd = new EdgeDriver(options);
        } else if (browser.equals(Browser.FIREFOX.browserName())){
            wd = new FirefoxDriver();
        }

        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        wd.get("http://localhost/addressbook/");

        groupHelper = new GroupHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);
        contactHelper = new ContactHelper(wd);

        sessionHelper.login("admin", "secret");
    }


    public void stop() {
        wd.findElement(By.linkText("Logout")).click();
        wd.quit();
    }


    public GroupHelper group() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }
}
