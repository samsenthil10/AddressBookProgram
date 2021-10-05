package com.bridgelabz.addressbookprogram;

public class ContactDTO {
    private int contactId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Address address;

    public ContactDTO() {

    }

    public ContactDTO(int contactId, String firstName, String lastName, Address contactAddress, String phoneNumber, String email) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = contactAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }




    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }


    //set methods

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public Address getContactAddress() {
        return address;
    }
    public void setContactAddress(Address contactAddress) {
        this.address = contactAddress;
    }

    @Override
    public String toString() {
        return "ContactDTO [contactId=" + contactId + ", firstName=" + firstName + ", lastName=" + lastName +
            ", phoneNumber=" + phoneNumber + ", email=" + email + ", address=" + address + "]";
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(address, contactId, email, firstName, lastName, phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContactDTO other = (ContactDTO) obj;
        return java.util.Objects.equals(address, other.address) && contactId == other.contactId &&
            java.util.Objects.equals(email, other.email) && java.util.Objects.equals(firstName, other.firstName) &&
            java.util.Objects.equals(lastName, other.lastName) &&
            java.util.Objects.equals(phoneNumber, other.phoneNumber);
    }
}