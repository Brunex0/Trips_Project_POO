package com.g24.main.user;

//imports
import java.util.ArrayList;
import java.io.*;
import com.g24.main.input.Input;
import com.g24.main.writeToFile.WriteToFile;

/**
 * Represents a user logged in
 */
public abstract class User implements Serializable{
	@Serial
	private static final long serialVersionUID=6132059763008398568L;
	//user account parameters
	private String username, password, name;
	
	//getters/setters
	/**
	 * getter for the username
	 * @return the username of the user
	 */
	public final String getUsername(){
		return username;
	}
	
	/**
	 * getter for the name
	 * @return the name of the user
	 */
	public final String getName(){
		return name;
	}
	
	/**
	 * Getter for the password
	 * @return password of the user
	 */
	protected final String getPassword(){
		return password;
	}
	
	/**
	 * Setter for name
	 * @param name the name of the user
	 */
	public final void setName(String name){
		this.name=name;
	}
	
	/**
	 * Setter for password
	 * @param password the password of the user
	 */
	public final void setPassword(String password){
		this.password=password;
	}
	
	/**
	 * Setter for username
	 * @param username the username of the user
	 */
	public final void setUsername(String username){
		this.username=username;
	}
	
	/**
	 * The account parameters of a user
	 * @return the user account's parameters
	 */
	@Override
	public String toString(){
		return "Username: "+username+", Name: "+name;
	}
	
	/**
	 * Updates a user's parameters and saves the users' list to the file
	 * @param registeredUsers the registered users' list
	 * @param user the user to update
	 */
	public static void updateUser(ArrayList<User>registeredUsers,User user){
		for(int i=0;i<registeredUsers.size();i++){
			if(registeredUsers.get(i).getUsername().equals(user.getUsername())){
				registeredUsers.set(i,user);
			}
		}
		WriteToFile.saveListToFile(new File("src/com/g24/main/user/users.dat"),registeredUsers);
	}
	
	/**
	 * Shows information about the user at position <code>id</code>
	 * @param users the registered users' list
	 * @param id the position of the user in the users' list
	 */
	public static void showCustomerInfo(ArrayList<User>users,int id){
		if((users.get(id-1).getClass().toString().split("\\."))[4].equals("Admin")){
			Admin admTemp=(Admin)(users.get(id-1));
			System.out.println(admTemp.toString());
		}
		else if((users.get(id-1).getClass().toString().split("\\."))[4].equals("Client")){
			Client clientTemp=(Client)(users.get(id-1));
			System.out.println(clientTemp.toString());
		}
		System.out.println("Press ok to continue");
		String choice;
		while(true){
			choice=Input.getString().toLowerCase().trim();
			if(choice.equals("ok")){
				break;
			}
			else{
				System.out.println("Invalid option!");
			}
		}
	}
	
	/**
	 * Shows the menu of the users' orders
	 * @param clients the registered users' list
	 * @param pg the page number
	 * @param lastPage the lastPage number
	 * @param ls the list size
	 */
	public static void inspectCustomers(ArrayList<User>clients,int pg, int lastPage, int ls){
		System.out.println("============== \033[1mUsers\033[0m ==============");
		System.out.println("Total users: "+clients.size());
		if(pg==lastPage){
			for(int i=1;i<=ls%5; i++){
				System.out.println(i+"-"+clients.get((i+5*(pg-1))-1).getUsername());
			}
		}
		else{
			for(int i=1; i<=5; i++){
				System.out.println(i+"-"+clients.get((i+5*(pg-1))-1).getUsername());
			}
		}
		System.out.println("0-Back");
		System.out.println("Select customer ('n'-next | 'p'-previous):");
	}
}