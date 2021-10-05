package com.bridgelabz.addressbookprogram;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class Address implements Serializable {

	private String houseNumber;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String address;

	public Address(String houseNumber, String street, String city, String state, String zip) {

		this.houseNumber = houseNumber;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;		
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(houseNumber, other.houseNumber)
				&& Objects.equals(state, other.state) && Objects.equals(street, other.street)
				&& Objects.equals(zip, other.zip);
	}

	@Override
	public String toString() {
		return "Address [houseNumber=" + houseNumber + ", street=" + street + ", city=" + city + ", state=" + state
				+ ", zip=" + zip + "]";
	}
}