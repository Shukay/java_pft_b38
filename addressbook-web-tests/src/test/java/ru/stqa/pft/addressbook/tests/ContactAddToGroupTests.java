package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;


import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactAddToGroupTests extends TestBase {

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

        app.contact().addToGroup(contact);

        Contacts contactsAfter = app.db().contacts();
        ContactData contactAfter = contactsAfter.iterator().next().withId(contact.getId());
        Groups after = contactAfter.getGroups();
        System.out.println(after + "after");
//        assertThat(after, equalTo(
//                before.withAdded(group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt()))));
    }
}
