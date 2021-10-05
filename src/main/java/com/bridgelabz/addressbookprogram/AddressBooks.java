package com.bridgelabz.addressbookprogram;

import java.util.LinkedList;
import java.util.List;

public class AddressBooks {

    private List < AddressBook > addressbookList;
    public AddressBooks() {
        addressbookList = new LinkedList < AddressBook > ();
    }

    public List < AddressBook > getAddressbookList() {
        return this.addressbookList;
    }

    public void setAddressbookList(List < AddressBook > addressbookList) {
        this.addressbookList = addressbookList;
    }
}