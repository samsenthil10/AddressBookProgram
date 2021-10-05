package com.bridgelabz.addressbookprogram;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

public class AddressBookTest {

    AddressBookImpl addressBookOperations;
    AddressBooks addressBookSystem = new AddressBooks();
    Contact contact1, contact2, contact3, contact4, contact5, contact6;

    String addressBookName;

    ByteArrayOutputStream byteArrayOutputStream;
    PrintStream printStream;

    @Before
    public void initialSetUp() {
        addressBookOperations = new AddressBookImpl();

        addressBookOperations.createAddressBook("Addressbook1");
        addressBookName = "Addressbook1";

        Address address1 = new Address("123", "texas", "aaa", "california", "11134");
        contact1 = new Contact("Sheldon", "Cooper", address1, "12345", "abc@gmail.com");
        Address address2 = new Address("1", "groov street", "aaa", "New york", "12311");
        contact2 = new Contact("Ross ", "Geller", address2, "89897677", "ross@gmail.com");
        Address address3 = new Address("24", "aaa", "bbb", "New york", "44562");
        contact3 = new Contact("Ted", "Mobsby", address3, "567890999", "ted@gmail.com");
        addressBookOperations.addNewContact(contact1, addressBookName);
        addressBookOperations.addNewContact(contact2, addressBookName);
        addressBookOperations.addNewContact(contact3, addressBookName);

        addressBookOperations.createAddressBook("Addressbook2");
        addressBookName = "Addressbook2";
        Address address4 = new Address("3", "texas", "pasadena", "california", "11134");
        contact4 = new Contact("james", "b", address4, "12345", "abc@gmail.com");
        Address address5 = new Address("123", "groov street", "aaa", "New york", "12311");
        contact5 = new Contact("susan ", "aaa", address5, "89897677", "ross@gmail.com");
        Address address6 = new Address("342", "aaa", "bbb", "new york", "44562");
        contact6 = new Contact("alan", "c", address6, "567890999", "ted@gmail.com");
        addressBookOperations.addNewContact(contact4, addressBookName);
        addressBookOperations.addNewContact(contact5, addressBookName);
        addressBookOperations.addNewContact(contact6, addressBookName);


        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
    }

    @Test
    public void whenGiven3Contacts_ShouldBeProperlyAddedToGivenAddressBook() {
        List < Contact > givenContactList = new ArrayList < Contact > ();
        givenContactList.add(contact1);
        givenContactList.add(contact2);
        givenContactList.add(contact3);

        Assert.assertEquals(givenContactList, addressBookOperations.getAddressBook("Addressbook1").getContactList());

    }

    @Test
    public void whenGivenContactToEdit_GivenFieldMustBeEdited() {
        addressBookName = "Addressbook1";
        addressBookOperations.editContact("12345", "shell@example.com", "email", addressBookName);
        Address address = new Address("123", "texas", "aaa", "california", "11134");
        Contact resultingContact = new Contact("Sheldon", "Cooper", address, "12345", "shell@example.com");
        int contactIndex = addressBookOperations.hasContact("12345", addressBookName);
        Contact editedContact = addressBookOperations.getAddressBook(addressBookName).getContactList().get(contactIndex);
        Assert.assertEquals(resultingContact, editedContact);

    }
    @Test
    public void whenGivenDetailsOfContactToDelete_ShouldBeDeletedFromContactList() {
        addressBookName = "Addressbook1";
        addressBookOperations.deleteContact("12345", addressBookName);
        Assert.assertEquals(-1, addressBookOperations.hasContact("12345", addressBookName));

    }

    @Test
    public void whenGivenNameAndCity_ShouldPrintAllContactsWithGivenNameAndCity() {
        addressBookOperations.searchPersonByCity("Sheldon", "aaa", printStream);
        String expected = contact1.toString() + "\n";
        Assert.assertEquals(expected, byteArrayOutputStream.toString());

    }

    @Test
    public void whenGivenNameAndState_ShouldPrintAllContactsWithGivenNameAndState() {
        addressBookOperations.searchPersonByState("Sheldon", "california", printStream);
        String expected = contact1.toString() + "\n";
        Assert.assertEquals(expected, byteArrayOutputStream.toString());

    }
    @Test
    public void whenGivenCity_ShouldPrintAllContactsInGivenCity() {
        addressBookOperations.getAllContactsInCity("aaa", printStream);
        String expected = contact1.toString() + "\n" + contact2.toString() + "\n" + contact5.toString() + "\n";
        Assert.assertEquals(expected, byteArrayOutputStream.toString());
    }

    @Test
    public void whenGivenState_ShouldPrintAllContactsInGivenState() {
        addressBookOperations.getAllContactsInState("california", printStream);
        String expected = contact1.toString() + "\n" + contact4.toString() + "\n";
        Assert.assertEquals(expected, byteArrayOutputStream.toString());

    }

    @Test
    public void whenGivenAddressBookAndContacts_ShouldSortByName() {
        addressBookOperations.sortByName(printStream);
        String expected = contact2.toString() + "\n" + contact1.toString() + "\n" + contact3.toString() + "\n" + contact6.toString() + "\n" + contact4.toString() + "\n" + contact5.toString() + "\n";
        Assert.assertEquals(expected, byteArrayOutputStream.toString());
    }
    @Test
    public void whenGivenAddressBookAndContacts_ShouldSortByCity() {
        addressBookOperations.sortByCity(printStream);
        String expected = contact1.toString() + "\n" + contact2.toString() + "\n" + contact3.toString() + "\n" + contact5.toString() + "\n" + contact6.toString() + "\n" + contact4.toString() + "\n";
        Assert.assertEquals(expected, byteArrayOutputStream.toString());
    }
    @Test
    public void whenGivenAddressBookAndContacts_ShouldSortByState() {
        addressBookOperations.sortByState(printStream);
        String expected = contact2.toString() + "\n" + contact3.toString() + "\n" + contact1.toString() + "\n" + contact5.toString() + "\n" + contact4.toString() + "\n" + contact6.toString() + "\n";
        Assert.assertEquals(expected, byteArrayOutputStream.toString());
    }

    @Test
    public void whenGivenAddressBookAndContacts_ShouldSortByZip() {
        addressBookOperations.sortByZip(printStream);
        String expected = contact1.toString() + "\n" + contact2.toString() + "\n" + contact3.toString() + "\n" + contact4.toString() + "\n" + contact5.toString() + "\n" + contact6.toString() + "\n";
        Assert.assertEquals(expected, byteArrayOutputStream.toString());
    }
}