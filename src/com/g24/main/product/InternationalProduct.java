package com.g24.main.product;

import java.io.Serial;

/**
 * Defines an international product
 */
public class InternationalProduct extends Product{
	@Serial
	private static final long serialVersionUID=-4965646375701477202L;
	/**
	 * The trip's arrival place
	 */
	protected String arrivalPlace;
	
	/**
	 * Constructs a new international trip
	 * @param designation the trip's designation
	 * @param duration the trip's duration
	 * @param VIPdiscount the trip's VIP discount
	 * @param enterprisePrice the enterprise price
	 * @param comission the trip's comission
	 * @param departurePlace the trip's departure place
	 * @param arrivalPlace the trip's arrival place
	 */
	public InternationalProduct(String designation,int duration,float VIPdiscount,float enterprisePrice,float comission,String departurePlace,String arrivalPlace){
		super.designation=designation;
		super.duration=duration;
		super.comission=comission;
		super.VIPdiscount=VIPdiscount;
		super.enterprisePrice=enterprisePrice;
		super.userPrice=enterprisePrice+enterprisePrice*comission;
		super.VIPprice=enterprisePrice+enterprisePrice*comission*(1-VIPdiscount);
		super.departurePlace=departurePlace;
		this.arrivalPlace=arrivalPlace;
	}
	
	//getters
	/**
	 * Getter for the arrival place
	 * @return the trip's arrival place
	 */
	public String getArrivalPlace(){
		return arrivalPlace;
	}
	
	//setters
	/**
	 * Setter for the arrival place
	 * @param arrivalPlace the trip's new arrival place
	 */
	public void setArrivalPlace(String arrivalPlace){
		this.arrivalPlace=arrivalPlace;
	}
	
	/**
	 * Shows the product's information
	 * @return a string containing the product's information
	 */
	@Override
	public String toString(){
		return "============== \033[1m"+designation+"\033[0m ==============\n"+
			"From \033[3m"+departurePlace+"\033[0m\n"+
			"To \033[3m"+arrivalPlace+"\033[0m\n"+
			"The trip has a duration of "+duration+" days\n"+
			"Regular user's price: "+userPrice+"€ | VIP's price: "+VIPprice+"€";
	}
}