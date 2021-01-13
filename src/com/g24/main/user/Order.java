package com.g24.main.user;

//imports
import java.io.*;
import java.util.*;
import java.time.*;
import com.g24.main.product.*;

/**
 * Represents a user's order
 */
public class Order implements Serializable{
	@Serial
	private static final long serialVersionUID=-1843935191088718277L;
	private static int lastOrder=0;
	private final int orderNumber;
	private final ArrayList<Product> products;
	private String orderDate,orderHour;
	private float price,spend;
	
	/**
	 * Constructs a new order
	 * @param products the productList
	 * @param isVIP true if the user is VIP and false otherwise
	 */
	public Order(ArrayList<Product> products, boolean isVIP){
		//orderID
		lastOrder++;
		orderNumber=lastOrder;
		//productList
		this.products=new ArrayList<>();
		addOrder(products,isVIP);
	}
	
	//getters
	/**
	 * Getter for the productList
	 * @return the productList
	 */
	public ArrayList<Product>getProducts(){
		return products;
	}
	/**
	 * Getter for the price
	 * @return the price
	 */
	public float getPrice(){
		return this.price;
	}
	/**
	 * Getter for the spent amount
	 * @return the spent amount
	 */
	public float getSpend(){
		return this.spend;
	}
	
	/**
	 * Getter for the order number
	 * @return the order number
	 */
	public int getOrderNumber(){
		return orderNumber;
	}
	//setters
	/**
	 * Setter for the price according to their VIP status
	 * @param isVIP true if the user is VIP and false otherwise
	 */
	public void setPrice(boolean isVIP){
		if(isVIP){
			for(Product product: products){
				this.price+=product.getVIPprice();
			}
		}
		else{
			for(Product product: products){
				this.price+=product.getUserPrice();
			}
		}
	}
	/**
	 * Setter for the amount spent according to the enterprise price
	 */
	public void setSpend(){
		for(Product product: products){
			this.spend+=product.getEnterprisePrice();
		}
	}
	/**
	 * Setter for last order
	 * @param lastOrder the new last order
	 */
	public static void setLastOrder(int lastOrder){
		Order.lastOrder=lastOrder;
	}
	
	/**
	 * Defines the time variables
	 */
	private void getTimeHour(){
		LocalDate date=LocalDate.now();
		this.orderDate=date.toString();
		LocalTime myObj=LocalTime.now();
		String[] aux;
		String s=myObj.toString().replace("."," ");
		aux=s.split(" ");
		this.orderHour=aux[0];
	}
	
	/**
	 * Defines the order variables
	 * @param products the productList
	 * @param isVIP true if the user is VIP and false otherwise
	 */
	public void addOrder(ArrayList<Product> products, boolean isVIP){
		this.products.addAll(products);
		getTimeHour();
		price=0.0f;
		setPrice(isVIP);
		spend=0.0f;
		setSpend();
	}
	
	/**
	 * Displays the order information
	 * @return a string containing the order's information
	 */
	@Override
	public String toString(){
		return "Order ID: "+orderNumber+", "+orderDate+" | "+orderHour;
	}
}