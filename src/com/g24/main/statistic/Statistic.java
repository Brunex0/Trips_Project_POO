package com.g24.main.statistic;

//imports
import java.io.*;
import java.time.*;
import java.util.*;
import com.g24.main.product.*;
import com.g24.main.user.*;
import org.w3c.dom.ls.LSOutput;

/**
 * Provide statistical data about the trips
 */
public class Statistic implements Serializable{
	@Serial
	private static final long serialVersionUID=1521746478459430912L;
	private final LocalDate Date;
	private final String Hour;
	private final ArrayList<Order> ordersList;
	private final ArrayList<ProfitList> ProfitPerTripNonVIP,ProfitPerTripVIP;
	private final float AVGProfit, AVGNacTrips, AVGIntTrips;
	private final String bestSeller, MostExpensTrip, LessExpensTrip;
	
	/**
	 * Constructs a new statistical data object
	 * @param ClientsList the registered clients
	 * @param productslist the productList
	 */
	public Statistic(ArrayList<User> ClientsList,ArrayList<Product> productslist){
		this.Date=LocalDate.now();
		this.Hour=hour();
		this.ordersList=AllOrders(ClientsList);
		this.ProfitPerTripNonVIP=ProfitTrip(productslist,1);
		this.ProfitPerTripVIP=ProfitTrip(productslist,2);
		this.AVGProfit=AverageProfit(ordersList);
		this.AVGNacTrips=AVGNacional_InternacionalTrips(ordersList,1);
		this.AVGIntTrips=AVGNacional_InternacionalTrips(ordersList,2);
		this.bestSeller=bestSelling(ordersList);
		this.MostExpensTrip=MostExpensiveTrip(productslist);
		this.LessExpensTrip=LessExpensiveTrip(productslist);
	}
 
	/**
   * Getter for the best selleling trip
   * @return the best selling trip
   */
	public String getBestSeller(){
	    return bestSeller;
	}
	
	/**
   * Getter for the average profit
   * @return the average profit
   */
	public float getAVGProfit(){
	    return AVGProfit;
	}
	
	/**
   * Getter for the average of Nacional trips
   * @return the average of Nacional trips
   */
	public float getAVGNacTrips(){
	    return AVGNacTrips;
	}
	
	/**
   * Getter the average of Internacional trips
   * @return the average of Internacional trips
   */
	public float getAVGIntTrips(){
	    return AVGIntTrips;
	}
	
	/**
   * Getter for the profit per trip for non VIP users
   * @return an arraylist with the designation and profit per trip for non VIP users
   */
	public ArrayList<ProfitList> getProfitPerTripNonVIP(){
	    return ProfitPerTripNonVIP;
	}
	
	/**
   * Getter for the profit per trip for VIP users
   * @return an arraylist with the designation and profit per trip for VIP users
   */
	public ArrayList<ProfitList> getProfitPerTripVIP(){
	    return ProfitPerTripVIP;
	}
	
	/**
   * Getter for the least expensive trip
   * @return the least expensive trip
   */
	public String getLessExpensTrip(){
	    return LessExpensTrip;
	}
	
	/**
   * Getter for the most expensive trip
   * @return the most expensive trip
   */
	public String getMostExpensTrip(){
	    return MostExpensTrip;
	}
	
	/**
   * Removes the milliseconds from localTime
	 * @return the hour without milliseconds
	 */
	public String hour(){
		LocalTime myObj=LocalTime.now();
		String[] aux;
		String s=myObj.toString().replace("."," ");
		aux=s.split(" ");
		return aux[0];
	}
    
	/**
   * Joins all client's orders in a single array
   * @param ClientsList the registered clients
   * @return an array of all client's orders
   */
	public static ArrayList<Order>AllOrders(ArrayList<User> ClientsList){
		ArrayList<Order> All_Orders=new ArrayList<>();
		for(User cl: ClientsList){
			if(cl instanceof Client&&((Client)cl).getOrders().size()!=0){
				All_Orders.addAll(((Client)cl).getOrders());
			}
		}
		return All_Orders;
	}
	
	/**
	 * Calculates the profit by trip
	 * @param productslist the productList
	 * @param case_VIP  to know if the user is VIP or not. (1-Non VIP | 2-VIP)
	 * @return an arraylist with the designation and profit of each trip
	 */
	public ArrayList<ProfitList> ProfitTrip(ArrayList<Product> productslist,int case_VIP){
		ArrayList<ProfitList> list=new ArrayList<>();
		float difference;
		for(Product p: productslist){
			switch(case_VIP){
				case 1:
					difference=p.getUserPrice()-p.getEnterprisePrice();
					ProfitList plist0=new ProfitList(p.getDesignation(),difference);
					list.add(plist0);
					break;
				case 2:
					difference=p.getVIPprice()-p.getEnterprisePrice();
					ProfitList plist1=new ProfitList(p.getDesignation(),difference);
					list.add(plist1);
					break;
			}
		}
		return list;
	}
	
	/**
	 * Calculates the average profit
	 * @param orderslist all order's details
	 * @return the average profit
	 */
	public float AverageProfit(ArrayList<Order> orderslist){
		float AVGProfit=0;
		for(Order o: ordersList){
			AVGProfit+=(o.getPrice()-o.getSpend());
		}
		AVGProfit=AVGProfit/orderslist.size();
		return AVGProfit;
	}
	
	/**
	 * Calculates the average of Nacional or Internacional trips
	 * @param orderslist all order's details
	 * @param case_only to know if we calculate the average of Nacional or Internacional trips. (1- Nacional Trips | 2-Internacional Trips)
	 * @return the average of Nacional or Internacional trips
	 */
	public float AVGNacional_InternacionalTrips(ArrayList<Order> orderslist,int case_only){
		float Trip=0;
		float AVG;
		int size=0;
		for(Order n: ordersList){
			for(int j=0;j<n.getProducts().size();j++){
				switch(case_only){
					case 1:
						if(n.getProducts().get(j) instanceof NationalProduct){
							Trip+=1;
            }
            size++;
            break;
					case 2:
						if(n.getProducts().get(j) instanceof InternationalProduct){
							Trip+=1;
            }
            size++;
            break;
				}
			}
		}
		AVG=(Trip/size)*100;
		return AVG;
	}
	
	/**
	 * Determines the best selling trip
	 * @param orderslist all order's details
	 * @return the designation of the most selling trip
	 */
	public String bestSelling(ArrayList<Order> orderslist){
		int max=0;
		int aux=0;
		String best="";
		String temp;
		for(Order n: orderslist){
			for(int i=0;i<n.getProducts().size();i++){
				temp=n.getProducts().get(i).getDesignation();
				for(int j=0;j<n.getProducts().size();j++){
					if(temp.equals(n.getProducts().get(j).getDesignation())){
						aux++;
					}
					if(aux>max){
						best=temp;
						max=aux;
					}
				}
			}
		}
		return best;
	}
	
	/**
	 * Determines the most expensive trip
	 * @param productslist the productList
	 * @return the designation of the most expensive trip
	 */
	public String MostExpensiveTrip(ArrayList<Product> productslist){
		float aux=0;
		String expensive="";
		for(Product p: productslist){
			if(p.getUserPrice()>aux){
				aux=p.getUserPrice();
				expensive=p.getDesignation();
			}
		}
		return expensive;
	}
	
	/**
	 * Determines the trip with the lowest price
	 * @param productslist the productList
	 * @return the designation of the least expensive trip
	 */
	public String LessExpensiveTrip(ArrayList<Product> productslist){
		float aux=productslist.get(0).getUserPrice();
		String less=productslist.get(0).getDesignation();
		for(Product p: productslist){
			if(p.getUserPrice()<aux){
				aux=p.getUserPrice();
				less=p.getDesignation();
			}
		}
		return less;
	}
	
	/**
	 * Displays the statistic's information
	 * @return a string containing the statistic's information
	 */
	@Override
	public String toString(){
		return "\n============== \033[1mStatistics\033[0m ==============\n"+
           "Date: "+Date+"\n"+
			     "Hour: "+Hour+"\n"+
			     "Profit per trip non VIP: "+ProfitPerTripNonVIP+"\n"+
			     "Profit per trip VIP: "+ProfitPerTripVIP+"\n"+
			     "Average Profit: "+AVGProfit+"\n"+
			     "Average Nacional Trips: "+AVGNacTrips+"\n"+
			     "Average Internacioal Trips: "+AVGIntTrips+"\n"+
			     "Best seller trip: "+bestSeller+"\n"+
			     "Most expensive Trip: "+MostExpensTrip+"\n"+
			     "Less expensive Trip: "+LessExpensTrip+"\n";
	}
	
	//Funcao Defesa trabalho
	public static void nomeCliente(ArrayList<User> registeredUsers){
		String nome="";
		int qtd=0, max=0;
		for(int i=0;i<registeredUsers.size();i++){
			if(registeredUsers.get(i) instanceof Client){
				for(int j=0;j<((Client)registeredUsers.get(i)).getOrders().size();j++){
					qtd+=((Client)registeredUsers.get(i)).getOrders().get(j).getProducts().size();
				}
				if(qtd>max){
					max=qtd;
					nome = registeredUsers.get(i).getName();
				}
				qtd=0;
			}
		}
		System.out.println("Nome cliente com mais viagens: "+ nome);
	}
}


