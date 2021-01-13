package com.g24.main;

//imports
import com.g24.main.input.Input;

/**
 * Handles the option of the menu
 */
public abstract class Option{
	/**
	 * Handles handles the choosing of the option
	 * @param minOption the min value of the option
	 * @param maxOption the max value of the option
	 * @return the option
	 * @throws OptionNotInRangeException if the value is not in [{@code minOption},{@code maxOption}]
	 * @throws NumberFormatException if the value is not a number
	 */
	public static int getOption(int minOption, int maxOption) throws OptionNotInRangeException,NumberFormatException{
		System.out.print("-> ");
		int option=Input.getInt();
		
		//check if option is in correct range
		if(option>=minOption&&option<=maxOption){
			return option;
		}
		else{
			throw new OptionNotInRangeException("Option must be a number ["+minOption+"-"+maxOption+"]");
		}
	}
}