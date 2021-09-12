package com.bridgelabz.addressbookprogram;

import java.util.Scanner;

public class AddressBookMain {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("----------Welcome To Address Book Program----------");	
		System.out.println();
		ContactsIF contacts = new ContactsImpl();
		AddressBook addressBook = new AddressBook(contacts.addContact());
		System.out.println(addressBook.contact.getFirstName());
		System.out.println(addressBook.contact.getLastName());
		System.out.println(addressBook.contact.getCity());
		System.out.println(addressBook.contact.getState());
		System.out.println(addressBook.contact.getZip());
		System.out.println(addressBook.contact.getPhoneNumber());
		System.out.println(addressBook.contact.getEmail());
	}
}
