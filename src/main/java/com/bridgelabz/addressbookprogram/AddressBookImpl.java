package com.bridgelabz.addressbookprogram;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.bridgelabz.addressbookprogram.AddressBookFileIOService.FileType;
import com.bridgelabz.addressbookprogram.IOServiceMode.IOService;
import com.opencsv.exceptions.CsvException;

import java.util.stream.Collectors;


public class AddressBookImpl implements AddressBookConsoleServiceIF, AddressBookIF {

    AddressBookJdbcService addressBookDBService = new AddressBookJdbcService();
    AddressBooks addressbookSystem = new AddressBooks();

    List < Contact > contactList = new ArrayList < Contact > ();
    List < ContactDTO > contactListDTO = new ArrayList < ContactDTO > ();


    public AddressBookImpl(List < Contact > contactList) {
        this.contactList = new ArrayList < Contact > ();
    }

    public AddressBookImpl() {
        addressBookDBService = AddressBookJdbcService.getInstance();
    }

    @Override
    public void createAddressBook(String addressbookName) {
        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();
        AddressBook addressBook = new AddressBook();
        addressBook.setAddressBookName(addressbookName);
        addressbookList.add(addressBook);
        addressbookSystem.setAddressbookList(addressbookList);

    }

    @Override
    public AddressBook getAddressBook(String addressbookName) {
        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();


        for (int index = 0; index < addressbookList.size(); index++) {

            if (addressbookList.get(index).getAddressBookName().equals(addressbookName)) {
                return addressbookList.get(index);
            }
        }

        return null;
    }


    @Override
    public int hasContact(String number, String addressbookName) {
        int givenContactIndex = -1;

        AddressBook addressbook = getAddressBook(addressbookName);
        List < Contact > contactList = addressbook.getContactList();
        for (int index = 0; index < contactList.size(); index++) {

            if (contactList.get(index).getPhoneNumber().equals(number)) {
                givenContactIndex = index;

            }
        }
        return givenContactIndex;
    }

    @Override
    public void addNewContact(Contact person, String addressbookName) {
        AddressBook addressbook = getAddressBook(addressbookName);
        List < Contact > contactList = addressbook.getContactList();

        contactList.add(person);
        @SuppressWarnings("unused")
		Stream < Contact > stream = contactList.stream().distinct();

        contactList = contactList.stream()
            .distinct()
            .collect(Collectors.toList());

        addressbook.setContactList(contactList);


    }

    @Override
    public void editContact(String number, String editInfo, String choice, String addressbookName) {
        AddressBook addressbook = getAddressBook(addressbookName);


        List < Contact > contactList = addressbook.getContactList();

        int contactIndex = hasContact(number, addressbookName);
        if (contactIndex >= 0)
            switch (choice) {

                case "address":
                    contactList.get(contactIndex).getContactAddress().setAddress(editInfo);
                    break;
                case "city":
                    contactList.get(contactIndex).getContactAddress().setCity(editInfo);
                    break;
                case "state":
                    contactList.get(contactIndex).getContactAddress().setState(editInfo);
                    break;
                case "zip":
                    contactList.get(contactIndex).getContactAddress().setZip(editInfo);
                    break;
                case "phoneNumber":
                    contactList.get(contactIndex).setPhoneNumber(editInfo);
                    break;
                case "email":
                    contactList.get(contactIndex).setEmail(editInfo);
                    break;

            }

        System.out.println("After editing the details are:");
        displayContactInfo(number, addressbookName);

    }

    @Override
    public void deleteContact(String number, String addressbookName)

    {
        AddressBook addressbook = getAddressBook(addressbookName);

        List < Contact > newlist = addressbook.getContactList();
        int contactIndex = hasContact(number, addressbookName);
        if (contactIndex >= 0) {

            newlist.remove(contactIndex);
        }

    }


    @Override
    public void displayContactInfo(String number, String addressbookName)

    {
        AddressBook addressbook = getAddressBook(addressbookName);

        List < Contact > contactList = addressbook.getContactList();

        int contactIndex = hasContact(number, addressbookName);
        if (contactIndex >= 0) {
            Contact contact = contactList.get(contactIndex);
            System.out.println("address:" + contact.getContactAddress().getAddress());
            System.out.println("city:" + contact.getContactAddress().getCity());
            System.out.println("state:" + contact.getContactAddress().getState());
            System.out.println("zip:" + contact.getContactAddress().getZip());
            System.out.println("phone number:" + contact.getPhoneNumber());
            System.out.println("email:" + contact.getEmail());

        }


    }

    @Override
    public void searchPersonByState(String givenName, String state, PrintStream...printStreamObject) {

        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        System.out.println("all contacts in given name in given state are:");
        addressbookList.stream()
            .forEach(addressBook -> addressBook.getContactList()
                .stream().filter(contact -> contact.getContactAddress().getState().equals(state) && contact.getFirstName().equals(givenName))
                .forEach(object -> printStreamObject[0].println(object)));
    }


    @Override
    public void searchPersonByCity(String givenName, String city, PrintStream...printStreamObject) {

        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        System.out.println("all contacts in given name in given city are:");
        addressbookList.stream()
            .forEach(addressBook -> addressBook.getContactList()
                .stream().filter(contact -> contact.getContactAddress().getCity().equals(city) && contact.getFirstName().equals(givenName))
                .forEach(contact -> printStreamObject[0].println(contact)));

    }

    @Override
    public void getAllContactsInState(String state, PrintStream...printStreamObject) {
        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        System.out.println("all contacts in given state are:");
        addressbookList.stream()
            .forEach(addressBook -> addressBook.getContactList()
                .stream().filter(contact -> contact.getContactAddress().getState().equals(state)).forEach(contact -> printStreamObject[0].println(contact)));

    }
    @Override
    public void getAllContactsInCity(String city, PrintStream...printStreamObject) {

        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        System.out.println("all contacts in given city are:");
        addressbookList.stream()
            .forEach(addressBook -> addressBook.getContactList()
                .stream().filter(contact -> contact.getContactAddress().getCity().equals(city)).forEach(contact -> printStreamObject[0].println(contact)));

    }
    @Override
    public void countPeopleinCity(String city, PrintStream...printStreamObject) {

        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        addressbookList.stream()
            .forEach(addressBook -> System.out.println("Number of contacts of given city in addressbook" + addressBook.getAddressBookName() + " is" + addressBook.getContactList()
                .stream().filter(contact -> contact.getContactAddress().getCity().equals(city)).count()));


    }


    @Override
    public void countPeopleinState(String state, PrintStream...printStreamObject) {
        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        addressbookList.stream()
            .forEach(addressBook -> System.out.println("Number of contacts of given state in addressbook" + addressBook.getAddressBookName() + " is" + addressBook.getContactList()
                .stream().filter(contact -> contact.getContactAddress().getState().equals(state)).count()));

    }

    @Override
    public void sortByName(PrintStream...printStreamObject) {
        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        System.out.println("Sorting contacts by fist name:");
        addressbookList.stream()
            .forEach(addressBook -> addressBook.getContactList()
                .stream().sorted((person1, person2) -> person1.getFirstName().compareTo(person2.getFirstName())).forEach(contact -> printStreamObject[0].println(contact)));
    }

    @Override
    public void sortByCity(PrintStream...printStreamObject) {
        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        System.out.println("Sorting contacts by  city:");
        addressbookList.stream()
            .forEach(addressBook -> addressBook.getContactList()
                .stream().sorted((person1, person2) -> person1.getContactAddress().getCity().compareTo(person2.getContactAddress().getCity())).forEach(contact -> printStreamObject[0].println(contact)));
    }

    @Override
    public void sortByState(PrintStream...printStreamObject) {
        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        System.out.println("Sorting contacts by  state");
        addressbookList.stream()
            .forEach(addressBook -> addressBook.getContactList()
                .stream().sorted((person1, person2) -> person1.getContactAddress().getState().compareTo(person2.getContactAddress().getState())).forEach(contact -> printStreamObject[0].println(contact)));
    }

    @Override
    public void sortByZip(PrintStream...printStreamObject) {
        List < AddressBook > addressbookList = addressbookSystem.getAddressbookList();

        System.out.println("Sorting contacts by  zip:");
        addressbookList.stream()
            .forEach(addressBook -> addressBook.getContactList()
                .stream().sorted((person1, person2) -> person1.getContactAddress().getZip().compareTo(person2.getContactAddress().getZip())).forEach(contact -> printStreamObject[0].println(contact)));
    }
    @Override
    public List < Contact > getContactList(String addressbookName) {
        AddressBook addressbook = getAddressBook(addressbookName);

        List < Contact > contactList = addressbook.getContactList();

        return contactList;
    }
    @Override
    public void writeContactsOfAddressBook(IOService ioService, String addressbookName, FileType...fileType) throws IOException, CsvException {
        if (ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nWriting contact list  info to console:\n " + this.getContactList(addressbookName));
        else if (ioService.equals(IOService.FILE_IO)) {
            new AddressBookFileIOService().writeData(this.getContactList(addressbookName), fileType[0], this.addressbookSystem);
        }

    }
    @Override
    public void printData(IOService ioService, FileType...fileType) throws IOException, CsvException {
        if (ioService.equals(IOService.FILE_IO)) {
            new AddressBookFileIOService().printData(fileType[0]);
        }

    }
    @Override

    public long countEntries(IOService ioService, FileType...fileType) throws IOException, CsvException {

        if (ioService.equals(IOService.FILE_IO)) {
            return new AddressBookFileIOService().countEntries(fileType[0]);
        }

        return 0;
    }

    @Override
    public List < AddressBook > readContactListDataFromJSON() throws IOException, CsvException {
        AddressBookFileIOService addressbookFileIOService = new AddressBookFileIOService();

        return addressbookFileIOService.readData();

    }

    @Override
    public List < Contact > readContactListData(IOService ioService, String addressbookName, FileType...fileType) throws IOException, CsvException {

        if (ioService.equals(IOService.FILE_IO)) {
            AddressBookFileIOService addressbookFileIOService = new AddressBookFileIOService();

            this.getAddressBook(addressbookName).setContactList(addressbookFileIOService.readData(fileType[0]));


        }

        return this.getAddressBook(addressbookName).getContactList();
    }
    @Override
    public List < ContactDTO > readContactListDataFromDB(String addressbookName) throws AddressBookException {
        this.contactListDTO = new AddressBookJdbcService().readContactList(addressbookName);
        return this.contactListDTO;

    }

    @Override
    public List < ContactDTO > readContactListOfState(IOService ioService, String state) {
        if (ioService.equals(IOService.DB_IO)) {
            return new AddressBookJdbcService().readContactListOfState(state);

        }

        return null;
    }

    @Override
    public int countOfContactsInGivenStateCity(IOService ioService, String city, String state, String addressBook) {
        if (ioService.equals(IOService.DB_IO)) {
            return new AddressBookJdbcService().countOfContactsInGivenStateCity(city, state, addressBook);

        }

        return 0;
    }

    @Override
    public List < String > getSortedContactByName(IOService ioService, String city) throws AddressBookException

    {
        if (ioService.equals(IOService.DB_IO)) {
            return new AddressBookJdbcService().getSortedContactByName(city);

        }

        return null;
    }
    @Override
    public int countOfContactsInGivenType(IOService ioService, String type) {
        if (ioService.equals(IOService.DB_IO)) {
            return new AddressBookJdbcService().countOfContactsInGivenType(type);

        }
        return 0;
    }

    @Override
    public ContactDTO getContact(String name) {
        ContactDTO contact;
        contact = this.contactListDTO.stream()
            .filter(contactItem -> contactItem.getFirstName().equals(name))
            .findFirst()
            .orElse(null);

        return contact;

    }
    @Override
    public boolean checkContactInSyncWithDB(String name) {
        List < ContactDTO > contactList = addressBookDBService.getContactData(name);
        return contactList.get(0).equals(this.getContact(name));
    }

    @Override
    public void addContact(Contact contact, String addressBookName, LocalDate date) {

        contactListDTO.add(addressBookDBService.addContact(contact, addressBookName, date));


    }
    @Override
    public void setUpDataBase() throws FileNotFoundException, SQLException {
        addressBookDBService.setupDatabase();

    }

    @Override
    public int countOfContactsAddedInGivenDateRange(IOService ioService, String startDate, String endDate) {
        if (ioService.equals(IOService.DB_IO)) {
            return addressBookDBService.countOfContactsAddedInGivenDateRange(startDate, endDate);


        }


        return 0;
    }

    @Override
    public int countOfContacts(IOService ioService) {
        if (ioService.equals(IOService.DB_IO)) {
            return addressBookDBService.countOfContactsInDataBase();

        }
        return 0;
    }
    @Override
    public void updateContactPhoneNumber(IOService ioService, Contact contact, String phoneNumber) {
        if (ioService.equals(IOService.DB_IO)) {
            int result = addressBookDBService.updateContactPhoneNumber(contact, phoneNumber);
            if (result == 0) return;
            ContactDTO contactInMemory = this.getContact(contact.getFirstName());
            if (contactInMemory != null) {
                contactInMemory.setPhoneNumber(phoneNumber);

            }
        }
    }
}