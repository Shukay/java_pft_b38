package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroupTests extends TestBase {

    private ContactData contact;
    private GroupData group;
    private boolean contactCreated;
    private boolean groupCreated;

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test_group"));
            groupCreated = true;
        }

        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("Ivan").withLastName("Ivanov").withMobilePhone("+71111111111"));
            contactCreated = true;
        }

        if (!(contactCreated && groupCreated)) {
            for (ContactData c : app.db().contacts()) {
                if (c.getGroups().size() != 0) {
                    contact = c;
                    group = c.getGroups().iterator().next();
                    return;
                }
            }
        }
        contact = app.db().contacts().iterator().next();
        group = app.db().groups().iterator().next();
        app.goTo().homePage();
        app.contact().addToGroup(contact.getId(), group);
    }

    @Test
    public void testContactRemoveFromGroup() throws Exception {

        Groups groups = app.db().groups();
        GroupData before = app.group().getGroupContact(groups);

        app.contact().removeFromGroup(contact.getId(), before);
        GroupData after = app.db().group(before.getId());

        assertThat(after.getContacts(), equalTo(before.getContacts().without(contact)));
    }
}
