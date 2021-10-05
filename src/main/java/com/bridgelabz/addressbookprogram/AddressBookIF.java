package com.bridgelabz.addressbookprogram;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.bridgelabz.addressbookprogram.AddressBookFileIOService.FileType;
import com.bridgelabz.addressbookprogram.IOServiceMode.IOService;
import com.opencsv.exceptions.CsvException;

public interface AddressBookIF {

    public void createAddressBook(String addressbookName);
    public AddressBook getAddressBook(String addressbookName);

    public List < Contact > getContactList(String addressbookName);
    public void writeContactsOfAddressBook(IOService ioService, String addressbookName, FileType...fileType) throws IOException, CsvException;
    public void printData(IOService ioService, FileType...fileType) throws IOException, CsvException;
    public long countEntries(IOService ioService, FileType...fileType) throws IOException, CsvException;
    public List < Contact > readContactListData(IOService ioService, String addressbookName, FileType...fileType) throws IOException, CsvException;
    public List < AddressBook > readContactListDataFromJSON() throws IOException, CsvException;
    public List < ContactDTO > readContactListDataFromDB(String addressbookName) throws AddressBookException;
    public List < ContactDTO > readContactListOfState(IOService ioService, String state);
    public int countOfContactsInGivenStateCity(IOService ioService, String city, String state, String addressBook);
    public List < String > getSortedContactByName(IOService ioService, String city) throws AddressBookException;
    public int countOfContactsInGivenType(IOService ioService, String type);
    public boolean checkContactInSyncWithDB(String name);
    public void addContact(Contact contact, String addressBookName, LocalDate date);
    public void setUpDataBase() throws FileNotFoundException, SQLException;
    public int countOfContactsAddedInGivenDateRange(IOService ioService, String startDate, String endDate);
    public int countOfContacts(IOService ioService);
    public void updateContactPhoneNumber(IOService ioService, Contact contact, String phoneNumber);



}