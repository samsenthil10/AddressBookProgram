package com.bridgelabz.addressbookprogram;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

		System.out.println("City: "+enteredCity);
		List<Contacts> contactsInCity = new ArrayList<>();
		for (Entry<String, AddressBook> entry : addressBooks.entrySet()) {
			entry.getValue().contacts.stream()
			.filter(contact -> contact.getState().equalsIgnoreCase(enteredCity))
			.forEach(contactsInCity::add);
		}
		if(contactsInCity.size()>0) {
			contactsInCity.stream()
			.forEach(contact -> {System.out.println(contact.getFirstName()+" "+contact.getLastName());});
		}	
		else {
			System.out.println("No Contacts Found!");
		}
	}
	@Override
	public void showContactsInGivenState(String enteredState, Map<String, AddressBook> addressBooks) {

		System.out.println("State: "+enteredState);
		List<Contacts> contactsInState = new ArrayList<>();
		for (Entry<String, AddressBook> entry : addressBooks.entrySet()) {
			entry.getValue().contacts.stream()
			.filter(contact -> contact.getState().equalsIgnoreCase(enteredState))
			.forEach(contactsInState::add);
		}
		if(contactsInState.size()>0) {
			contactsInState.stream()
			.forEach(contact -> {System.out.println(contact.getFirstName()+" "+contact.getLastName());});
		}	
		else {
			System.out.println("No Contacts Found!");
		}
	}

	@Override
	public void showAllContactsInAllCity(Map<String, AddressBook> addressBooks) {

		List<Contacts> contactsInCity = new ArrayList<>();
		Multimap<String, String> cityContacts = ArrayListMultimap.create();
		for (Entry<String, AddressBook> entry : addressBooks.entrySet()) {
			entry.getValue().contacts.stream()
			.forEach(contactsInCity::add);
		}
		contactsInCity.stream()
		.forEach(contact -> {cityContacts.put(contact.getCity(), contact.getFirstName()+" "+contact.getLastName());});
		if(cityContacts.size()>0) {
			for(Entry<String,Collection<String>> result : cityContacts.asMap().entrySet()) {
				if(result.getValue().size()==0 && result.getKey()!=null) {
					System.out.println(result.getKey()+": No Contacts Found");
					break;
				}

				else if(result.getKey()!=null) {
					System.out.println("City: "+result.getKey()+" Count: "+result.getValue().stream().count()+" Contacts: "+result.getValue());
				}
			}
		}
		else
			System.out.println("No Records Found");
	}
	@Override
	public void showAllContactsInAllState(Map<String, AddressBook> addressBooks) {
		List<Contacts> contactsInState = new ArrayList<>();
		Multimap<String, String> stateContacts = ArrayListMultimap.create();
		for (Entry<String, AddressBook> entry : addressBooks.entrySet()) {
			entry.getValue().contacts.stream()
			.forEach(contactsInState::add);
		}
		contactsInState.stream()
		.forEach(contact -> {stateContacts.put(contact.getState(), contact.getFirstName()+" "+contact.getLastName());});
		if(stateContacts.size()>0) {
			for(Entry<String,Collection<String>> result : stateContacts.asMap().entrySet()) {
				if(result.getValue().size()==0 && result.getKey()!=null) {
					System.out.println(result.getKey()+": No Contacts Found");
					break;
				}

				else if(result.getKey()!=null) {
					System.out.println("State: "+result.getKey()+" Count: "+result.getValue().stream().count()+" Contacts: "+result.getValue());
				}
			}
		}
		else
			System.out.println("No Records Found");
	}

	@Override
	public void console() {

		System.out.println();
		System.out.print("Number Of Address Books:");
		int numberOfAddressBooks = AddressBookMain.scanner.nextInt();
		if(numberOfAddressBooks <= 0) {
			System.out.println("No Address Books Found");
			System.exit(0);
		}

		Map<String, AddressBook> addressBooks = new HashMap<String, AddressBook>();
		for(int iterator = 1; iterator<=numberOfAddressBooks; iterator++) {
			System.out.print("Name of Address Books "+iterator+":");
			String nameOfAddressBooks = AddressBookMain.scanner.next();
			AddressBook addressBook = new AddressBook();
			addressBooks.put(nameOfAddressBooks, addressBook);
		}
		AddressBookIF addressBookOperations = new AddressBookImpl();
		ContactsIF contacts = new ContactsImpl();
		AddressBook addressBook = null;
		do {
			int exitFlag=0;
			System.out.println("[1] Select Active Address Book\n[2] Contact operations\n[3] See contacts in a city or state\n[4] See contacts in all cities or states\n[5] Exit");
			System.out.print("Enter Choice: "); 
			int addressChoice = AddressBookMain.scanner.nextInt();
			switch(addressChoice) {
			case 1: addressBook = addressBookOperations.selectActiveAddressBook(addressBooks);
			if(addressBook == null)
				System.out.println("No Address Books Present");
			else
				System.out.println("Active Address Book Name: "+addressBookOperations.getKey(addressBooks, addressBook));
			break;
			case 2:
				if(addressBook==null) {
					System.out.println("Select Active Address Book!");
					break;
				}
				while(exitFlag == 0) {

					System.out.println("Active Address Book Name: "+addressBookOperations.getKey(addressBooks, addressBook));
					System.out.println("[1] Add Contact\n[2] Edit Contact\n[3] Delete Contact\n[4] Print Contacts\n[5] Back");
					System.out.print("Enter Choice: "); 
					int contactsChoice = AddressBookMain.scanner.nextInt();
					switch(contactsChoice) {
					case 1:  
						System.out.print("Number Of Contacts:");
						int numberOfContacts = AddressBookMain.scanner.nextInt();
						for(int iterator = 1; iterator<=numberOfContacts; iterator++) {
							System.out.println("Enter Contact "+iterator+" Details: ");
							contacts.addContact(addressBook);
						} 
						break;
					case 2:contacts.editContact(addressBook);
					break;
					case 3:contacts.deleteContact(addressBook);
					break;
					case 4: contacts.printContact(addressBook);
					break;
					case 5:exitFlag=1;
					break;
					default: System.out.println("Invalid Choice!");
					}
				}
				break;
			case 3:System.out.println("[1]Show contacts in city\n[2]Show contacts in state\n[3]Back");
			System.out.print("Enter Choice: "); 
			int searchChoice = AddressBookMain.scanner.nextInt();
			while(exitFlag==0) {
				switch(searchChoice) {
				case 1:System.out.print("Enter City: "); 
				String searchByCity = AddressBookMain.scanner.next();
				addressBookOperations.showContactsInGivenCity(searchByCity, addressBooks);
				exitFlag=1;
				break;
				case 2: System.out.print("Enter State: ");  
				String searchByState = AddressBookMain.scanner.next();
				addressBookOperations.showContactsInGivenState(searchByState, addressBooks);
				exitFlag=1;
				break;
				case 3:exitFlag=1;
				break;
				default:System.out.println("Invalid Choice");
				}
			}
			break;
			case 4:System.out.println("[1]Show contacts in all cities\n[2]Show contacts in all states\n[3]Back");
			System.out.print("Enter Choice: "); 
			int printChoice = AddressBookMain.scanner.nextInt();
			while(exitFlag==0) {
				switch(printChoice) {
				case 1:addressBookOperations.showAllContactsInAllCity(addressBooks);
				exitFlag=1;
				break;
				case 2: addressBookOperations.showAllContactsInAllState(addressBooks);
				exitFlag=1;
				break;
				case 3:exitFlag=1;
				break;
				default:System.out.println("Invalid Choice");
				}
			}
			break;
			case 5: AddressBookMain.scanner.close(); 
			System.exit(0);
			break;
			default: System.out.println("Invalid Choice!");
			}
		}while(true);

	}
}
