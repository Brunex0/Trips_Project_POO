package com.g24.main.input;

//imports
import java.util.Scanner;

/**
 * Handles user input from the standard input stream
 */
public abstract class Input{
	/**
	 * Asks a boolean from the standard input stream
	 * @return the given boolean
	 */
	public static boolean getBoolean(){
		String booleanToReturnString="";
		Scanner input=new Scanner(System.in);
		while(true){
			try{
				if(input.hasNextLine()){
					booleanToReturnString=input.nextLine().toLowerCase().trim();
				}
				if(!booleanToReturnString.equals("true")&&!booleanToReturnString.equals("false")){
					System.out.println("\033[1m\033[1mError\033[0m\033[0m: Value must be either 'true' or 'false'.");
					continue;
				}
				return Boolean.parseBoolean(booleanToReturnString);
			}
			catch(NumberFormatException err){
				System.out.println("\033[1mError\033[0m: Value must be either 'true' or 'false'.");
			}
		}
	}
	
	/**
	 * Asks a byte from the standard input stream
	 * @return the given byte
	 */
	public static byte getByte(){
		String byteToReturnString="";
		Scanner input=new Scanner(System.in);
		while(true){
			try{
				if(input.hasNextLine()){
					byteToReturnString=input.nextLine().trim();
				}
				return Byte.parseByte(byteToReturnString);
			}
			catch(NumberFormatException err){
				System.out.println("\033[1mError\033[0m: Value must be a byte.");
			}
		}
	}
	
	/**
	 * Asks a char from the standard input stream
	 * @return the given char
	 */
	public static char getChar(){
		String charToReturnString="";
		Scanner input=new Scanner(System.in);
		while(true){
			if(input.hasNextLine()){
				charToReturnString=input.nextLine().trim();
			}
			if(charToReturnString.length()!=1){
				System.out.println("\033[1mError\033[0m: Value must be a char.");
			}
			else{
				return charToReturnString.charAt(0);
			}
		}
	}
	
	/**
	 * Asks a short from the standard input stream
	 * @return the given short
	 */
	public static short getShort(){
		String shortToReturnString="";
		Scanner input=new Scanner(System.in);
		while(true){
			try{
				if(input.hasNextLine()){
					shortToReturnString=input.nextLine().trim();
				}
				return Short.parseShort(shortToReturnString);
			}
			catch(NumberFormatException err){
				System.out.println("\033[1mError\033[0m: Value must be a short.");
			}
		}
	}
	
	/**
	 * Asks an int from the standard input stream
	 * @return the given int
	 */
	public static int getInt(){
		String intToReturnString="";
		Scanner input=new Scanner(System.in);
		while(true){
			try{
				if(input.hasNextLine()){
					intToReturnString=input.nextLine().trim();
				}
				return Integer.parseInt(intToReturnString);
			}
			catch(NumberFormatException err){
				System.out.println("\033[1mError\033[0m: Value must be an int.");
			}
		}
	}
	
	/**
	 * Asks a long from the standard input stream
	 * @return the given long
	 */
	public static long getLong(){
		String longToReturnString="";
		Scanner input=new Scanner(System.in);
		while(true){
			try{
				if(input.hasNextLine()){
					longToReturnString=input.nextLine().trim();
				}
				return Long.parseLong(longToReturnString);
			}
			catch(NumberFormatException err){
				System.out.println("\033[1mError\033[0m: Value must be a long.");
			}
		}
	}
	
	/**
	 * Asks a float from the standard input stream
	 * @return the given float
	 */
	public static float getFloat(){
		String floatToReturnString="";
		Scanner input=new Scanner(System.in);
		while(true){
			try{
				if(input.hasNextLine()){
					floatToReturnString=input.nextLine().replace(',','.').trim();
				}
				return Float.parseFloat(floatToReturnString);
			}
			catch(NumberFormatException err){
				System.out.println("\033[1mError\033[0m: Value must be a float.");
			}
		}
	}
	
	/**
	 * Asks a double from the standard input stream
	 * @return the given double
	 */
	public static double getDouble(){
		String doubleToReturnString="";
		Scanner input=new Scanner(System.in);
		while(true){
			try{
				if(input.hasNextLine()){
					doubleToReturnString=input.nextLine().replace(',','.').trim();
				}
				return Double.parseDouble(doubleToReturnString);
			}
			catch(NumberFormatException err){
				System.out.println("\033[1mError\033[0m: Value must be a double.");
			}
		}
	}
	
	/**
	 * Asks a string from the standard input stream
	 * @return the given string
	 */
	public static String getString(){
		String stringToReturn="";
		Scanner input=new Scanner(System.in);
		if(input.hasNextLine()){
			stringToReturn=input.nextLine();
		}
		return stringToReturn;
	}
}