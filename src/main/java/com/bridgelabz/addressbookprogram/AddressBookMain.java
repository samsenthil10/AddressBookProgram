package com.bridgelabz.addressbookprogram;

import java.io.IOException;

import com.opencsv.exceptions.CsvException;

public class AddressBookMain {

	public static void main(String[] args) throws IOException, CsvException {
		
		AddressBookConsole menu = new AddressBookConsole();
		menu.performOperations();
	}
}