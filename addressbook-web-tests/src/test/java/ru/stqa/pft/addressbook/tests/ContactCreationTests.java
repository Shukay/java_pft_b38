package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;


public class ContactCreationTests extends TestBase{
  private WebDriver wd;

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().creationContact(new ContactData("test1", "test2", "+71111111111", "test1"));
  }

}
