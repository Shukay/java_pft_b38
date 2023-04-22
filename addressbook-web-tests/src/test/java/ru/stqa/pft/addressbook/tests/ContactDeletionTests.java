package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().creationContact(new ContactData("test1", "test2", "+71111111111", "test1"));
        }
        app.getContactHelper().selectContract();
        app.getContactHelper().deleteSelectedContract();
        app.getNavigationHelper().goToHomePage();

    }
}
