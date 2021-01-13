package com.g24.main;

//imports
import java.io.*;
import java.util.*;
import com.g24.main.input.Input;
import com.g24.main.statistic.Statistic;
import com.g24.main.user.*;
import com.g24.main.product.*;
import com.g24.main.writeToFile.WriteToFile;

/**
 * Shows the menu depending on the type of user
 */
public final class Menu{
	private Menu(){
		throw new IllegalStateException("Cannot instanciate Menu!");
	}
	
	/**
	 * Displays the initial app menu
	 */
	public static void initialMenu(){
		System.out.println("============== \033[1mWelcome to the G24 trip agency!\033[0m ==============");
		System.out.println("\033[3mWhat do you want to do?\033[0m");
		System.out.println("1-Go to the app");
		System.out.println("2-Manage administration");
		System.out.println("0-Exit");
		System.out.println("=============================================================");
	}
	/**
	 * Prints the menu for the non logged in users
	 */
	public static void printNonLoggedInMenu(){
		System.out.println("============== \033[1mMenu\033[0m ==============");
		System.out.println("->\033[3mNot Logged In\033[0m<-");
		System.out.println("1-Login");
		System.out.println("2-Register");
		System.out.println("3-View/Buy Trips");
		System.out.println("4-Shopping Cart");
		System.out.println("0-Exit");
		System.out.println("==================================");
	}
	
	/**
	 * Checks if the user is an admin or a client and calls the
	 * correct functions to print the menu based on the type of user
	 * @param user the user
	 * @throws IllegalClassNameException if the class name is not an admin or a client
	 * @return the user type
	 */
	public static String printMenu(User user) throws IllegalClassNameException{
		String userType=(user.getClass().toString().split("\\."))[4];
		if(userType.equals("Admin")){
			printAdminMenu(user);
			return userType;
		}
		else if(userType.equals("Client")){
			printClientMenu(user);
			return userType;
		}
		else{
			throw new IllegalClassNameException("Error: Must be a defined type of user!");
		}
	}
	
	/**
	 * Prints the client's menu
	 * @param user the logged in user
	 */
	private static void printClientMenu(User user){
		System.out.println("============== \033[1mMenu\033[0m ==============");
		System.out.println("\033[3mHey "+user.getName()+"! What do you want to do?\033[0m");
		System.out.println("1-View/Buy Trips");
		System.out.println("2-Shopping Cart");
		System.out.println("3-Wishlist");
		System.out.println("4-Your orders");
		System.out.println("5-Settings");
		System.out.println("6-Logout");
		System.out.println("0-Exit");
		System.out.println("==================================");
	}
	
	/**
	 * Print's the admin's menu
	 * @param user the logged in user
	 */
	private static void printAdminMenu(User user){
		System.out.println("============== \033[1mMenu\033[0m ==============");
		System.out.println("\033[3mHey "+user.getName()+"! What do you want to do?\033[0m");
		System.out.println("1-Add Trip");
		System.out.println("2-View/Manage Trips");
		System.out.println("3-Check Users");
		System.out.println("4-View Statistics");
		System.out.println("5-Settings");
		System.out.println("6-Logout");
		System.out.println("0-Exit");
		System.out.println("==================================");
	}
	
	/**
	 * Prints the productList menu
	 * @param list the productList
	 * @param pageNumber the current page's number
	 */
	public static void printProductsListMenu(ArrayList<Product> list, int pageNumber){
		System.out.println("========================= \033[1mTrip  List\033[0m =========================");
		int count=0;
		if(pageNumber==1){
			if(list.size()>5){
				System.out.println("1-Show me the next 5");
				for(int i=0;i<5;i++,count++){
					try{
						System.out.println((count+2)+"-"+list.get(i).getDesignation());
					}
					catch(IndexOutOfBoundsException ignored){}
				}
			}
			else{
				for(int i=0;i<5;i++,count++){
					try{
						System.out.println((count+1)+"-"+list.get(i).getDesignation());
					}
					catch(IndexOutOfBoundsException ignored){}
				}
			}
			
		}
		else if((list.size()%5==0&&pageNumber==list.size()/5)||(list.size()%5!=0&&pageNumber==(list.size()/5)+1)){
			System.out.println("1-Show me the previous 5");
			for(int i=pageNumber*5-5;i<pageNumber*5;i++,count++){
				try{
					System.out.println((count+2)+"-"+list.get(i).getDesignation());
				}
				catch(IndexOutOfBoundsException ignored){}
			}
		}
		else{
			System.out.println("1-Show me the next 5");
			System.out.println("2-Show me the previous 5");
			for(int i=pageNumber*5-5;i<pageNumber*5;i++,count++){
				try{
					System.out.println((count+3)+"-"+list.get(i).getDesignation());
				}
				catch(IndexOutOfBoundsException ignored){}
			}
		}
		System.out.println("0-Go back");
		System.out.println("==============================================================");
	}
	
	/**
	 * Prints a specific product's menu for an admin or user
	 * @param productList the productList
	 * @param index the index of the specified product
	 * @param loggedOutShoppingCart the loggedOutShoppingCart list
	 * @param isAdmin whether the logged user is admin or not
	 */
	public static void productMenu(ArrayList<Product>productList,int index,ArrayList<Product>loggedOutShoppingCart,boolean isAdmin){
		int option;
		boolean back=false;
		while(!back){
			if(!isAdmin){
				try{
					System.out.println("=============================================================");
					System.out.println(productList.get(index-2).toString());
					System.out.println("=============================================================");
					System.out.println("1-Add trip to shopping cart");
					System.out.println("0-Exit");
					option=Input.getInt();
					switch(option){
						case 1:
							loggedOutShoppingCart.add(productList.get(index-2));
							System.out.println("Trip added to shopping cart!");
							back=true;
							break;
						case 0:
							back=true;
							break;
						default:
							throw new OptionNotInRangeException();
					}
				}
				catch(OptionNotInRangeException err){
					System.out.println("Invalid input");
				}
			}
			else{
				try {
					System.out.println("\n\n=============================================================");
					System.out.println(productList.get(index-2).toString());
					System.out.println("=============================================================");
					System.out.println("1: Modify product");
					System.out.println("2: Remove product");
					System.out.println("0: Exit");
					int option2=Input.getInt();
					switch(option2) {
						case 1:
							Product.modifyTrip(productList, index-2);
							back=true;
							break;
						case 2:
							productList.remove(index-2);
							//Writes the arraylist in the file
							try{
								ObjectOutputStream is = new ObjectOutputStream(new FileOutputStream("src/com/g24/main/product/products.dat"));
								is.writeObject(productList);
								is.flush();
							}
							catch (IOException e){
								System.out.println("File not found");
							}
							System.out.println("Removed with success!");
							back=true;
							break;
						case 0:
							back=true;
							break;
						default:
							throw new OptionNotInRangeException();
					}
				}
				catch (OptionNotInRangeException e) {
					System.out.println("Invalid input");
				}
			}
		}
	}
	
	/**
	 * Pritns a specific product's menu
	 * @param list the productList
	 * @param index the index of the specified product
	 * @param currentUser the loggedIn user
	 */
	public static void productMenu(ArrayList<Product>list,int index,Client currentUser){
		int option;
		boolean back=false;
		while(!back){
			try{
				System.out.println("=============================================================");
				System.out.println(list.get(index-2).toString());
				System.out.println("=============================================================");
				System.out.println("1-Add trip to shopping cart");
				System.out.println("2-Add trip to wishlist");
				System.out.println("0-Exit");
				option=Input.getInt();
				switch(option){
					case 1:
						currentUser.addToShoppingCart(list.get(index-2));
						break;
					case 2:
						currentUser.addToWishlist(list.get(index-2));
						break;
					case 0:
						back=true;
						break;
					default:
						throw new OptionNotInRangeException();
				}
			}
			catch(OptionNotInRangeException err){
				System.out.println("Invalid input");
			}
		}
	}
	
	/**
	 * Prints the checkout menu
	 */
	public static void printCheckoutMenu(){
		System.out.println("============== \033[1mDo you wish to confirm the payment?\033[0m ==============");
		System.out.println("1-Yes");
		System.out.println("2-Reset shopping cart");
		System.out.println("3-No (take me back)");
		System.out.println("==================================");
	}
	
	/**
	 * Prints the wishlist menu
	 */
	public static void printWishlistMenu(){
		System.out.println("============== \033[1mDo you wish to add the wishlist to the shopping cart?\033[0m ==============");
		System.out.println("1-Yes");
		System.out.println("2-Reset wishlist");
		System.out.println("3-No (take me back)");
		System.out.println("==================================");
	}
	
	/**
	 * Prints the membership menu
	 */
	public static void printMembershipMenu(){
		System.out.println("============== \033[1mDo you wish to buy the membership?\033[0m ==============");
		System.out.println("1-Yes");
		System.out.println("2-No (take me back)");
		System.out.println("==================================");
	}
	
	/**
	 * Prints the settings menu for a client
	 */
	public static void printSettingsMenu(){
		System.out.println("============== \033[1mSettings\033[0m ==============");
		System.out.println("1-Change Password");
		System.out.println("2-Change Name");
		System.out.println("3-Change Tax Number");
		System.out.println("4-Get VIP membership");
		System.out.println("0-Exit");
		System.out.println("==================================");
	}
	
	/**
	 * Prints the settings menu for an admin
	 */
	public static void printSettingsMenuAdmin(){
		System.out.println("============== \033[1mSettings\033[0m ==============");
		System.out.println("1-Change Password");
		System.out.println("2-Change Name");
		System.out.println("0-Exit");
		System.out.println("==================================");
	}
	
	/**
	 * Prints and handles the menu for adding trips (admin only)
	 * @param productList the productList
	 * @return true if a trip was added and false otherwise
	 */
	public static boolean addTripMenu(ArrayList<Product> productList){
		while(true){
			System.out.println("===================== \033[1mAdd Trip\033[0m ============================");
			System.out.println("1-Add national trip");
			System.out.println("2-Add international trip");
			System.out.println("0-Go back");
			System.out.println("===========================================================");
			System.out.print("-> ");
			int i=Input.getInt();
			try{
				if(i==1){
					Product.addProduct(productList,true);
					return true;
				}
				else{
					if(i==2){
						Product.addProduct(productList,false);
						return true;
					}
					else{
						if(i==0){
							return false;
						}
						else{
							throw new OptionNotInRangeException();
						}
					}
				}
			}
			catch(OptionNotInRangeException ignored){
				System.out.println(i+" is not a valid input");
			}
		}
	}
	
	/**
	 * Displays the first statistic's menu
	 * @param staticticList the list of statistics
	 * @param productslist the productList
	 * @param Clientlist the clientList
	 */
	public static void firstStatisticMenu(ArrayList<Statistic> staticticList,ArrayList<Product> productslist,ArrayList<User> Clientlist){
		boolean o=true;
		int i;
		while(o){
			Menu.printStatisticMenu1();
			System.out.print("-> ");
			i=Input.getInt();
			try{
				switch(i){
					case 1:
						Statistic stat=new Statistic(Clientlist,productslist);
						staticticList.add(stat);
						WriteToFile.saveListToFile(new File("src/com/g24/main/statistic/statistic.dat"),staticticList);
						secondStatisticMenu(stat, Clientlist);
						break;
					case 2:
						System.out.println(staticticList);
						break;
					case 0:
						o=false;
						break;
					default:
						throw new OptionNotInRangeException();
				}
			}
			catch(OptionNotInRangeException err){
				System.out.println(err.getMessage());
			}
		}
	}
	
	/**
	 * Prints a second statistic's menu
	 */
	public static void printStatisticMenu1(){
		System.out.println("============== \033[1mStatistics Menu\033[0m ==============");
		System.out.println("1-View current statistic");
		System.out.println("2-View historical statistics");
		System.out.println("0-Back");
		System.out.println("=============================================");
	}
	
	/**
	 * prints a third statistic's menu
	 */
	public static void printStatisticMenu2(){
		System.out.println("============== \033[1mCurrent Statistic\033[0m ==============");
		System.out.println("1-Profit per Trip non VIP costumers");
		System.out.println("2-Profit per Trip VIP costumers");
		System.out.println("3-Average profit");
		System.out.println("4-Average nacional trips");
		System.out.println("5-Average internacional trips");
		System.out.println("6-Best selling trip");
		System.out.println("7-Most expensive trip");
		System.out.println("8-Less expensive trip");
		System.out.println("9-Client with more trips");
		System.out.println("0-Back");
		System.out.println("==============================================");
	}
	
	/**
	 * Prints the second statistic's menu
	 * @param s the specifed statistic object to present
	 */
	public static void secondStatisticMenu(Statistic s,ArrayList<User> Clientlist){
		boolean o=true;
		int option;
		while(o){
			Menu.printStatisticMenu2();
			System.out.print("-> ");
			option=Input.getInt();
			try{
				switch(option){
					case 1:
						System.out.println(s.getProfitPerTripNonVIP());
						break;
					case 2:
						System.out.println(s.getProfitPerTripVIP());
						break;
					case 3:
						System.out.println(s.getAVGProfit()+"€");
						break;
					case 4:
						System.out.println(s.getAVGNacTrips()+"%");
						break;
					case 5:
						System.out.println(s.getAVGIntTrips()+"%");
						break;
					case 6:
						System.out.println(s.getBestSeller());
						break;
					case 7:
						System.out.println(s.getMostExpensTrip());
						break;
					case 8:
						System.out.println(s.getLessExpensTrip());
						break;
					case 9:
						Statistic.nomeCliente(Clientlist);
					case 0:
						o=false;
						break;
					default:
						throw new OptionNotInRangeException();
				}
			}
			catch(OptionNotInRangeException err){
				System.out.println(err.getMessage());
			}
		}
	}
}