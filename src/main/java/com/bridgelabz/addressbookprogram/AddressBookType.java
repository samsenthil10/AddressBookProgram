package com.bridgelabz.addressbookprogram;

import java.util.List;

public class AddressBookType {
	
    private String addressBookType;
    private List < AddressBook > addressBookList;

    public AddressBookType(String addressBookType, List < AddressBook > addressBookList) {

        this.addressBookType = addressBookType;
        this.addressBookList = addressBookList;
    }

    public String getAddressBookType() {
        return addressBookType;
    }

    public void setAddressBookType(String addressBookType) {
        this.addressBookType = addressBookType;
    }

    public List < AddressBook > getAddressBookList() {
        return addressBookList;
    }

    public void setAddressBookList(List < AddressBook > addressBookList) {
        this.addressBookList = addressBookList;
    }
}