package com.bridgelabz.addressbookprogram;

import java.util.Scanner;

public class AddressBookMain {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("----------Welcome To Address Book Program----------");	
		System.out.println();
		AddressBook addressBook = new AddressBook();
		ContactsIF contacts = new ContactsImpl();
		System.out.print("Number Of Contacts:");
		int numberOfContacts = scanner.nextInt();
		for(int iterator = 1; iterator<=numberOfContacts; iterator++) {
			System.out.println("Enter Contact "+iterator+" Details: ");
			contacts.addContact(addressBook);
		}
		contacts.printContact(addressBook);
	}
}