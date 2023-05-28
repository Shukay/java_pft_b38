package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
    private final Properties properties;
    private String browser;
    protected WebDriver wd;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

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
        wd.get(properties.getProperty("web.baseUrl"));

    }


    public void stop() {
        wd.findElement(By.linkText("Logout")).click();
        wd.quit();
    }

}
