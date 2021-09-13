package com.bridgelabz.addressbookprogram;

import java.util.*;

public class ContactsImpl implements ContactsIF {

	@Override 
	public boolean equals(String enteredPhoneNumber, AddressBook addressBook) {
		Iterator<Contacts> iterator = addressBook.contacts.iterator();
		while(iterator.hasNext())
			if(enteredPhoneNumber.equalsIgnoreCase(iterator.next().getPhoneNumber()))
				return true; 
		return false;
	}

	@Override
	public void addContact(AddressBook addressBook) {

		Contacts contact = new Contacts();
		System.out.print("Enter Phone Number: ");
		String phoneNumber = AddressBookMain.scanner.next();
		if(equals(phoneNumber,addressBook)==true){
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
			System.out.print("Enter Phone Number: ");
			contact.setPhoneNumber(AddressBookMain.scanner.next());
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
			while(iterator.hasNext()) {
				contact = iterator.next();
				if(edit.equalsIgnoreCase(contact.getPhoneNumber())){
					break;
				}
			}
			if(contact!=null) {

				System.out.println("Choose field to edit:\n[1] First Name\n[2] Last Name\n[3] Address\n[4] City\n[5] State\n[6] Pin Code\n[7] Phone Number\n[8] Email\n[9] Back");
				int choice = AddressBookMain.scanner.nextInt();
				switch(choice) {

				case 1: System.out.print("Enter First Name: ");
				contact.setFirstName(AddressBookMain.scanner.next());
				break;
				case 2: System.out.print("Enter Last Name: ");
				contact.setLastName(AddressBookMain.scanner.next());
				break;
				case 3: System.out.print("Enter Address: ");
				contact.setAddress(AddressBookMain.scanner.next());
				break;
				case 4: System.out.print("Enter City: ");
				contact.setCity(AddressBookMain.scanner.next());
				break;
				case 5: System.out.print("Enter State: ");
				contact.setState(AddressBookMain.scanner.next());
				break;
				case 6: System.out.print("Enter Pin Code: ");
				contact.setZip(AddressBookMain.scanner.next());
				break;
				case 7: System.out.print("Enter Phone Number: ");
				contact.setPhoneNumber(AddressBookMain.scanner.next());
				break;
				case 8: System.out.print("Enter E mail: ");
				contact.setEmail(AddressBookMain.scanner.next());
				break;
				case 9: System.exit(0);
				break;
				default: System.out.println("Invalid Choice");
				}
			}
			else {

				System.out.println("Contact Not Found!");
			}
		}

	}

	@Override
	public void deleteContact(AddressBook addressBook) {

		Iterator<Contacts> iterator = addressBook.contacts.iterator();
		if(iterator.hasNext() == false) {
			System.out.println("Contacts empty!");
		}		
		else {
			System.out.print("Enter Phone Number: ");
			String delete = AddressBookMain.scanner.next();

			Contacts contact = null;
			while(iterator.hasNext()) {
				contact = iterator.next();
				if(delete.equalsIgnoreCase(contact.getPhoneNumber())){
					break;
				}
			}
			if(contact!=null) {
				addressBook.contacts.remove(contact);
			}
			else {

				System.out.println("Contact Not Found!");
			}
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
}