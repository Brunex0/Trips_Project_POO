package com.g24.main.user;

//imports
import java.util.ArrayList;
import com.g24.main.input.Input;
import com.g24.main.product.Product;

/**
 * Handles the login of a user
 */
public class Login{
	/**
	 * Logins a new user
	 * @param users the registered users' list
	 * @param shoppingCart the user's shopping cart
	 * @return the logged in user
	 */
	public static User login(ArrayList<User>users,ArrayList<Product>shoppingCart){
		//account parameters
		String username, password;
		
		//menu
		System.out.println("============== \033[1mLogin\033[0m ==============");
		
		//get username
		System.out.println("Username:");
		username=Input.getString();
		
		//get password
		System.out.println("Password:");
		password=Input.getString();
		
		//check username and password
		for(User user:users){
			if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
				if((user.getClass().toString().split("\\."))[4].equals("Client")){
					return new Client(user.getUsername(),user.getPassword(),user.getName(),((Client)user).getIsVIP(),((Client)user).getTaxNumber(),((Client)user).getShoppingCart(),((Client)user).getWishlist(),((Client)user).getOrders());
				}
				else{
					return new Admin(user.getUsername(),user.getPassword(),user.getName());
				}
			}
		}
		System.out.println("Wrong username or password!");
		return null;
	}
}
