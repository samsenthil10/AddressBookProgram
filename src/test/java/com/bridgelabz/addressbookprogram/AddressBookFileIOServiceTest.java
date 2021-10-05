package com.bridgelabz.addressbookprogram;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.addressbookprogram.IOServiceMode.IOService;
import com.opencsv.exceptions.CsvException;
import com.bridgelabz.addressbookprogram.AddressBookFileIOService.FileType;

public class AddressBookFileIOServiceTest {
	AddressBookImpl addressBookOperations;
	AddressBooks addressBookSystem = new AddressBooks();

	String addressBookName;

	@Before
	public void initialSetUp() {
		addressBookOperations = new AddressBookImpl();

		addressBookOperations.createAddressBook("Addressbook1");
		addressBookName = "Addressbook1";
		Address address = new Address("123", "aaa", "pasadena", "california", "12345");
		addressBookOperations.addNewContact(new Contact("Sheldon", "Cooper", address, "12345", "abc@gmail.com"), addressBookName);
		address = new Address("456", "groov street", "LA", "california", "12345");
		addressBookOperations.addNewContact(new Contact("Ross ", "Geller", address, "89897677", "ross@gmail.com"), addressBookName);
		address = new Address("22", "green", "pasadena", "new york", "12345");
		addressBookOperations.addNewContact(new Contact("Ted", "Mobsby", address, "567890999", "ted@gmail.com"), addressBookName);

		addressBookOperations.createAddressBook("Addressbook2");
		addressBookName = "Addressbook2";
		address = new Address("456", "texas", "pasadena", "california", "11134");
		addressBookOperations.addNewContact(new Contact("james", "b", address, "12345", "abc@gmail.com"), addressBookName);
		address = new Address("23", "rover street", "aaa", "New york", "2333");
		addressBookOperations.addNewContact(new Contact("susan ", "aaa", address, "89897677", "ross@gmail.com"), addressBookName);
		address = new Address("44", "aaaa", "bbb", "New york", "2345");
		addressBookOperations.addNewContact(new Contact("alan", "c", address, "567890999", "ted@gmail.com"), addressBookName);

	}
	@Test
	public void given3ContactsWhenWrittenToTXTFileShouldMatchContactEntries() throws IOException, CsvException {

		addressBookOperations.writeContactsOfAddressBook(IOService.FILE_IO, addressBookName, FileType.TXT);
		long txtEntries = addressBookOperations.countEntries(IOService.FILE_IO, FileType.TXT);
		Assert.assertEquals(3, txtEntries);
	}
	@Test
	public void given3ContactsWhenWrittenToCSVFileShouldMatchContactEntries() throws IOException, CsvException {

		addressBookOperations.writeContactsOfAddressBook(IOService.FILE_IO, addressBookName, FileType.CSV);
		long csvEntries = addressBookOperations.countEntries(IOService.FILE_IO, FileType.CSV);
		Assert.assertEquals(3, csvEntries);

	}
	@Test
	public void given3ContactsWhenWrittenToJSONFileShouldMatchContactEntries() throws IOException, CsvException {

		addressBookOperations.writeContactsOfAddressBook(IOService.FILE_IO, addressBookName, FileType.JSON);
		long jsonEntries = addressBookOperations.countEntries(IOService.FILE_IO, FileType.JSON);
		Assert.assertEquals(2, jsonEntries);

	}

	@Test
	public void givenFileOnReadingFromTXTFileShouldMatchContactsCount() throws IOException, CsvException {
		long txtEntries = addressBookOperations.readContactListData(IOService.FILE_IO, addressBookName, FileType.TXT).size();
		Assert.assertEquals(3, txtEntries);

	}
	@Test
	public void givenFileOnReadingFromCSVFileShouldMatchContactsCount() throws IOException, CsvException {

		long csvEntries = addressBookOperations.readContactListData(IOService.FILE_IO, addressBookName, FileType.CSV).size();
		Assert.assertEquals(3, csvEntries);

	}
	@Test
	public void givenFileOnReadingFromJSONFileShouldMatchContactsCount() throws IOException, CsvException {

		long jsonEntries = addressBookOperations.readContactListDataFromJSON().size();
		Assert.assertEquals(2, jsonEntries);

	}
}