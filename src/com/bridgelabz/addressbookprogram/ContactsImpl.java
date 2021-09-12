package com.bridgelabz.addressbookprogram;

public class ContactsImpl implements ContactsIF {

	@Override
	public Contacts addContact() {

		Contacts contact = new Contacts();
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
		return contact;
	}
}
