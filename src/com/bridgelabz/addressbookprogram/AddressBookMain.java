package com.bridgelabz.addressbookprogram;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("----------Welcome To Address Book Program----------");	
		System.out.println();
		System.out.print("Number Of Address Books:");
		int numberOfAddressBooks = scanner.nextInt();
		Map<String, AddressBook> addressBooks = new HashMap<String, AddressBook>();
		for(int iterator = 1; iterator<=numberOfAddressBooks; iterator++) {
			System.out.print("Name of Address Books "+iterator+":");
			String nameOfAddressBooks = scanner.next();
			AddressBook addressBook = new AddressBook();
			addressBooks.put(nameOfAddressBooks, addressBook);
		}
		AddressBookIF addressBookOperations = new AddressBookImpl();
		ContactsIF contacts = new ContactsImpl();
		AddressBook addressBook = null;
		do {
			int exitFlag=0;
			System.out.println("[1] Select Active Address Book\n[2] Contact operations\n[3] Exit");
			int addressChoice = scanner.nextInt();
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
					System.out.println("[1] Add Contact\n[2] Edit Contact\n[3] Delete Contact\n[4] Print Contacts\n[5] Exit");
					int contactsChoice = scanner.nextInt();
					switch(contactsChoice) {
					case 1:  
						System.out.print("Number Of Contacts:");
						int numberOfContacts = scanner.nextInt();
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
			case 3: scanner.close(); 
			System.exit(0);
			break;
			default: System.out.println("Invalid Choice!");
			}
		}while(true);
	}
}