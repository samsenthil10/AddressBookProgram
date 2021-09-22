package com.bridgelabz.addressbookprogram;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.bridgelabz.addressbookprogram.AddressBookImpl.IOService;
import com.opencsv.CSVReader;


public class AddressBookIO {

	public void readFromFile(Map<String, AddressBook> addressBooks,AddressBook addressBook, IOService ioservice) {

		if(ioservice.equals(IOService.FILE_IO))
			readFromTextFile(addressBooks, addressBook);
		else if(ioservice.equals(IOService.CSV_IO))
			readFromCsvFile(addressBooks, addressBook);
		else if(ioservice.equals(IOService.JSON_IO))
			readFromJsonFile(addressBooks, addressBook);
	}

	public void writeToFile(Map<String, AddressBook> addressBooks,AddressBook addressBook , IOService ioservice) {

		if(ioservice.equals(IOService.FILE_IO))
			writeToTextFile(addressBooks, addressBook);
		else if(ioservice.equals(IOService.CSV_IO))
			writeToCsvFile(addressBooks, addressBook);
		else if(ioservice.equals(IOService.JSON_IO))
			writeToJsonFile(addressBooks, addressBook);
	}

	private void readFromTextFile(Map<String, AddressBook> addressBooks, AddressBook addressBook) {
		AddressBookIF addressBookOperations = new AddressBookImpl();
		String fileName = addressBookOperations.getKey(addressBooks, addressBook);
		String filePath = "./txt/"+fileName+".txt";
		Path path = Paths.get(filePath);
		boolean exists = Files.exists(path);
		if(exists) {
			String lines;
			try {
				lines = Files.readString(path);
				if(lines.equalsIgnoreCase(""))
					System.out.println("Empty File!");
				else
					System.out.println(lines);
			} catch (IOException e) {
			}
		}
		else {
			System.out.println("File Does Not Exist!");
		}
	}

	private void writeToTextFile(Map<String, AddressBook> addressBooks, AddressBook addressBook) {
		AddressBookIF addressBookOperations = new AddressBookImpl();
		String fileName = addressBookOperations.getKey(addressBooks, addressBook);
		String filePath = "./txt/"+fileName+".txt";
		Path path = Paths.get(filePath);
		try {
			Files.deleteIfExists(path);
		} 
		catch (IOException e) {

		}
		Iterator<Contacts> iterator = addressBook.contacts.iterator();
		String line="";
		while(iterator.hasNext()) {
			Contacts contact = iterator.next();

			line = contact.getFirstName();
			line+="\n"+contact.getLastName();
			line+="\n"+contact.getAddress();
			line+="\n"+contact.getCity();
			line+="\n"+contact.getState();
			line+="\n"+contact.getZip();
			line+="\n"+contact.getPhoneNumber();
			line+="\n"+contact.getEmail();
			line+="\n";
			line+="\n";
			try {
				Files.write(path, line.getBytes(StandardCharsets.UTF_8),
						StandardOpenOption.CREATE,StandardOpenOption.APPEND);
			} catch (IOException e) {
			}
		}
	}

	private void readFromCsvFile(Map<String, AddressBook> addressBooks,AddressBook addressBook) {

		AddressBookIF addressBookOperations = new AddressBookImpl();
		String fileName = addressBookOperations.getKey(addressBooks, addressBook);
		String filePath = "./csv/"+fileName+".csv";
		Path path = Paths.get(filePath);
		List<String[]> records = null;
		boolean exists = Files.exists(path);
		if(exists) {
			try {
				CSVReader reader = new CSVReader(new FileReader(filePath));

				try {
					records = reader.readAll();
				} catch (IOException e) {

				}
				for (String[] record : records) {
					System.out.println("First Name : " + record[0]);
					System.out.println("Last Name : " + record[1]);
					System.out.println("Address : " + record[2]);
					System.out.println("City : " + record[3]);
					System.out.println("State : " + record[4]);
					System.out.println("Pin Code : " + record[5]);
					System.out.println("Phone Number : " + record[6]);
					System.out.println("Email : " + record[7]);
					System.out.println();
				}
			} catch (FileNotFoundException e) {

			}
		}
		else {
			System.out.println("File Does Not Exist!");
		}
	}

	private void writeToCsvFile(Map<String, AddressBook> addressBooks,AddressBook addressBook) {

		AddressBookIF addressBookOperations = new AddressBookImpl();
		String fileName = addressBookOperations.getKey(addressBooks, addressBook);
		String filePath = "./csv/"+fileName+".csv";
		Path path = Paths.get(filePath);
		try {
			Files.deleteIfExists(path);
		} 
		catch (IOException e) {

		}
		Iterator<Contacts> iterator = addressBook.contacts.iterator();
		String line="";
		while(iterator.hasNext()) {
			Contacts contact = iterator.next();

			line = contact.getFirstName();
			line+=","+contact.getLastName();
			line+=","+contact.getAddress();
			line+=","+contact.getCity();
			line+=","+contact.getState();
			line+=","+contact.getZip();
			line+=","+contact.getPhoneNumber();
			line+=","+contact.getEmail();
			line+="\n";
			try {
				Files.write(path, line.getBytes(StandardCharsets.UTF_8),
						StandardOpenOption.CREATE,StandardOpenOption.APPEND);
			} catch (IOException e) {
			}
		}
	}

	private void readFromJsonFile(Map<String, AddressBook> addressBooks,AddressBook addressBook) {

	}
	private void writeToJsonFile(Map<String, AddressBook> addressBooks,AddressBook addressBook) {

	}
}