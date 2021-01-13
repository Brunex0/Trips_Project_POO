package com.g24.main.user;

//imports
import java.io.Serial;
import java.util.*;
import com.g24.main.product.*;

/**
 * Represents a logged in client
 */
public class Client extends User{
	@Serial
	private static final long serialVersionUID=6377287761611260459L;
	private boolean isVIP;
	private int taxNumber;
	private final ArrayList<Product>wishlist;
	private final ArrayList<Product>shoppingCart;
	private final ArrayList<Order>orders;
	/**
	 * Default contructor for Client
	 * @param username the username of the client
	 * @param password the password of the client
	 * @param name the name of the client
	 * @param isVIP is the user VIP
	 * @param taxNumber the taxNumber of the client
	 * @param shoppingCart the shopping cart of the client
	 * @param wishlist the wishlist for the client
	 * @param orders the orders of the client
	 */
	public Client(String username, String password, String name, boolean isVIP, int taxNumber,ArrayList<Product>shoppingCart,ArrayList<Product>wishlist,ArrayList<Order>orders){
		setUsername(username);
		setPassword(password);
		setName(name);
		setVIP(isVIP);
		setTaxNumber(taxNumber);
		this.wishlist=new ArrayList<>();
		this.shoppingCart=new ArrayList<>();
		if(shoppingCart!=null){
			this.shoppingCart.addAll(shoppingCart);
		}
		if(wishlist!=null){
			this.wishlist.addAll(wishlist);
		}
		this.orders=new ArrayList<>();
		if(orders!=null){
			this.orders.addAll(orders);
		}
	}
	
	//getters
	/**
	 * Getter for boolean isVIP
	 * @return true if the user is a VIP and false otherwise
	 */
	public final boolean getIsVIP(){
		return isVIP;
	}
	/**
	 * Getter for taxNumber
	 * @return the tax number of the user
	 */
	public final int getTaxNumber(){
		return taxNumber;
	}
	/**
	 * Getter for the wishlist
	 * @return the user's wishlist
	 */
	public final ArrayList<Product> getWishlist(){
		return wishlist;
	}
	
	/**
	 * Getter for the shopping cart
	 * @return the user's shopping cart
	 */
	public final ArrayList<Product> getShoppingCart(){
		return shoppingCart;
	}
	
	/**
	 * Getter for the orders
	 * @return the user's orders
	 */
	public final ArrayList<Order> getOrders(){
		return orders;
	}
	
	//setters
	/**
	 * Setter for taxNumber
	 * @param taxNumber the password of the user
	 */
	public final void setTaxNumber(int taxNumber){
		this.taxNumber=taxNumber;
	}
	
	/**
	 * Setter for the boolean isVIP
	 * @param VIP isVIP
	 */
	public final void setVIP(boolean VIP){
		isVIP=VIP;
	}
	
	/**
	 * Adds the specified product to the user's wishlist
	 * @param product the product to add to the wishlist
	 */
	public final void addToWishlist(Product product){
		wishlist.add(product);
		System.out.println("Product added to your wishlist!");
	}
	
	/**
	 * Adds the specified product to the user's shopping cart
	 * @param product the product to add to the shopping cart
	 */
	public final void addToShoppingCart(Product product){
		shoppingCart.add(product);
		System.out.println("Product added to your shoppingCart!");
	}
	
	/**
	 * Adds the list of products to the user's shopping cart
	 * @param products the product list to add to the shopping cart
	 */
	public final void addToShoppingCart(ArrayList<Product>products){
		shoppingCart.addAll(products);
	}
	
	/**
	 * Adds a new order to the user by using the items in its shopping cart
	 */
	public final void addOrder(){
		orders.add(new Order(shoppingCart,getIsVIP()));
	}
	
	/**
	 * Resets the shopping cart
	 */
	public final void resetShoppingCart(){
		shoppingCart.clear();
		shoppingCart.trimToSize();
	}
	
	/**
	 * Resets the wishlist
	 */
	public final void resetWishlist(){
		wishlist.clear();
		wishlist.trimToSize();
	}
	
	/**
	 * Displays the user's information
	 * @return a string containing the user's information
	 */
	@Override
	public String toString(){
		return "============== \033[1m"+getUsername()+"\033[0m ==============\n" +
			     "Name: "+getName()+"\n"+
			     "TaxNumber: "+getTaxNumber()+"\n"+
			     (getIsVIP()?"The user is VIP\n":"The user is not VIP\n")+
			     "Orders:"+(orders.size()==0?" The user has no open orders.":orders)+"\n"+
			     "Wishlist:"+(wishlist.size()==0?" The user has no items on the wishlist.":wishlist);
	}
}