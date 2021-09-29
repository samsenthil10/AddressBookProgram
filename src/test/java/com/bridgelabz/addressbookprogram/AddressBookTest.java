package com.bridgelabz.addressbookprogram;

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.junit.Assert;
import org.junit.Test;

public class AddressBookTest {

	@Test
	public void givenContact_WhenAdded_ShouldIncreaseSize() {
		LinkedHashSet<Contacts> contacts = new LinkedHashSet<>();
		Contacts contact = new Contacts();
		contact.setCity("city");
		contact.setState("state");
		contacts.add(contact);
		Assert.assertEquals(1, contacts.size());
	}
	
	@Test
	public void givenMultipleContacts_WhenAdded_ShouldHaveSizeGreaterThan1() {
		LinkedHashSet<Contacts> contacts = new LinkedHashSet<>();
		Contacts contactOne = new Contacts();
		Contacts contactTwo = new Contacts();
		contactOne.setCity("city");
		contactOne.setState("state");
		contactTwo.setCity("city1");
		contactTwo.setState("state1");
		contacts.add(contactOne);
		contacts.add(contactTwo);
		Assert.assertTrue(contacts.size()>1);
	}
	
	@Test
	public void whenGivenCity_WhenRetrieved_ShouldGivenCountOfContactsInGivenCity() {
		LinkedHashSet<Contacts> contacts = new LinkedHashSet<>();
		int count=0;
		Contacts contactOne = new Contacts();
		Contacts contactTwo = new Contacts();
		Contacts contactThree = new Contacts();
		contactOne.setCity("city2");
		contactOne.setState("state1");
		contactTwo.setCity("city1");
		contactTwo.setState("state2");
		contactThree.setCity("city1");
		contactThree.setState("state2");
		contacts.add(contactOne);
		contacts.add(contactTwo);
		contacts.add(contactThree);
		String givenCity = "city1";
		Iterator<Contacts> iterator = contacts.iterator();
		while(iterator.hasNext()) {
			Contacts contact = iterator.next();
			if(contact.getCity().equalsIgnoreCase(givenCity))
				count++;
		}
		Assert.assertEquals(count, 2);
	}
	
	@Test
	public void whenGivenState_WhenRetrieved_ShouldGivenCountOfContactsInGivenCity() {
		LinkedHashSet<Contacts> contacts = new LinkedHashSet<>();
		int count=0;
		Contacts contactOne = new Contacts();
		Contacts contactTwo = new Contacts();
		Contacts contactThree = new Contacts();
		contactOne.setCity("city2");
		contactOne.setState("state1");
		contactTwo.setCity("city1");
		contactTwo.setState("state2");
		contactThree.setCity("city1");
		contactThree.setState("state2");
		contacts.add(contactOne);
		contacts.add(contactTwo);
		contacts.add(contactThree);
		String givenState = "state2";
		Iterator<Contacts> iterator = contacts.iterator();
		while(iterator.hasNext()) {
			Contacts contact = iterator.next();
			if(contact.getState().equalsIgnoreCase(givenState))
				count++;
		}
		Assert.assertEquals(count, 2);
	}
	
	@Test
	public void whenUpdated_ShouldCheckForContactsInSync() {
		
		String name = "Michael";
		String newName = "Michael";
		AddressBookJdbcService.getInstance().updateContactByName(name, newName);
		boolean result = AddressBookJdbcService.getInstance().checkAddressBookInSyncWithDB(name);
		Assert.assertTrue(result);
	}
	
	@Test
	public void givenDateRange_WhenRecordsRetrieved_ShouldReturnCountOfRecordsBetweenThatRange() {
		
		String startDate="2021-09-28";
		String endDate="2021-09-29";
		int result = AddressBookJdbcService.getInstance().retrieveRecordBetweenDates(startDate, endDate);
	    Assert.assertEquals(result,4);
	}
}
