package com.g24.main.product;

//imports
import java.io.*;
import java.util.*;
import com.g24.main.input.Input;
import com.g24.main.writeToFile.WriteToFile;

/**
 * Defines a product and its properties
 */
public abstract class Product implements Cloneable, Serializable{
	@Serial
	private static final long serialVersionUID=176205271118950933L;
	//variables
	/**
	 * The trip's name
	 */
	protected String designation;
	/**
	 * The place of departure
	 */
	protected String departurePlace;
	/**
	 * The number of days of the trip
	 */
	protected int duration;
	/**
	 * A discount for the VIP users; must be a float in the range [0,1[
	 */
	protected float VIPdiscount;
	/**
	 * The profit of the trip for the company; must be a float in the range ]0,inf]
	 */
	protected float comission;
	/**
	 * The amount of money a normal user will have to pay with the comission included
	 */
	protected float userPrice;
	/**
	 * The price payed by the company for the trip
	 */
	protected float enterprisePrice;
	/**
	 * The user's price with the VIP's discount
	 */
	protected float VIPprice;
	
	//getters
	/**
	 * Getter for the trip's designation
	 * @return the trip's designation
	 */
	public final String getDesignation(){
		return designation;
	}
	/**
	 * Getter for the trip's departure place
	 * @return the departure place
	 */
	public final String getDeparturePlace(){
		return departurePlace;
	}
	/**
	 * Getter for the duration of the trip
	 * @return the trip's duration
	 */
	public final int getDuration(){
		return duration;
	}
	/**
	 * Getter for the VIP's discount on the current trip
	 * @return the trip VIP's discount
	 */
	public final float getVIPdiscount(){
		return VIPdiscount;
	}
	/**
	 * Getter for the comission of the trip
	 * @return the trip's comission
	 */
	public final float getComission(){
		return comission;
	}
	/**
	 * Getter for the user's price
	 * @return the user's price
	 */
	public final float getUserPrice(){
		return userPrice;
	}
	/**
	 * Getter for the VIP's price
	 * @return the VIP's price
	 */
	public final float getVIPprice(){
		return VIPprice;
	}
	/**
	 * Getter for the enterprise's price
	 * @return the enterprise's price
	 */
	public final float getEnterprisePrice(){
		return enterprisePrice;
	}
	
	//setters
	/**
	 * Setter for the trip's designation
	 * @param designation the new trip's designation
	 */
	public final void setDesignation(String designation){
		this.designation=designation;
	}
	/**
	 * Setter for the trip's departure place
	 * @param departurePlace the new trip's departure place
	 */
	public final void setDeparturePlace(String departurePlace){
		this.departurePlace = departurePlace;
	}
	/**
	 * Setter for the trip's duration
	 * @param duration the new trip's duration
	 */
	public final void setDuration(int duration){
		this.duration=duration;
	}
	
	/**
	 * Setter for the VIP's discount
	 * @param VIPdiscount the new VIP's discount
	 */
	public final void setVIPdiscount(float VIPdiscount){
		this.VIPdiscount=VIPdiscount;
	}
	/**
	 * Setter for the comission
	 * @param comission the new trip's comission
	 */
	public final void setComission(float comission){
		this.comission=comission;
	}
	/**
	 * Setter for the user price
	 * @param userPrice the new user price for the trip
	 */
	public final void setUserPrice(float userPrice){
		this.userPrice=userPrice;
	}
	/**
	 * Setter for the VIP's price
	 * @param VIPprice the new VIP's price for the trip
	 */
	public final void setVIPprice(float VIPprice){
		this.VIPprice=VIPprice;
	}
	/**
	 * Setter for the enterprise's price
	 * @param enterprisePrice the new enterprise's price for the trip
	 */
	public final void setEnterprisePrice(float enterprisePrice){
		this.enterprisePrice=enterprisePrice;
	}
	
	//equals
	/**
	 * Equals method for the product class
	 * @param obj a product object to compare
	 * @return true if the product is the same as <code>obj</code>; otherwise returns false
	 */
	@Override
	public boolean equals(Object obj){
		if(obj!=null&&obj.getClass()==this.getClass()){
			Product b1=(Product)obj;
			return this.userPrice==b1.userPrice&&
						 this.designation.equals(b1.designation)&&
						 this.duration==b1.duration&&
						 this.VIPdiscount==b1.VIPdiscount&&
						 this.comission==b1.comission&&
						 this.departurePlace.equals(b1.departurePlace);
		}
		else{
			return false;
		}
	}
	
	/**
	 * Modifies the trip to new values. -1 must be typed in each new parameter to keep the same value
	 * @param productList the list of products
	 * @param index the index of the product in the productList to modify
	 */
	public static void modifyTrip(ArrayList<Product> productList,int index){
		System.out.println("============== \033[1mModify\033[0m ==============");
		System.out.println("\033[3mType -1 if you don't want to change a parameter\033[0m");
		
		//designation
		System.out.println("What's the new trip's designation?");
		System.out.print("-> ");
		String newDesignation=Input.getString();
		if(!newDesignation.equals("-1")){
			productList.get(index).setDesignation(newDesignation);
		}
		
		//enterprise's price
		System.out.println("What's the new enterprise's price?");
		System.out.print("-> ");
		int newPrice;
		do{
			newPrice=Input.getInt();
			if(newPrice>0||newPrice==-1){
				break;
			}
			else{
				System.out.println("In a world where travelling is free, that would be a great number; but that's the problem, travelling is not free...");
			}
		}
		while(true);
		if(newPrice!=-1){
			productList.get(index).setEnterprisePrice(newPrice);
		}
		
		//duration
		System.out.println("What's the new duration of the trip (in days)?");
		System.out.print("-> ");
		int duration;
		do{
			duration=Input.getInt();
			if(duration>0||duration==-1){
				break;
			}
			else{
				System.out.println("How does that work? Staying negative days doesn't seem very possible.");
			}
		}
		while(true);
		
		if(duration!=-1){
			productList.get(index).setDuration(duration);
		}
		
		//comission
		System.out.println("What's the new comission?");
		System.out.print("-> ");
		String comissionString=Input.getString();
		float comission;
		if(!comissionString.equals("-1")){
			comission=Float.parseFloat(comissionString);
			productList.get(index).setComission(comission);
		}
		
		//vip discount
		System.out.println("What's the new VIP discount?");
		System.out.print("-> ");
		String vipDiscountString=Input.getString();
		float VIPdiscount;
		if(!vipDiscountString.equals("-1")) {
			VIPdiscount=Float.parseFloat(vipDiscountString);
			productList.get(index).setVIPdiscount(VIPdiscount);
		}
		
		//user price
		productList.get(index).setUserPrice(
			productList.get(index).getEnterprisePrice()+productList.get(index).getEnterprisePrice()*productList.get(index).getComission()
		);
		
		//vip price
		productList.get(index).setVIPprice(
			productList.get(index).getEnterprisePrice()+(productList.get(index).getEnterprisePrice()*productList.get(index).getComission()*(1-productList.get(index).getVIPdiscount()))
		);
		
		//Writes the arraylist in the file
		WriteToFile.saveListToFile(new File("src/com/g24/main/product/products.dat"),productList);
	}
	
	/**
	 * Adds a new product to the productList
	 * @param productList the productList
	 * @param isNationalProduct true if a national product is to be added and false if an international product is to be added
	 */
	public static void addProduct(ArrayList<Product> productList,boolean isNationalProduct){
		//designation
		System.out.println("What's the trip's designation?");
		System.out.print("-> ");
		String designation=Input.getString();
		
		//departure place
		System.out.println("What's the trip's departure place?");
		System.out.print("-> ");
		String departurePlace=Input.getString();
		
		//arrival place
		System.out.println("What's the trip's arrival place?");
		System.out.print("-> ");
		String arrivalPlace=Input.getString();
		
		//enterprise's price
		System.out.println("What's the enterprise's price?");
		System.out.print("-> ");
		int enterprisePrice;
		do{
			enterprisePrice=Input.getInt();
			if(enterprisePrice>0){
				break;
			}
			else{
				System.out.println("In a world where travelling is free, that would be a great number; but that's the problem, travelling is not free...");
			}
		}
		while(true);
		
		//duration
		System.out.println("What's the duration of the trip (in days)?");
		System.out.print("-> ");
		int duration;
		do{
			duration=Input.getInt();
			if(duration>0){
				break;
			}
			else{
				System.out.println("How does that work? Staying negative days doesn't seem very possible.");
			}
		}
		while(true);
		
		//comission
		System.out.println("What's the comission?");
		System.out.print("-> ");
		float comission;
		do{
			comission=Input.getFloat();
			if(comission>0){
				break;
			}
			else{
				System.out.println("Negative profit doesn't seem very profitable.");
			}
		}
		while(true);
		
		//vip discount
		System.out.println("What's the VIP discount?");
		System.out.print("-> ");
		float VIPdiscount;
		do{
			VIPdiscount=Input.getFloat();
			if(VIPdiscount>=0&&VIPdiscount<1){
				break;
			}
			else{
				System.out.println("Tha VIP discount seems a little extreme.");
			}
		}
		while(true);
		
		//add product
		Product newProduct;
		if(isNationalProduct){
			newProduct=new NationalProduct(
				designation,duration,VIPdiscount,enterprisePrice,comission,departurePlace,arrivalPlace
			);
		}
		else{
			newProduct=new InternationalProduct(
				designation,duration,VIPdiscount,enterprisePrice,comission,departurePlace,arrivalPlace
			);
		}
		if(!productList.contains(newProduct)){
			productList.add(newProduct);
			WriteToFile.saveListToFile(new File("src/com/g24/main/product/products.dat"),productList);
		}
		else{
			System.out.println("\033[1mError\033[0m: This product already exists.");
		}
	}
}