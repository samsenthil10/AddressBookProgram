package com.bridgelabz.addressbookprogram;

import java.util.Map;

public interface AddressBookIF {

	public AddressBook selectActiveAddressBook(Map<String, AddressBook> addressBooks);
	public <K, V> String getKey(Map<String, AddressBook> map, AddressBook value);
}
