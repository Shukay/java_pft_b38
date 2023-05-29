package ru.stqa.pft.addressbook.tests;

import org.hibernate.SessionBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {

    ContactData contact;
    GroupData group;
    private boolean groupCreated;
    private boolean contactCreated;

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test_group"));
            group = app.db().groups().iterator().next();
            groupCreated = true;
        }

        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("Ivan").withLastName("Ivanov").withMobilePhone("+71111111111"));
            contact = app.db().contacts().iterator().next();
            contactCreated = true;
        }

        if (!(contactCreated && groupCreated)) {
            for (GroupData g : app.db().groups()) {
                for (ContactData c : app.db().contacts()) {
                    if (!c.getGroups().contains(g)) {
                        contact = c;
                        group = g;
                        return;
                    }
                }
            }
            contact = app.db().contacts().iterator().next();
            app.goTo().GroupPage();
            group = new GroupData().withName("group_" + (int) (Math.random() * 1000));
            app.group().create(group);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactAddToGroup() throws Exception {

        Contacts before = app.db().contacts();
        Groups groupsBefore = contact.getGroups();
        System.out.println(groupsBefore);
        ContactData contactWithAddedGroup = contact.inGroup(group);

        app.contact().addToGroup(contact.getId(), group.getName());

        Contacts after = app.db().contacts();
        Groups groupsAfter = contact.getGroups();
        System.out.println(groupsAfter);
//        assertThat(after, equalTo(before.without(contact).withAdded(contactWithAddedGroup)));
        assertThat(groupsAfter, equalTo(groupsBefore.withAdded(group)));
    }
}
