package com.g24.main.user;

//imports
import java.io.*;
import java.util.*;
import java.util.regex.*;
import com.g24.main.OptionNotInRangeException;
import com.g24.main.input.Input;
import com.g24.main.writeToFile.WriteToFile;

/**
 * Handles the registration of a new user
 */
public class Register{
	private static final String password="adminG24_oop";
	/**
	 * Registers a new user
	 * @param users an array that contains all users
	 */
	public static void register(ArrayList<User>users){
		//account parameters
		String username, password,name;
		int taxNumber;
		
		//menu
		System.out.println("============== \033[1mRegister\033[0m ==============");
		
		//get username
		while(true){
			try{
				System.out.println("Username:");
				username=getUsername(users);
				break;
			}
			catch(AlreadyRegisteredException err){
				System.out.println(err.getMessage());
			}
		}
		
		//get password
		Pattern p=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*^_&+=]).{8,}$");
		Matcher m;
		while(true){
			System.out.println("Password:");
			password=Input.getString();
			m=p.matcher(password);
			if(m.find()){
				break;
			}
			else{
				System.out.println("Password too weak! The password must be, at least, 8 characters long and "+
					"contain one lower and upper case character, a digit, and a special character.");
			}
		}
		
		//get name
		do{
			System.out.println("Name:");
			name=Input.getString();
		}
		while(name.length()>30||name.trim().isEmpty());
		
		//get tax number
		while(true){
			try{
				System.out.println("Tax Number:");
				taxNumber=getTaxNumber(users);
				break;
			}
			catch(AlreadyRegisteredException|OptionNotInRangeException err){
				System.out.println(err.getMessage());
			}
		}
		
		//add user to registration
		users.add(new Client(username,password,name,false,taxNumber,null,null,null));
		
		//save to file
		WriteToFile.saveListToFile(new File("src/com/g24/main/user/users.dat"),users);
	}
	
	/**
	 * Registers a new admin
	 * @param users the registered users' list
	 * @return true if the registration was successful and false if the password to access the administrator menu was wrong
	 */
	public static boolean registerAdmin(ArrayList<User>users){
		System.out.println("Password");
		String passwordTemp=Input.getString();
		if(!passwordTemp.equals(password)){
			System.out.println("Wrong password!");
			return false;
		}
		else{
			System.out.println("\033[1mRegister new admin\033[0m");
			//get username
			String usernameAdmin;
			while(true){
				try{
					System.out.println("Username:");
					usernameAdmin=getUsername(users);
					break;
				}
				catch(AlreadyRegisteredException err){
					System.out.println(err.getMessage());
				}
			}
			
			//get password
			Pattern p=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*^_&+=]).{8,}$");
			Matcher m;
			String passwordAdmin;
			while(true){
				System.out.println("Password:");
				passwordAdmin=Input.getString();
				m=p.matcher(passwordAdmin);
				if(m.find()){
					break;
				}
				else{
					System.out.println("Password too weak! The password must be, at least, 8 characters long and "+
						"contain one lower and upper case character, a digit, and a special character.");
				}
			}
			
			//get name
			String nameAdmin;
			do{
				System.out.println("Name:");
				nameAdmin=Input.getString();
			}
			while(nameAdmin.length()>30||nameAdmin.trim().isEmpty());
			
			//add user to registration
			users.add(new Admin(usernameAdmin,passwordAdmin,nameAdmin));
			
			//save to file
			WriteToFile.saveListToFile(new File("src/com/g24/main/user/users.dat"),users);
			return true;
		}
	}
	
	private static boolean isAlreadyUser(ArrayList<User>users,String username){
		for(User user:users){
			if(user.getUsername().equals(username)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isAlreadyTaxNumber(ArrayList<User>users,int taxNumber){
		for(User user:users){
			if(!(user.getClass().toString().split("\\."))[4].equals("Admin")){
				if(((Client)(user)).getTaxNumber()==taxNumber){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Gets a username
	 * @param users the registered users' list
	 * @return the username
	 * @throws AlreadyRegisteredException if the username is already taken
	 */
	public static String getUsername(ArrayList<User>users) throws AlreadyRegisteredException{
		String usernameTemp=Input.getString();
		if(isAlreadyUser(users,usernameTemp)){
			throw new AlreadyRegisteredException("The username is already taken!");
		}
		else{
			return usernameTemp;
		}
	}
	
	/**
	 * Gets the taxNumber
	 * @param users the registered users' list
	 * @return the taxNumber
	 * @throws AlreadyRegisteredException if the taxNumber is already taken
	 * @throws OptionNotInRangeException if the taxNumber has an invalid size
	 */
	public static int getTaxNumber(ArrayList<User>users) throws AlreadyRegisteredException, OptionNotInRangeException{
		int taxNumberTemp=Input.getInt();
		
		if(isAlreadyTaxNumber(users,taxNumberTemp)){
			throw new AlreadyRegisteredException("The tax number is already taken!");
		}
		else if(String.valueOf(taxNumberTemp).length()!=9){
			throw new OptionNotInRangeException("The tax number must have 9 digits!");
		}
		else{
			return taxNumberTemp;
		}
	}
}