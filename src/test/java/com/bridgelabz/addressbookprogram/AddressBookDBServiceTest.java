package com.bridgelabz.addressbookprogram;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.bridgelabz.addressbookprogram.IOServiceMode.IOService;
import com.opencsv.exceptions.CsvException;

public class AddressBookDBServiceTest {
	AddressBookImpl addressBookOperations;

	@Before
	public void initialSetUp() throws FileNotFoundException, SQLException {
		addressBookOperations = new AddressBookImpl();
	}

	@Test
	public void givenContactsInDB_WhenRetrieved_ShouldMatchContactsCount() throws IOException, CsvException, AddressBookException {

		List < ContactDTO > contactList = addressBookOperations.readContactListDataFromDB("book1");
		Assert.assertEquals(2, contactList.size());
	}
	@Test
	public void givenContactsInDB_WhenGivenAddressBookNameAsNull_ShouldThrowCustomException() {

		try {

			addressBookOperations.readContactListDataFromDB(null);
		} catch (AddressBookException e) {
			Assert.assertEquals("Please enter valid AddressBook Name", e.getMessage());
		}
	}
	@Test
	public void givenContactsInDB_WhenGivenAddressBookNameAsEmpty_ShouldThrowCustomException() {

		try {

			addressBookOperations.readContactListDataFromDB("");
		} catch (AddressBookException e) {
			Assert.assertEquals("Please enter valid AddressBook Name", e.getMessage());
		}
	}

	@Test
	public void givenContactsInDB_WhenGivenState_ShouldMatchContactsCountInGivenState() {

		List < ContactDTO > contactList = addressBookOperations.readContactListOfState(IOService.DB_IO, "San Andreas");
		Assert.assertEquals(6, contactList.size());

	}
	@Test
	public void givenContactsInDB_WhenGivenStateCity_ShouldReturnCountOfContactsInGivenAddressBook1() {

		int count = addressBookOperations.countOfContactsInGivenStateCity(IOService.DB_IO, "Los Santos", "San Andreas", "book1");
		Assert.assertEquals(2,count);
	}
	@Test
	public void givenContactsInDB_WhenGivenStateCity_ShouldReturnCountOfContactsInGivenAddressBook2() {

		int count = addressBookOperations.countOfContactsInGivenStateCity(IOService.DB_IO, "Los Santos", "San Andreas", "book2");
		Assert.assertEquals(2,count);
	}
	@Test
	public void givenContactsInDB_WhenGivenStateCity_ShouldReturnCountOfContactsInGivenAddressBook3() {

		int count = addressBookOperations.countOfContactsInGivenStateCity(IOService.DB_IO, "Los Santos", "San Andreas", "book3");
		Assert.assertEquals(2,count);
	}
	@Test
	public void givenContactsInDB_WhenGivenCity_ShouldReturnSortedContactsByName() throws AddressBookException {
		List < String > expectedSortOrder = new ArrayList < String > ();
		expectedSortOrder.add("Amanda");
		expectedSortOrder.add("Franklin");
		expectedSortOrder.add("Lamar");
		expectedSortOrder.add("Michael");
		expectedSortOrder.add("Trevor");
		List < String > retrievedSortedOrder = addressBookOperations.getSortedContactByName(IOService.DB_IO, "Los Santos");
		Assert.assertEquals(expectedSortOrder, retrievedSortedOrder);
	}
	@Test
	public void givenContactsInDB_WhenGivenCityNameAsNull_ShouldThrowCustomException() {

		try {

			List < String > expectedSortOrder = new ArrayList < String > ();
			expectedSortOrder.add("Bob");
			expectedSortOrder.add("raj");
			addressBookOperations.getSortedContactByName(IOService.DB_IO, null);
		} catch (AddressBookException e) {
			Assert.assertEquals("Please enter valid city Name", e.getMessage());
		}
	}
	@Test
	public void givenContactsInDB_WhenGivenCityNameAsEmpty_ShouldThrowCustomException() {

		try {

			List < String > expectedSortOrder = new ArrayList < String > ();
			expectedSortOrder.add("Bob");
			expectedSortOrder.add("raj");
			addressBookOperations.getSortedContactByName(IOService.DB_IO, "");
		} catch (AddressBookException e) {
			Assert.assertEquals("Please enter valid city Name", e.getMessage());
		}
	}
	@Test
	public void givenContactDB_WhenGivenAddresBookTypeFriend_ShouldReturnCountOfContactsOfGivenTypeFriend() {
		int count = addressBookOperations.countOfContactsInGivenType(IOService.DB_IO, "Friend");
		Assert.assertEquals(2, count);
	}
	@Test
	public void givenContactDB_WhenGivenAddresBookTypeFamily_ShouldReturnCountOfContactsOfGivenTypeFamily() {
		int count = addressBookOperations.countOfContactsInGivenType(IOService.DB_IO, "Family");
		Assert.assertEquals(2, count);
	}
	@Test
	public void givenContactDB_WhenGivenAddresBookTypeProfessional_ShouldReturnCountOfContactsOfGivenTypeProfessional() {
		int count = addressBookOperations.countOfContactsInGivenType(IOService.DB_IO, "Professional");
		Assert.assertEquals(2, count);
	}

	@Test
	public void givenContactDB_WhenGivenDate_ShouldReturnCountOfContactsAddedInGivenDateRange() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String startDate = "2021-09-29";
		String endDate = "2021-09-30";

		LocalDate.parse(startDate, formatter);
		LocalDate.parse(endDate, formatter);

		int count = addressBookOperations.countOfContactsAddedInGivenDateRange(IOService.DB_IO, startDate, endDate);
		Assert.assertEquals(4, count);
	}
}