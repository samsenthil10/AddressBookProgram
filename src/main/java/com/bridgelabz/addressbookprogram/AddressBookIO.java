package com.bridgelabz.addressbookprogram;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Map;

public class AddressBookIO {

	public void readFromFile(Map<String, AddressBook> addressBooks,AddressBook addressBook) {

		AddressBookIF addressBookOperations = new AddressBookImpl();
		String fileName = addressBookOperations.getKey(addressBooks, addressBook);
		String filePath = "./"+fileName+".txt";
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

	public void writeToFile(Map<String, AddressBook> addressBooks,AddressBook addressBook) {

		AddressBookIF addressBookOperations = new AddressBookImpl();
		String fileName = addressBookOperations.getKey(addressBooks, addressBook);
		String filePath = "./"+fileName+".txt";
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
}