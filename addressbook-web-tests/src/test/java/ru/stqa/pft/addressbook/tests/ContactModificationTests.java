package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().creationContact(new ContactData("test1", "test2", "+71111111111", "test1"));
        }
        app.getContactHelper().initContractModification();
        app.getContactHelper().fillContactForm(new ContactData("test3", "test4", "+72222222222", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();

    }
}
