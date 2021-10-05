package com.bridgelabz.addressbookprogram;

@SuppressWarnings("serial")
public class AddressBookException extends RuntimeException {
	
	 public enum ExceptionType {
		 
		ENTERED_NULL, ENTERED_EMPTY
	}

	public ExceptionType type;

	public AddressBookException(ExceptionType type, String message) {

		super(message);
		this.type = type;
	}
}
