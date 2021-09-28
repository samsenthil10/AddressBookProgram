package com.bridgelabz.addressbookprogram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

	public void readContactList(String addressbookName) {
		
		if(this.addressBookReadStatement==null) {
			this.preparedStatementForContactData();
		}
		
		try {
			addressBookReadStatement.setString(1, addressbookName);
			ResultSet resultSet=addressBookReadStatement.executeQuery();
			while(resultSet.next())
			{
				contactGrabber(resultSet);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
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

	public void countOfContactsInGivenStateCity(String city, String state, String addressBook) 
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

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
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

	public void countOfContactsInGivenType(String type) 
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
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
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
}