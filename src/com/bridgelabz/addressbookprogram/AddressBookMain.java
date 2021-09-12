package com.bridgelabz.addressbookprogram;

import java.util.Scanner;

public class AddressBookMain {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("----------Welcome To Address Book Program----------");	
		System.out.println();
		ContactsIF contacts = new ContactsImpl();
		AddressBook addressBook = new AddressBook(contacts.addContact());
		contacts.printContact(addressBook);
		contacts.editContact(addressBook);
		contacts.printContact(addressBook);
		contacts.deleteContact(addressBook);
	}
}
