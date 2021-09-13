package com.bridgelabz.addressbookprogram;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

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

	@Override
	public void showContactsInGivenCity(String enteredCity, Map<String, AddressBook> addressBooks) {

		int emptyFlag=0;
		System.out.println("City: "+enteredCity);
		for (Entry<String, AddressBook> entry : addressBooks.entrySet()) {
			Iterator<Contacts> iterator = entry.getValue().contacts.iterator();
			while(iterator.hasNext()) {
				Contacts contact = iterator.next();
				if(contact == null)
					continue;
				if(contact.getCity().equalsIgnoreCase(enteredCity)) {
					emptyFlag=1;
					System.out.println(contact.getFirstName()+" "+contact.getLastName());
				}					
			}
		}
		if(emptyFlag == 0)
			System.out.println("No Contacts Found!");
	}

	@Override
	public void showContactsInGivenState(String enteredState, Map<String, AddressBook> addressBooks) {

		int emptyFlag=0;
		System.out.println("State: "+enteredState);
		for (Entry<String, AddressBook> entry : addressBooks.entrySet()) {
			Iterator<Contacts> iterator = entry.getValue().contacts.iterator();
			while(iterator.hasNext()) {
				Contacts contact = iterator.next();
				if(contact.getState().equalsIgnoreCase(enteredState)) {
					emptyFlag=1;
					System.out.println(contact.getFirstName()+" "+contact.getLastName());
				}					
			}
		}
		if(emptyFlag == 0)
			System.out.println("No Contacts Found!");
	}

	@Override
	public void showAllContactsInAllCity(Map<String, AddressBook> addressBooks) {
		int emptyFlag =0;
		Multimap<String, String> cityContacts = ArrayListMultimap.create();
		for (Entry<String, AddressBook> entry : addressBooks.entrySet()) {
			Iterator<Contacts> iterator = entry.getValue().contacts.iterator();
			while(iterator.hasNext()) {
				Contacts contact = iterator.next();
				if(contact == null)
					continue;
				cityContacts.put(contact.getCity(),contact.getFirstName()+" "+contact.getLastName());
			}					
		}
		for(Entry<String,Collection<String>> result : cityContacts.asMap().entrySet()) {
			if(result.getValue().size()==0 && result.getKey()!=null) {
				System.out.println(result.getKey()+": No Contacts Found");
				break;
			}

			else if(result.getKey()!=null) {
				emptyFlag=1;
				System.out.println(result.getKey()+": "+result.getValue());
			}
		}
		if(emptyFlag == 0) {
			System.out.println("No Records Found");
		}
	}

	@Override
	public void showAllContactsInAllState(Map<String, AddressBook> addressBooks) {

		int emptyFlag=0;
		Multimap<String, String> stateContacts = ArrayListMultimap.create();
		for (Entry<String, AddressBook> entry : addressBooks.entrySet()) {
			Iterator<Contacts> iterator = entry.getValue().contacts.iterator();
			while(iterator.hasNext()) {
				Contacts contact = iterator.next();
				if(contact == null)
					continue;
				stateContacts.put(contact.getState(),contact.getFirstName()+" "+contact.getLastName());
			}					
		}
		for(Entry<String,Collection<String>> result : stateContacts.asMap().entrySet()) {
			if(result.getValue().size()==0 && result.getKey()!=null) {
				System.out.println(result.getKey()+": No Contacts Found");
				break;
			}

			else if(result.getKey()!=null) {
				emptyFlag=1;
				System.out.println(result.getKey()+": "+result.getValue());
			}
		}
		if(emptyFlag == 0) {
			System.out.println("No Records Found");
		}
	}
}
