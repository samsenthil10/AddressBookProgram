package com.bridgelabz.addressbookprogram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.bridgelabz.addressbookprogram.AddressBookException.ExceptionType;

public class AddressBookJdbcService implements Runnable {

    private static AddressBookJdbcService addressBookDBService;
    private PreparedStatement addressBookReadStatement;

    private Contact contact;
    String addressBookName;
    LocalDate dateAdded;

    public AddressBookJdbcService() {

    }

    public AddressBookJdbcService(Contact contact, String addressBookName, LocalDate dateAdded) {
        this.contact = contact;
        this.addressBookName = addressBookName;
        this.dateAdded = dateAdded;

    }
    public static AddressBookJdbcService getInstance() {
        if (addressBookDBService == null) {
            addressBookDBService = new AddressBookJdbcService();
        }
        return addressBookDBService;
    }
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?allowPublicKeyRetrieval=true&useSSL=false";
        String username = "root";
        String password = "root@123";
        Connection connection = null;
        System.out.println("Connecting to database" + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connection is successfull!!" + connection);


        return connection;
    }

    public void setupDatabase() throws SQLException, FileNotFoundException {
        Connection connection = this.getConnection();
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader("AddressBookScript.sql"));
        sr.runScript(reader);


    }

    public List < ContactDTO > readContactList(String addressbookName) throws AddressBookException

    {
        List < ContactDTO > contactList = new ArrayList < ContactDTO > ();
        if (this.addressBookReadStatement == null) {
            String sql = "SELECT * FROM contact JOIN address ON contact.address_id=address.address_id JOIN address_book ON contact.address_book_id=address_book.address_book_id" +
                " where address_book_name=?;";
            this.preparedStatementForGivenQuery(sql);
        }
        try {
            if (addressbookName.isEmpty()) {
                throw new AddressBookException(ExceptionType.ENTERED_EMPTY, "Please enter valid AddressBook Name");
            }
            addressBookReadStatement.setString(1, addressbookName);
            ResultSet resultSet = addressBookReadStatement.executeQuery();
            contactList = this.getContactData(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new AddressBookException(ExceptionType.ENTERED_NULL, "Please enter valid AddressBook Name");
        }

        return contactList;
    }

    private void preparedStatementForGivenQuery(String sql) {
        try {
            Connection connection = this.getConnection();
            addressBookReadStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List < ContactDTO > getContactData(String name) {
        String sql = String.format("SELECT * FROM contact JOIN address ON contact.address_id=address.address_id WHERE firstName='%s';", name);
        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return this.getContactData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public List < ContactDTO > getContactData(ResultSet resultSet) {
        List < ContactDTO > contactList = new ArrayList < ContactDTO > ();
        try {

            while (resultSet.next()) {
                int id = resultSet.getInt("contact_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String houseNumber = resultSet.getString("house_number");
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zip = resultSet.getString("zip");
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");

                Address contactAddress = new Address(houseNumber, street, city, state, zip);
                ContactDTO contact = new ContactDTO(id, firstName, lastName, contactAddress, phoneNumber, email);
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactList;

    }

    public int updateContactPhoneNumber(Contact contact, String phoneNumber) {

        String sql = "UPDATE contact SET phoneNumber = ? WHERE firstName = ? AND lastName = ? AND email = ?;";


        this.preparedStatementForGivenQuery(sql);

        try {
            addressBookReadStatement.setString(1, contact.getPhoneNumber());
            addressBookReadStatement.setString(2, contact.getFirstName());
            addressBookReadStatement.setString(3, contact.getLastName());
            addressBookReadStatement.setString(4, contact.getEmail());


            return addressBookReadStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int countOfContactsAddedInGivenDateRange(String startDate, String endDate) {
        String sql = String.format("SELECT * FROM contact JOIN address ON contact.address_id=address.address_id" +
            " WHERE date_added BETWEEN '%s' AND '%s';", Date.valueOf(startDate), Date.valueOf(endDate));

        return getSelectQueryResult(sql).size();

    }
    public int countOfContactsInDataBase() {
        String sql = "SELECT * FROM contact JOIN address ON contact.address_id=address.address_id;";
        return getSelectQueryResult(sql).size();

    }
    public List < ContactDTO > getSelectQueryResult(String sql) {
        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return this.getContactData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public List < ContactDTO > readContactListOfState(String givenState) {
        String sql = String.format("SELECT * FROM contact JOIN address ON contact.address_id=address.address_id" +
            " where address.state=\"%s\";", givenState);

        List < ContactDTO > contactList = new ArrayList < ContactDTO > ();
        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactList = this.getContactData(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactList;
    }



    public int countOfContactsInGivenStateCity(String city, String state, String addressBook) {

        int count = 0;
        String sql = String.format("SELECT count(contact_id) As total FROM contact JOIN address ON contact.address_id=address.address_id" +
            " JOIN address_book ON contact.address_book_id=address_book.address_book_id where contact.contact_id in" +
            " (Select contact_id from address where state=\"%s\" and city=\"%s\") and address_book_name=\"%s\";", state, city, addressBook);

        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count=resultSet.getInt("total");
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List < String > getSortedContactByName(String city) throws AddressBookException {

        String sql = String.format("SELECT * FROM contact JOIN address ON contact.address_id=address.address_id" +
            " where address.city=\"%s\" ORDER BY first_name ASC;", city);

        List < String > sortedContactList = new ArrayList < String > ();
        try (Connection connection = this.getConnection()) {
            if (city.isEmpty()) {
                throw new AddressBookException(ExceptionType.ENTERED_EMPTY, "Please enter valid city Name");
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                sortedContactList.add(firstName);
            }

            return sortedContactList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new AddressBookException(ExceptionType.ENTERED_NULL, "Please enter valid city Name");
        }

        return null;
    }

    public int countOfContactsInGivenType(String type) {
        int count = 0;
        String sql = String.format("SELECT count(contact_id) FROM address_book JOIN address_book_type" +
            " ON address_book.address_book_id=address_book_type.address_book_id JOIN contact" +
            " ON address_book.address_book_id=contact.address_book_id" +
            " where address_book_type.address_book_type=\"%s\";", type);
        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt("count(contact_id)");
            }
            return count;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public synchronized ContactDTO addContact(Contact givenContact, String addressBookName, LocalDate dateAdded) {
        int contactId = -1;
        Connection connection = null;
        ContactDTO contact = null;
        String firstName = givenContact.getFirstName();
        String lastName = givenContact.getLastName();
        String email = givenContact.getEmail();
        String phoneNumber = givenContact.getPhoneNumber();
        int addressBookID = this.getAddressBookID(addressBookName);
        String houseNumber = givenContact.getContactAddress().getHouseNumber();
        String street = givenContact.getContactAddress().getStreet();
        String city = givenContact.getContactAddress().getCity();
        String state = givenContact.getContactAddress().getState();
        String zip = givenContact.getContactAddress().getZip();

        if (checkContactExists(firstName, lastName, email, phoneNumber, addressBookID) == false) {

            try {
                connection = this.getConnection();
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            contactId = insertContactToDatabase(firstName, lastName, phoneNumber, email, addressBookID, contactId,
                connection, dateAdded);


            contact = insertAddressToDataBase(firstName, lastName, houseNumber, street, city, state, zip, phoneNumber,
                email, contactId, connection, contact);
            try {
                connection.commit();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("duplicate contact");
        }



        return contact;
    }

    private ContactDTO insertAddressToDataBase(String firstName, String lastName, String houseNumber, String street,
        String city, String state, String zip, String phoneNumber, String email, int contactId,
        Connection connection, ContactDTO contact) {
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("Insert into address values " +
                "('%d','%s','%s','%s','%s','%s');", contactId, houseNumber, street, city, state, zip);
            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) contactId = resultSet.getInt(1);
                Address address = new Address(houseNumber, street, city, state, zip);
                contact = new ContactDTO(contactId, firstName, lastName, address, phoneNumber, email);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return contact;
    }

    private int insertContactToDatabase(String firstName, String lastName, String phoneNumber, String email,
        int addressBookId, int contactId, Connection connection, LocalDate dateAdded) {
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("INSERT INTO contact(firstName,lastName,phoneNumber,email,address_book_id,date_added) " +
                "values ('%s','%s','%s','%s','%s','%s');", firstName, lastName, phoneNumber, email, addressBookId, dateAdded);
            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) contactId = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
        return contactId;
    }

    private int getAddressBookID(String addressBookName) {
        int id = 0;
        String sql = String.format("Select address_book_id  from address_book " +
            "where address_book_name=\"%s\";", addressBookName);
        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                id = resultSet.getInt("address_book_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

    private boolean checkContactExists(String firstName, String lastName, String email, String phoneNumber, int addressBookId) {
        String sql = String.format("select address_book_id from contact where " + "firstName ='%s' and lastName='%s' and email='%s' and phoneNumber='%s';",
            firstName, lastName, email, phoneNumber);

        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("address_book_id");
                if (id == addressBookId) {
                    return true;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    public void run() {
        try {

            this.addContact(this.contact, this.addressBookName, this.dateAdded);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}