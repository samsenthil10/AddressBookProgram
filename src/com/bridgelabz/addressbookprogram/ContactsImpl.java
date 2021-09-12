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

	@Override
	public void editContact(AddressBook addressBook) {

		System.out.print("Enter Phone Number: ");
		String edit = AddressBookMain.scanner.next();
		if(edit.equalsIgnoreCase(addressBook.contact.getPhoneNumber())) {

			System.out.println("Choose field to edit:\n[1] First Name\n[2] Last Name\n[3] Address\n[4] City\n[5] State\n[6] Pin Code\n[7] Phone Number\n[8] Email\n[9] Exit");
			int choice = AddressBookMain.scanner.nextInt();
			switch(choice) {

			case 1: System.out.print("Enter First Name: ");
			addressBook.contact.setFirstName(AddressBookMain.scanner.next());
			break;
			case 2: System.out.print("Enter Last Name: ");
			addressBook.contact.setLastName(AddressBookMain.scanner.next());
			break;
			case 3: System.out.print("Enter Address: ");
			addressBook.contact.setAddress(AddressBookMain.scanner.next());
			break;
			case 4: System.out.print("Enter City: ");
			addressBook.contact.setCity(AddressBookMain.scanner.next());
			break;
			case 5: System.out.print("Enter State: ");
			addressBook.contact.setState(AddressBookMain.scanner.next());
			break;
			case 6: System.out.print("Enter Pin Code: ");
			addressBook.contact.setZip(AddressBookMain.scanner.next());
			break;
			case 7: System.out.print("Enter Phone Number: ");
			addressBook.contact.setPhoneNumber(AddressBookMain.scanner.next());
			break;
			case 8: System.out.print("Enter E mail: ");
			addressBook.contact.setEmail(AddressBookMain.scanner.next());
			break;
			case 9: System.exit(0);
			break;
			default: System.out.println("Invalid Choice");
			}
		}
		else {

			System.out.println("Contact Not Found!");
			System.exit(0);
		}
	}

	@Override
	public void deleteContact(AddressBook addressBook) {

		System.out.print("Enter Phone Number: ");
		String delete = AddressBookMain.scanner.next();
		if(delete.equalsIgnoreCase(addressBook.contact.getPhoneNumber())) {
			addressBook.contact = null;
		}
		else {

			System.out.println("Contact Not Found!");
			System.exit(0);
		}
	}

	@Override
	public void printContact(AddressBook addressBook) {

		System.out.println(addressBook.contact.getFirstName());
		System.out.println(addressBook.contact.getLastName());
		System.out.println(addressBook.contact.getAddress());
		System.out.println(addressBook.contact.getCity());
		System.out.println(addressBook.contact.getState());
		System.out.println(addressBook.contact.getZip());
		System.out.println(addressBook.contact.getPhoneNumber());
		System.out.println(addressBook.contact.getEmail());
	}
}
