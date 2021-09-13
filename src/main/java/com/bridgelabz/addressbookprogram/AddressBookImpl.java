package com.bridgelabz.addressbookprogram;

import java.util.Map;
import java.util.Map.Entry;

public class AddressBookImpl implements AddressBookIF {

	@Override
	public AddressBook selectActiveAddressBook(Map<String, AddressBook> addressBooks) {

		if(addressBooks.isEmpty() == true)
			return null;
		else {
			System.out.print("Name of Address Books :");
			String toSelectAddressBooks = AddressBookMain.scanner.next();
			for(Map.Entry<String, AddressBook> mapper:addressBooks.entrySet()) {
				if(mapper.getKey().equalsIgnoreCase(toSelectAddressBooks)) {
					return mapper.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public <K, V> String getKey(Map<String, AddressBook> map, AddressBook value) {
		for (Entry<String, AddressBook> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}
}
