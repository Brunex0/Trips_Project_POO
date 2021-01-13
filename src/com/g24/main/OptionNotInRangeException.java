package com.g24.main;

import java.io.Serial;

/**
 * Error thrown if an input is not in a specified range
 */
public final class OptionNotInRangeException extends Exception{
	@Serial
	private static final long serialVersionUID=401412043095958280L;
	
	/**
	 * The regular no message constructor
	 */
	public OptionNotInRangeException(){super();}
	/**
	 * The Regular message constructor
	 * @param message the error message to display
	 */
	public OptionNotInRangeException(String message){
		super(message);
	}
}