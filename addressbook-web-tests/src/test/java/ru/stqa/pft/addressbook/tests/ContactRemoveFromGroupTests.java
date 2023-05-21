package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactRemoveFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test_group"));
        }

        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("Ivan").withLastName("Ivanov").withMobilePhone("+71111111111"));
        }
    }

    @Test
    public void testContactAddToGroup() throws Exception {
        Contacts contactsBefore = app.db().contacts();
        ContactData contact = contactsBefore.iterator().next();

        Groups before = contact.getGroups();
        System.out.println(before + "before");

        app.contact().removeFromGroup(contact);

        Contacts contactsAfter = app.db().contacts();
        ContactData contactAfter = contactsAfter.iterator().next().withId(contact.getId());
        Groups after = contactAfter.getGroups();
        System.out.println(after + "after");
//        assertEquals(after.size(), before.size());
//        assertThat(after, equalTo(before.without(deletedContact)));
    }
}
