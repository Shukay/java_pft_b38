package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import ru.stqa.pft.mantis.model.MailMessage;
import org.testng.SkipException;
import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.io.IOException;

import java.io.File;
import java.util.List;


public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", Browser.CHROME.browserName()));
//    protected static final ApplicationManager app = new ApplicationManager(Browser.EDGE.browserName());
//    protected static final ApplicationManager app = new ApplicationManager(Browser.FIREFOX.browserName());

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }

    protected String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        //MailMessage mailMessage = mailMessages.stream().filter((x) -> x.to.equals(email)).findAny().get();
        MailMessage mailMessage = mailMessages.stream().filter((x) -> x.to.equals(email)).reduce((x, y) -> y).get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        return app.soap().isIssueOpen(issueId);
    }

    public void skipIfNotFixedBugify(int issueId) throws IOException, ServiceException {
        if (isIssueOpenBugify(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpenBugify(int issueId) throws IOException, ServiceException {
        return app.rest().isIssueOpen(issueId);
    }

}
