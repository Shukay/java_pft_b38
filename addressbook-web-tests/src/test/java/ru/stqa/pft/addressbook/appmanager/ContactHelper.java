package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.*;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void home() {
        click(By.linkText("home"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());

        try {
            attach(By.name("photo"), contactData.getPhoto());
        } catch (NullPointerException e) {
            return;
        }

        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void initContractModificationById(int id) {
        wd.findElement(By.xpath("//td[8]/a[@href='edit.php?id=" + id + "']")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContract(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContractById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteSelectedContract() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    private void addSelectedContractToGroup(String groupName) {
        click(By.name("to_group"));
        wd.findElement(By.xpath(String.format("//select[@name='to_group']/option[text()='%s']", groupName))).click();
        click(By.name("add"));
    }

    private void removeSelectedContractFromGroup() {
        click(By.name("remove"));
    }

    private void selectGroupOnMainPage(String groupName) {
        wd.findElement(By.xpath(String.format("//select[@name='group']/option[text()='%s']", groupName))).click();
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        initContractModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContractById(contact.getId());
        deleteSelectedContract();
        home();
    }

    public void addToGroup(int contactId, String groupName) {
        selectContractById(contactId);
        addSelectedContractToGroup(groupName);
        home();
    }

    public void removeFromGroup(int contactId, String groupName) {
        selectGroupOnMainPage(groupName);
        selectContractById(contactId);
        removeSelectedContractFromGroup();
        home();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            String lastName = element.findElement(By.xpath("./td[2]")).getText();
            String firstName = element.findElement(By.xpath("./td[3]")).getText();
            String address = element.findElement(By.xpath("./td[4]")).getText();
            String allEmails = element.findElement(By.xpath("./td[5]")).getText();
            String allPhones = element.findElement(By.xpath("./td[6]")).getText();
            int id = Integer.parseInt(element.findElement(By.xpath("./td[1]/input")).getAttribute("id"));
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).withAddress(address)
                    .withAllPhones(allPhones).withAllEmails(allEmails));
        }
        return contacts;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContractModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname).withAddress(address)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
                .withEmail(email).withEmail2(email2).withEmail3(email3);
    }
}
