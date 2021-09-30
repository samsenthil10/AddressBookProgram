package com.bridgelabz.addressbookprogram;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class AddressBookJdbcService { 

	private static AddressBookJdbcService addressBookJdbcService;
	private PreparedStatement addressBookReadStatement;

	public AddressBookJdbcService() {

	}

	public static AddressBookJdbcService getInstance() {

		if(addressBookJdbcService==null) {
			addressBookJdbcService=new AddressBookJdbcService();
		}
		return  addressBookJdbcService;
	}


	private void addContact(Contacts contact, String first_name, String last_name, String street,String city, String state, String zip, String phoneNumber, String email) {

		contact.setPhoneNumber(phoneNumber);
		contact.setFirstName(first_name);
		contact.setLastName(last_name);
		contact.setAddress(street);
		contact.setCity(city);
		contact.setState(state);
		contact.setZip(zip);
		contact.setEmail(email);
	}

	public void printContact(String first_name, String last_name, String city, String state, String zip, String phone_number, String email) {

		System.out.println("First Name : " + first_name);
		System.out.println("Last Name : " + last_name );
		System.out.println("City : " + city );
		System.out.println("State : " +state );
		System.out.println("Pin Code : " + zip);
		System.out.println("Phone Number : " + phone_number);
		System.out.println("Email : " + email );
		System.out.println();

	}

	private Connection getConnection() throws SQLException 
	{
		String jdbcURL ="jdbc:mysql://localhost:3306/address_book_service?allowPublicKeyRetrieval=true&useSSL=false";
		String username="root";
		String password="root@123";
		Connection connection = null;
		System.out.println("Connecting to database"+jdbcURL);
		connection=DriverManager.getConnection(jdbcURL,username,password);
		System.out.println("Connection is successfull!!"+connection);
		return connection;
	}

	public LinkedHashSet<Contacts> readContactList(String addressbookName) {

		if(this.addressBookReadStatement==null) {
			this.preparedStatementForContactData();
		}

		try {
			addressBookReadStatement.setString(1, addressbookName);
			ResultSet resultSet=addressBookReadStatement.executeQuery();
			LinkedHashSet<Contacts> contacts = new LinkedHashSet<Contacts>();
			while(resultSet.next())
			{
				contacts.add(contactAdder(resultSet));
				contactGrabber(resultSet);
			}
			return contacts;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void preparedStatementForContactData() {

		try {
			Connection connection = this.getConnection();
			String sql="SELECT contact.first_name,contact.last_name,address.house_number,address.street,address.city,address.state,address.zip,contact.phone_number,contact.email,address_book.address_book_name,address_book_type.address_book_type FROM contact JOIN address   ON contact.address_id=address.address_id JOIN address_book ON contact.address_book_id=address_book.address_book_id JOIN address_book_type ON address_book.address_book_id=address_book_type.address_book_id"
					+ " where address_book_name=?;";
			addressBookReadStatement=connection.prepareStatement(sql);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Contacts contactAdder(ResultSet resultSet) throws SQLException {

		String first_name=resultSet.getString("first_name");
		String last_name=resultSet.getString("last_name");
		String street = resultSet.getString("street");
		String city=resultSet.getString("city");
		String state=resultSet.getString("state");
		String zip=resultSet.getString("zip");
		String phone_number=resultSet.getString("phone_number");
		String email=resultSet.getString("email");
		Contacts contact = new Contacts();
		addContact(contact, first_name, last_name, street, city, state, zip, phone_number, email);
		return contact;

	}


	private void contactGrabber(ResultSet resultSet) throws SQLException {

		String first_name=resultSet.getString("first_name");
		String last_name=resultSet.getString("last_name");
		String city=resultSet.getString("city");
		String state=resultSet.getString("state");
		String zip=resultSet.getString("zip");
		String phone_number=resultSet.getString("phone_number");
		String email=resultSet.getString("email");
		printContact(first_name, last_name, city, state, zip, phone_number, email);
	}

	public void readContactListOfCity(String givenCity) 
	{
		String sql=String.format("SELECT * FROM contact JOIN address ON contact.address_id=address.address_id"+
				" where address.city=\"%s\";",givenCity);

		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);


			while(resultSet.next())
			{
				contactGrabber(resultSet);
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public void readContactListOfState(String givenState) 
	{
		String sql=String.format("SELECT * FROM contact JOIN address ON contact.address_id=address.address_id"+
				" where address.state=\"%s\";",givenState);

		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);


			while(resultSet.next())
			{
				contactGrabber(resultSet);
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public int countOfContactsInGivenStateCity(String city, String state, String addressBook) 
	{

		int count=0;
		String sql=String.format("SELECT count(contact_id) FROM contact JOIN address ON contact.address_id=address.address_id"+
				" JOIN address_book ON contact.address_book_id=address_book.address_book_id where contact.address_id in"+
				" (Select address_id from address where state=\"%s\" and city=\"%s\") and address_book_name=\"%s\";",state,city,addressBook);


		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			while(resultSet.next())
			{
				count++;
			}
			System.out.println(count);
			return count;

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return 0;
	}

	public void getSortedContactByName(String city) 
	{

		String sql=String.format("SELECT * FROM contact JOIN address ON contact.address_id=address.address_id"+
				" where address.city=\"%s\" ORDER BY first_name ASC;",city);

		List<String > sortedContactList = new ArrayList<String>();
		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);


			while(resultSet.next())
			{
				String first_name=resultSet.getString("first_name");
				sortedContactList.add(first_name);
			}

			sortedContactList.forEach(value -> {System.out.println(value);});
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}

	public int countOfContactsInGivenType(String type) 
	{
		int count=0;
		String sql=String.format("SELECT count(contact_id) FROM address_book JOIN address_book_type" + 
				" ON address_book.address_book_id=address_book_type.address_book_id JOIN contact"+
				" ON address_book.address_book_id=contact.address_book_id"+
				" where address_book_type.address_book_type=\"%s\";",type);
		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			while(resultSet.next())
			{
				count=resultSet.getInt("count(contact_id)");
			}
			System.out.println(count);
			return count;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return 0;
	}

	public void readContactListByType(String type) 

	{

		String sql=String.format("SELECT contact.first_name,contact.last_name,address.house_number,address.street,address.city,address.state,address.zip,contact.phone_number,contact.email,address_book.address_book_name,address_book_type.address_book_type FROM contact JOIN address   ON contact.address_id=address.address_id JOIN address_book ON contact.address_book_id=address_book.address_book_id JOIN address_book_type ON address_book.address_book_id=address_book_type.address_book_id where address_book_type.address_book_type=\"%s\"",type);

		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);


			while(resultSet.next())
			{
				contactGrabber(resultSet);
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}

	public void updateContactByName(String name, String newName) {

		String sql=String.format("UPDATE contact SET first_name = '%s' where first_name = '%s';",newName,name);

		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			@SuppressWarnings("unused")
			int result=statement.executeUpdate(sql);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public boolean checkAddressBookInSyncWithDB(String name)
	{
		LinkedHashSet<Contacts> contacts= readContactList(name);
		return contacts.equals(this.readContactList(name));
	}

	public int retrieveRecordBetweenDates(String startDate, String endDate) {

		int count=0;
		String sql = String.format("SELECT * FROM contact WHERE date_added BETWEEN '%s' AND '%s';", Date.valueOf(startDate),
				Date.valueOf(endDate));
		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				count++;
			}
			return count;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		return 0;
	}

	public int addAddressToAddressBook(String first_name, String last_name, String phone_number, String email, int address_id,int address_book_id) throws SQLException {

		int count=0;
		String sql = String.format(
				"INSERT INTO contact(first_name,last_name,phone_number,email,address_id,address_book_id) VALUES ('%s','%s','%s','%s', %d, %d);",first_name, last_name, phone_number, email,address_id,address_book_id);
		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			@SuppressWarnings("unused")
			int result=statement.executeUpdate(sql);
			sql = "SELECT * FROM contact";
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				count++;
			}
			return count;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return 0;
	}

	public void deleteContactByPhoneNumber(String phoneNumber) {

		String sql=String.format("DELETE from contact where phone_number = '%s';",phoneNumber);

		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			@SuppressWarnings("unused")
			int result=statement.executeUpdate(sql);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}