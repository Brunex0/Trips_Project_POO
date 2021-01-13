package com.g24.main.statistic;

//imports
import java.io.Serial;
import java.io.Serializable;

/**
 * Provides the designation and profit of a trip
 */
public class ProfitList implements Serializable{
	@Serial
	private static final long serialVersionUID=8673528633571529585L;
	/**
   * The trip's designation
   */
	protected String designation;
	/**
   * The trip's profit
   */
	protected float profit;
	
	/**
	 * Constructs a new ProfitList
	 * @param designation the trip's name
	 * @param profit the trip's Profit
	 */
	public ProfitList(String designation,float profit){
		this.designation=designation;
		this.profit=profit;
	}
	
	/**
	 * Profitlist with the trip's name and profit
	 * @return the profitlist with the trip's name and profit
	 */
	@Override
	public String toString(){
		return "ProfitList{ "+
			"Designation: '"+designation+'\''+
			", Profit = "+String.format("%.2f",profit)+"€"+
			'}';
	}
}