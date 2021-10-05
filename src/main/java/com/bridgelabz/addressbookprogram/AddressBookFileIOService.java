package com.bridgelabz.addressbookprogram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookFileIOService {
    public static String ADDRESSBOOK_TXT_FILE_NAME = "addressbook.txt";
    public static String ADDRESSBOOK_CSV_FILE_NAME = "addressbook.csv";
    public static String ADDRESSBOOK_JSON_FILE_NAME = "addressbook.json";
    public static String ADDRESSBOOK_FILE_NAME = "addressbookStream.txt";


    public enum FileType {
        TXT,
        CSV,
        JSON
    }


    public void writeData(List < Contact > contactList, FileType fileType, AddressBooks...addressBookSystem) throws IOException, CsvException {

        switch (fileType) {
            case TXT:
                writeDataToTXTFile(contactList);
                break;
            case CSV:
                writeDataToCSVFile(contactList);
                break;
            case JSON:
                writeDataTOJSONFile(addressBookSystem);
        }
    }

    private void writeDataTOJSONFile(AddressBooks...addressBookSystem) {
        try {

            List < AddressBook > addressbookList = addressBookSystem[0].getAddressbookList();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String json = gson.toJson(addressbookList);
            FileWriter writer = new FileWriter(ADDRESSBOOK_JSON_FILE_NAME);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void writeDataToCSVFile(List < Contact > contactList)
    throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {


        try (Writer writer = Files.newBufferedWriter(Paths.get(ADDRESSBOOK_CSV_FILE_NAME))) {

            ColumnPositionMappingStrategy < Contact > strategy = new ColumnPositionMappingStrategy < Contact > ();
            strategy.setType(Contact.class);
            String[] memberFieldsToBindTo = {
                "firstName",
                "lastName",
                "address",
                "phoneNumber",
                "email"
            };
            strategy.setColumnMapping(memberFieldsToBindTo);

            StatefulBeanToCsv < Contact > beanToCsv = new StatefulBeanToCsvBuilder < Contact > (writer)
                .withMappingStrategy(strategy)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();


            beanToCsv.write(contactList);
        }
    }

    private void writeDataToTXTFile(List < Contact > contactList) throws IOException {
        this.writeDataToTXTFileUsingOutputStream(contactList);
        StringBuffer contactBuffer = new StringBuffer();
        contactList.forEach(contact -> {
            String contactDataString = contact.toString().concat("\n");
            contactBuffer.append(contactDataString);
        });

        try {
            Files.write(Paths.get(ADDRESSBOOK_TXT_FILE_NAME), contactBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeDataToTXTFileUsingOutputStream(List < Contact > contactList) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ADDRESSBOOK_FILE_NAME))) {
            objectOutputStream.writeObject(contactList);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void printData(FileType fileType) throws IOException, CsvException {

        switch (fileType) {
            case TXT:
                try {
                    Files.lines(new File(ADDRESSBOOK_TXT_FILE_NAME).toPath())
                        .forEach(System.out::println);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CSV:
                try (
                    Reader reader = Files.newBufferedReader(Paths.get(ADDRESSBOOK_CSV_FILE_NAME)); CSVReader csvReader = new CSVReader(reader);
                ) {
                    List < String[] > records = csvReader.readAll();
                    for (String[] record: records) {
                        System.out.println("First Name: " + record[0]);
                        System.out.println("Last Name: " + record[1]);
                        System.out.println("Adddress: " + record[2]);
                        System.out.println("City: " + record[3]);
                        System.out.println("Zip: " + record[4]);
                        System.out.println("state: " + record[5]);
                        System.out.println("Phone number: " + record[6]);
                        System.out.println("Email: " + record[7]);

                        System.out.println("=========================");

                    }
                }
                break;
            case JSON:
                List < AddressBook > addressBookList = this.readData();
                addressBookList.stream()
                    .forEach(addressBook -> {
                        addressBook.getContactList().stream().forEach(contact -> System.out.println(contact));
                    });
        }
    }

    public long countEntries(FileType fileType) throws IOException, CsvException {

        switch (fileType) {
            case TXT:
                long entries = 0;
                try {
                    entries = Files.lines(new File(ADDRESSBOOK_TXT_FILE_NAME).toPath())
                        .count();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return entries;
            case CSV:
                return this.readData(FileType.CSV).size();
            case JSON:
                return this.readData().size();
        }

        return 0;
    }


    @SuppressWarnings("incomplete-switch")
	public List < Contact > readData(FileType fileType) throws IOException, CsvException {
        List < Contact > contactList = new ArrayList < Contact > ();


        switch (fileType) {
            case TXT:
                contactList = readDataFromTXTFileToList(contactList);
                break;
            case CSV:
                readDataFromCSVFileToList(contactList);

        }
        return contactList;
    }

    private void readDataFromCSVFileToList(List < Contact > contactList) throws IOException, CsvException {
        List < String[] > contactDataFromFile;
        try (
            Reader reader = Files.newBufferedReader(Paths.get(ADDRESSBOOK_CSV_FILE_NAME)); CSVReader csvReader = new CSVReader(reader);
        ) {

            contactDataFromFile = csvReader.readAll();

            contactDataFromFile.stream().forEach(object -> contactList.add(new Contact(object)));
        }
    }

    @SuppressWarnings("unchecked")
	private List < Contact > readDataFromTXTFileToList(List < Contact > contactList) throws IOException {

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ADDRESSBOOK_FILE_NAME))) {
            contactList = (List < Contact > ) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return contactList;
    }

    public List < AddressBook > readData() throws IOException, CsvException {
        BufferedReader br = new BufferedReader(new FileReader(ADDRESSBOOK_JSON_FILE_NAME));
        Gson gson = new Gson();
        AddressBook[] usrObj = gson.fromJson(br, AddressBook[].class);
        List < AddressBook > addressBookList = Arrays.asList(usrObj);

        return addressBookList;

    }

}