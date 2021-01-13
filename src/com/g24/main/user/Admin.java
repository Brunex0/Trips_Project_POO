package com.g24.main.user;

import java.io.Serial;

/**
 * Represents a logged in admin
 */
public class Admin extends User{
	@Serial
	private static final long serialVersionUID=293527488338697607L;
	
	/**
	 * Default contructor for Admin
	 * @param username the username of the client
	 * @param password the password of the client
	 * @param name the name of the client
	 */
	public Admin(String username, String password, String name){
		setUsername(username);
		setPassword(password);
		setName(name);
	}
	
	/**
	 * Displays the admin's info
	 * @return the admin's info
	 */
	@Override
	public String toString(){
		return "============== \033[1m"+getUsername()+"\033[0m ==============\n" +
			"Name: "+getName();
	}
}