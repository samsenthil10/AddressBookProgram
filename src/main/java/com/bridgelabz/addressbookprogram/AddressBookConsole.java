package com.bridgelabz.addressbookprogram;

import java.io.IOException;
import java.util.Scanner;
import com.bridgelabz.addressbookprogram.IOServiceMode.IOService;
import com.opencsv.exceptions.CsvException;

public class AddressBookConsole {
	
	public void performOperations() throws IOException, CsvException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Address book system program");

		AddressBookImpl addressBookOperations = new AddressBookImpl();

		int addressBookMenuChoice;

		do {
			System.out.println("Menu: \n1)Create addressbook \n2)Perform operations on given addressbook \n3)Quit");
			addressBookMenuChoice = scanner.nextInt();
			scanner.nextLine();
			if (addressBookMenuChoice == 1) {
				createAddrressBook(scanner, addressBookOperations);

			} else if (addressBookMenuChoice == 2) {
				System.out.println("Enter addressbook name to perform operations:");
				String addressbookName = scanner.next();
				System.out.println("Menu for the given addressbook: \n1)Add contact\n2)Edit contact\n3Delete contac\n4)Display contact" +
						"\n5)Search by City\n6)Search by State\n7)Get all contacts in city\n8Get all contacts in state\n" +
						"9)Count people in city\n10)Count peopel in state \n11)Sort by name)" +
						"\n12)sort by city \n13)sort by state \n14)sort by zip\n15)Write to file\n16)Read from file");
				int choice = scanner.nextInt();
				scanner.nextLine();

				switch (choice) {
				case 1:

					addContact(scanner, addressBookOperations, addressbookName);
					break;

				case 2:
					editContact(scanner, addressBookOperations, addressbookName);
					break;

				case 3:
					deleteContact(scanner, addressBookOperations, addressbookName);
					break;

				case 4:
					displayContact(scanner, addressBookOperations, addressbookName);
					break;

				case 5:
					searchPersonByCity(scanner, addressBookOperations);
					break;
				case 6:
					searchPersonByState(scanner, addressBookOperations);
					break;
				case 7:
					getAllContactsInCity(scanner, addressBookOperations);
					break;
				case 8:
					getAllContactsInState(scanner, addressBookOperations);
					break;
				case 9:
					countPeopleInCity(scanner, addressBookOperations);
					break;
				case 10:
					countPeopleInState(scanner, addressBookOperations);
					break;
				case 11:
					addressBookOperations.sortByName();
					break;
				case 12:
					addressBookOperations.sortByCity();
					break;
				case 13:
					addressBookOperations.sortByState();
					break;
				case 14:
					addressBookOperations.sortByZip();
					break;
				case 15:
					addressBookOperations.writeContactsOfAddressBook(IOService.FILE_IO, addressbookName);
					break;
				case 16:
					addressBookOperations.readContactListData(IOService.FILE_IO, addressbookName);

				}

			} else if (addressBookMenuChoice == 3) {
				break;
			} else {
				System.out.println("Wrong choice");
			}
		} while (addressBookMenuChoice != 3);

	}

	private void createAddrressBook(Scanner scanner, AddressBookImpl addressBookOperations) {
		System.out.println("Enter name for the addressbook");
		String addressBookName = scanner.nextLine();
		addressBookOperations.createAddressBook(addressBookName);
	}

	private void countPeopleInState(Scanner scanner, AddressBookImpl addressBookOperations) {
		String state;
		System.out.println("Enter state to search");
		state = scanner.nextLine();
		addressBookOperations.countPeopleinState(state);
	}

	private void countPeopleInCity(Scanner scanner, AddressBookImpl addressBookOperations) {
		String city;
		System.out.println("Enter city to search");
		city = scanner.nextLine();
		addressBookOperations.countPeopleinCity(city);
	}

	private void getAllContactsInState(Scanner scanner, AddressBookImpl addressBookOperations) {
		String state;
		System.out.println("Enter state to search");
		state = scanner.nextLine();
		addressBookOperations.getAllContactsInState(state);
	}

	private void getAllContactsInCity(Scanner scanner, AddressBookImpl addressBookOperations) {
		String city;
		System.out.println("Enter city to search");
		city = scanner.nextLine();
		addressBookOperations.getAllContactsInCity(city);
	}

	private void searchPersonByState(Scanner scanner, AddressBookImpl addressBookOperations) {
		String state;
		String name;
		System.out.println("Enter name of the person to search");
		name = scanner.nextLine();
		System.out.println("Enter state to search");
		state = scanner.nextLine();
		addressBookOperations.searchPersonByState(name, state);
	}

	private void searchPersonByCity(Scanner scanner, AddressBookImpl addressBookOperations) {
		String city;
		String name;
		System.out.println("Enter name of the person to search");
		name = scanner.nextLine();
		System.out.println("Enter city to search");
		city = scanner.nextLine();
		addressBookOperations.searchPersonByCity(name, city);
	}

	private void displayContact(Scanner scanner, AddressBookImpl addressBookOperations, String addressbookName) {
		String phoneNumber;
		System.out.println("Enter phone number of contact to be display");
		phoneNumber = scanner.nextLine();
		addressBookOperations.displayContactInfo(phoneNumber, addressbookName);
	}

	private void deleteContact(Scanner scanner, AddressBookImpl addressBookOperations, String addressbookName) {
		String phoneNumber;
		System.out.println("Enter phone number of contact to be deleted");
		phoneNumber = scanner.nextLine();
		addressBookOperations.deleteContact(phoneNumber, addressbookName);
	}

	private void editContact(Scanner scanner, AddressBookImpl addressBookOperations, String addressbookName) {
		String phoneNumber;
		System.out.println("Enter phone number of contact to be edited");
		phoneNumber = scanner.nextLine();
		System.out.println("Enter the field and value to be edited");
		String fieldToBeEdited = scanner.nextLine();
		String valueToBeEdited = scanner.nextLine();
		addressBookOperations.editContact(phoneNumber, valueToBeEdited, fieldToBeEdited, addressbookName);
	}

	private void addContact(Scanner scanner, AddressBookImpl addressBookOperations, String addressbookName) {
		String firstName;
		String lastName;
		String houseNumber, street;
		String city;
		String state;
		String zip;
		String phoneNumber;
		String email;
		System.out.println("Enter first and last name of the person");
		firstName = scanner.nextLine();
		lastName = scanner.nextLine();
		System.out.println("Enter HouseNumber ");
		houseNumber = scanner.nextLine();
		System.out.println("Enter street ");
		street = scanner.nextLine();
		System.out.println("Enter city ");
		city = scanner.nextLine();
		System.out.println("Enter state ");
		state = scanner.nextLine();
		System.out.println("Enter zip ");
		zip = scanner.nextLine();
		System.out.println("Enter phone number ");
		phoneNumber = scanner.nextLine();
		System.out.println("Enter email ");
		email = scanner.nextLine();
		Address address = new Address(houseNumber, street, city, state, zip);
		Contact person = new Contact(firstName, lastName, address, phoneNumber, email);
		addressBookOperations.addNewContact(person, addressbookName);
	}
}