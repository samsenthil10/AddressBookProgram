package com.bridgelabz.addressbookprogram;

import java.util.Scanner;

public class AddressBookMain {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("----------Welcome To Address Book Program----------");	
		AddressBookIF addressBookOperations = new AddressBookImpl();
		addressBookOperations.console();
	}
}