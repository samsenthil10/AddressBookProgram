package com.bridgelabz.addressbookprogram;

import java.util.Map;

public class AddressBookImpl implements AddressBookIF {
	
	@Override
	public AddressBook selectActiveAddressBook(Map<String, AddressBook> addressBooks) {
		System.out.print("Name of Address Books :");
		String toSelectAddressBooks = AddressBookMain.scanner.next();
		for(Map.Entry<String, AddressBook> mapper:addressBooks.entrySet()) {
			if(mapper.getKey().equalsIgnoreCase(toSelectAddressBooks)) {
				return mapper.getValue();
			}
		}
		return null;
	}
}
