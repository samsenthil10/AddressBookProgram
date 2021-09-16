package com.bridgelabz.addressbookprogram;

import java.util.*;
import java.util.stream.Collectors;

public class ContactsImpl implements ContactsIF {

	@Override
	public void addContact(AddressBook addressBook) {

		Contacts contact = new Contacts();
		System.out.print("Enter Phone Number: ");
		String phoneNumber = AddressBookMain.scanner.next();
		boolean checkAlreadyExist = addressBook.contacts.stream()
				.anyMatch(n -> n.getPhoneNumber().equalsIgnoreCase(phoneNumber));
		if(checkAlreadyExist == true){
			System.out.println("Contact already exits!");
		}
		else {
			contact.setPhoneNumber(phoneNumber);
			System.out.print("Enter First Name: ");
			contact.setFirstName(AddressBookMain.scanner.next());
			System.out.print("Enter Last Name: ");
			contact.setLastName(AddressBookMain.scanner.next());
			System.out.print("Enter Address: ");
			contact.setAddress(AddressBookMain.scanner.next());
			System.out.print("Enter City: ");
			contact.setCity(AddressBookMain.scanner.next());
			System.out.print("Enter State: ");
			contact.setState(AddressBookMain.scanner.next());
			System.out.print("Enter Pin Code: ");
			contact.setZip(AddressBookMain.scanner.next());
			System.out.print("Enter E mail: ");
			contact.setEmail(AddressBookMain.scanner.next());
			addressBook.contacts.add(contact);
		}	
	}

	@Override
	public void editContact(AddressBook addressBook) {

		Iterator<Contacts> iterator = addressBook.contacts.iterator();
		if(iterator.hasNext()==false) {
			System.out.println("Contacts Empty!");
		}
		else {
			System.out.print("Enter Phone Number: ");
			String edit = AddressBookMain.scanner.next();
			Contacts contact = null;
			int exitFlag=0;
			while(iterator.hasNext()) {
				contact = iterator.next();
				if(edit.equalsIgnoreCase(contact.getPhoneNumber())){
					exitFlag=1;
					System.out.println("Choose field to edit:\n[1] First Name\n[2] Last Name\n[3] Address\n[4] City\n[5] State\n[6] Pin Code\n[7] Phone Number\n[8] Email\n[9] Back");
					int choice = AddressBookMain.scanner.nextInt();
					switch(choice) {

					case 1: System.out.print("Enter First Name: ");
					contact.setFirstName(AddressBookMain.scanner.next());
					System.out.println("First Name Successfully Edited");
					break;
					case 2: System.out.print("Enter Last Name: ");
					contact.setLastName(AddressBookMain.scanner.next());
					System.out.println("Last Name Successfully Edited");
					break;
					case 3: System.out.print("Enter Address: ");
					contact.setAddress(AddressBookMain.scanner.next());
					System.out.println("Address Successfully Edited");
					break;
					case 4: System.out.print("Enter City: ");
					contact.setCity(AddressBookMain.scanner.next());
					System.out.println("City Successfully Edited");
					break;
					case 5: System.out.print("Enter State: ");
					contact.setState(AddressBookMain.scanner.next());
					System.out.println("State Successfully Edited");
					break;
					case 6: System.out.print("Enter Pin Code: ");
					contact.setZip(AddressBookMain.scanner.next());
					System.out.println("Pin Code Successfully Edited");
					break;
					case 7: System.out.print("Enter Phone Number: ");
					contact.setPhoneNumber(AddressBookMain.scanner.next());
					System.out.println("Phone Number Successfully Edited");
					break;
					case 8: System.out.print("Enter E mail: ");
					contact.setEmail(AddressBookMain.scanner.next());
					System.out.println("Email Successfully Edited");
					break;
					case 9: break;
					default: System.out.println("Invalid Choice");
					}		
				}
			}
			if(exitFlag==0)
				System.out.println("Contact Not Found");
		}
	}

	@Override
	public void deleteContact(AddressBook addressBook) {

		Iterator<Contacts> iterator = addressBook.contacts.iterator();
		if(iterator.hasNext()==false) {
			System.out.println("Contacts Empty!");
		}
		else {
			System.out.print("Enter Phone Number: ");
			String delete = AddressBookMain.scanner.next();
			Contacts contact = null;
			int exitFlag=0;
			while(iterator.hasNext()) {
				contact = iterator.next();
				if(delete.equalsIgnoreCase(contact.getPhoneNumber())){
					exitFlag=1;
					addressBook.contacts.remove(contact);
					System.out.println("Contact Successfully Deleted");
					break;
				}
			}
			if(exitFlag==0)
				System.out.println("Contact Not Found");
		}

	}

	@Override
	public void printContact(AddressBook addressBook) {

		Iterator<Contacts> iterator = addressBook.contacts.iterator();
		if(iterator.hasNext() == false) {
			System.out.println("Contacts empty!");
		}
		else {
			while(iterator.hasNext()){
				Contacts contact = iterator.next();
				System.out.println();
				System.out.println(contact.getFirstName());
				System.out.println(contact.getLastName());
				System.out.println(contact.getAddress());
				System.out.println(contact.getCity());
				System.out.println(contact.getState());
				System.out.println(contact.getZip());
				System.out.println(contact.getPhoneNumber());
				System.out.println(contact.getEmail());
				System.out.println();	
			} 
		}
	}

	@Override
	public void sortedContacts(AddressBook addressBook) {
		int exitFlag=0;
		System.out.println("[1]Sort by Name\n[2]Back");
		System.out.print("Enter Choice: "); 
		int searchChoice = AddressBookMain.scanner.nextInt();
		while(exitFlag==0) {
			switch(searchChoice) {
			case 1:List<Contacts> sortedContacts = addressBook.contacts.stream()
					.collect(Collectors.toList());
			sortedContacts.sort(Comparator.comparing(Contacts::getFirstName));
			sortedContacts.stream()
			.forEach(contact -> {System.out.println();
			System.out.println(contact.getFirstName());
			System.out.println(contact.getLastName());
			System.out.println(contact.getAddress());
			System.out.println(contact.getCity());
			System.out.println(contact.getState());
			System.out.println(contact.getZip());
			System.out.println(contact.getPhoneNumber());
			System.out.println(contact.getEmail());
			System.out.println();});
			exitFlag=1;
			break;
			case 2: exitFlag=1;
			break;
			default:System.out.println("Invalid Choice");
			}
		}
	}
}