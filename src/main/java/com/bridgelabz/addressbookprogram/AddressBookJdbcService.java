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


	public void printContact(String firstName, String lastName, String address, String city, String state, String zip, String phoneNumber, String email) {
		
		System.out.println("First Name : " + firstName);
		System.out.println("Last Name : " + lastName );
		System.out.println("Address : " + address );
		System.out.println("City : " + city );
		System.out.println("State : " +state );
		System.out.println("Pin Code : " + zip);
		System.out.println("Phone Number : " + phoneNumber);
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

	private void contactGrabber(ResultSet resultSet) throws SQLException {
		String firstName=resultSet.getString("firstName");
		String lastName=resultSet.getString("lastName");
		String houseNumber=resultSet.getString("house_number");
		String street=resultSet.getString("street");
		String city=resultSet.getString("city");
		String state=resultSet.getString("state");
		String zip=resultSet.getString("zip");
		String address=houseNumber+street+city+zip;
		String phoneNumber=resultSet.getString("phoneNumber");
		String email=resultSet.getString("email");
		printContact(firstName, lastName, address, city, state, zip, phoneNumber, email);
	}
	
	public void readContactListOfCity(String givenCity) 
	{
		String sql=String.format("SELECT * FROM contact JOIN address ON contact.address_id=address.address_id"+
				" where address.state=\"%s\";",givenCity);

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
		String sql=String.format("SELECT count(id) FROM contact JOIN address ON contact.address_id=address.address_id"+
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
				" where address.city=\"%s\" ORDER BY firstName ASC;",city);




		List<String > sortedContactList = new ArrayList<String>();
		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);


			while(resultSet.next())
			{
				String firstName=resultSet.getString("firstName");
				sortedContactList.add(firstName);
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
		String sql=String.format("SELECT count(id) FROM address_book JOIN address_book_type" + 
				" ON address_book.address_book_id=address_book_type.address_book_id JOIN contact"+
				" ON address_book.address_book_id=contact.address_book_id"+
				" where address_book_type.address_book_type=\"%s\";",type);
		try (Connection connection = this.getConnection())
		{

			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			while(resultSet.next())
			{
				count=resultSet.getInt("count(id)");
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

		String sql=String.format("select * from contact as c, address_book as a , address_book_type as att where att.address_book_type = \"%s\" and att.address_book_id=a.address_book_id and a.address_book_id=c.address_book_id",type);
		
		
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