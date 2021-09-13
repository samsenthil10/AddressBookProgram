package com.bridgelabz.addressbookprogram;

public interface ContactsIF {

	public void addContact(AddressBook addressBook);
	public void editContact(AddressBook addressBook);
	public void deleteContact(AddressBook addressBook);
	public void printContact(AddressBook addressBook);
	public boolean equals(String enteredPhoneNumber, AddressBook addressBook);
}
