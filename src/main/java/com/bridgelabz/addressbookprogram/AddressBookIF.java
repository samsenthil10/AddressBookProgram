package com.bridgelabz.addressbookprogram;

import java.util.Map;

public interface AddressBookIF {

	public AddressBook selectActiveAddressBook(Map<String, AddressBook> addressBooks);
	public <K, V> String getKey(Map<String, AddressBook> map, AddressBook value);
	public void showContactsInGivenCity(String enteredCity, Map<String, AddressBook> addressBooks);
	public void showContactsInGivenState(String enteredState, Map<String, AddressBook> addressBooks);
	public void showAllContactsInAllCity(Map<String, AddressBook> addressBooks);
	public void showAllContactsInAllState(Map<String, AddressBook> addressBooks);
}
