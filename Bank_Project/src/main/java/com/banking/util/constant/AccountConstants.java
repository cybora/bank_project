package com.banking.util.constant;

public class AccountConstants {
	
	public static final String ID_NOT_FOUND = "No customer with id ";
	
	public static final String USER_HAS_SECONDARY_ACCOUNT = 
						"The user has already a secondary account";
	
	public static final String NO_PRIMARY_ACCOUNT = 
			"The user does not have a primary account";
	
	public static final String INSUFFICIENT_CREDIT = 
			"The primary account does not have sufficient credit";

	
		
	private AccountConstants() {
		throw new IllegalStateException("Constants class");
	}

}
