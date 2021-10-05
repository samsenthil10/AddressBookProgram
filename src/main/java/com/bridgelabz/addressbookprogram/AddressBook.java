package com.bridgelabz.addressbookprogram;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AddressBook {
	
    private String AddressBookName;
    private List < Contact > contactList = new LinkedList < Contact > ();
    private AddressBookType addressBookType;
    private transient HashMap < String, List < Contact >> cityDictionary = new HashMap < String, List < Contact >> ();
    private transient HashMap < String, List < Contact >> stateDictionary = new HashMap < String, List < Contact >> ();


    public HashMap < String, List < Contact >> getCityDictionary() {
        return cityDictionary;
    }

    public void setCityDictionary(HashMap < String, List < Contact >> cityDictionary) {
        this.cityDictionary = cityDictionary;
    }

    public HashMap < String, List < Contact >> getStateDictionary() {
        return stateDictionary;
    }

    public void setStateDictionary(HashMap < String, List < Contact >> stateDictionary) {
        this.stateDictionary = stateDictionary;
    }


    public String getAddressBookName() {
        return AddressBookName;

    }



    public void setAddressBookName(String AddressBookName) {
        this.AddressBookName = AddressBookName;

    }
    public List < Contact > getContactList() {
        return contactList;
    }

    public void setContactList(List < Contact > newlist) {
        this.contactList = newlist;
    }

    public AddressBookType getAddressBookType() {
        return addressBookType;
    }

    public void setAddressBookType(AddressBookType addressBookType) {
        this.addressBookType = addressBookType;
    }
}