package com.g24.main;

import java.io.Serial;

/**
 * Exception for wrong class name in the menu, i.e., it isn't an admin nor a client
 */
public class IllegalClassNameException extends Exception{
	@Serial
	private static final long serialVersionUID=5741229626542975320L;
	
	IllegalClassNameException(){super();}
	IllegalClassNameException(String message){
		super(message);
	}
}