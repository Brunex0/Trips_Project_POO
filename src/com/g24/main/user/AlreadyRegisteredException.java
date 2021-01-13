package com.g24.main.user;

import java.io.Serial;

/**
 * Exception for already registered users
 */
public class AlreadyRegisteredException extends Exception{
	@Serial
	private static final long serialVersionUID=-5436996980258192242L;
	
	AlreadyRegisteredException(){super();}
	AlreadyRegisteredException(String message){
		super(message);
	}
}
