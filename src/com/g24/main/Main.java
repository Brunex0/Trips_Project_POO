package com.g24.main;

//java class library imports
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//project imports
import com.g24.main.input.Input;
import com.g24.main.statistic.Statistic;
import com.g24.main.user.*;
import com.g24.main.product.*;

/**
 * The main class of the trip agency program. Manages the option menus and handle method calls.
 * @author Ângelo Morgado, a43855
 * @author Bruno Monteiro, a43994
 * @author Duarte Arribas, a44585
 * @author Henrique Jesus, a43931
 */
public class Main{
	/**
	 * Tells wether the current user is logged in or not
	 */
	public static boolean loggedIn=false;
	
	/**
	 * The main method is called whenever the program starts
	 * @param args commandLineOptions (None in our case)
	 */
	public static void main(String[] args){
		//loggedOut options
		User loggedUser=null;
		int option=0, pageNumber=1,pageNumber2=1;
		boolean inProductMenu, inProductMenu2;
		
		//get list of registered users
		ArrayList<User>registeredUsers=new ArrayList<>();
		try(ObjectInputStream o=new ObjectInputStream(new FileInputStream("src/com/g24/main/user/users.dat"))){
			registeredUsers=(ArrayList<User>)o.readObject();
		}
		catch(IOException|ClassNotFoundException err){
			System.out.println(err.getMessage());
		}
		
		//read number of orders
		int countOrders=0;
		for(User c:registeredUsers){
			if(c instanceof Client){
				for(Order o:((Client)(c)).getOrders()){
					countOrders++;
				}
			}
		}
		Order.setLastOrder(countOrders);
		
		//get list of products
		ArrayList<Product> productList = new ArrayList<>();
		try(ObjectInputStream o=new ObjectInputStream(new FileInputStream("src/com/g24/main/product/products.dat"))){
			productList=(ArrayList<Product>)o.readObject();
		}
		catch(IOException|ClassNotFoundException err){
			System.out.println(err.getMessage());
		}
		int numberOfProducts=productList.size();
		
		//get list of statistics
		ArrayList<Statistic>statisticList=new ArrayList<>();
		try(ObjectInputStream o=new ObjectInputStream(new FileInputStream("src/com/g24/main/statistic/statistic.dat"))){
			statisticList=(ArrayList<Statistic>)o.readObject();
		}
		catch(IOException|ClassNotFoundException err){
			System.out.println(err.getMessage());
		}
		
		//shoppingCart
		ArrayList<Product>loggedOutShoppingCart=new ArrayList<>();
		
		//initialMenu
		int initialMenuOption;
		while(true){
			Menu.initialMenu();
			try{
				initialMenuOption=Option.getOption(0,2);
			}
			catch(OptionNotInRangeException err){
				System.out.println(err.getMessage());
				continue;
			}
			if(initialMenuOption==0){
				return;
			}
			else if(initialMenuOption==1){
				break;
			}
			else{
				if(!Register.registerAdmin(registeredUsers)){
					return;
				}
				else{
					break;
				}
			}
		}
		
		do{
			if(loggedUser!=null){
				loggedIn=true;
			}
			//==================================== Menu for loggedOut users ====================================
			if(!loggedIn){
				Menu.printNonLoggedInMenu();
				//get menu option
				while(true){
					try{
						option=Option.getOption(0,4);
						break;
					}
					catch(OptionNotInRangeException err){
						System.out.println(err.getMessage());
						Menu.printNonLoggedInMenu();
					}
					catch(NumberFormatException err){
						System.out.println("Invalid option!");
						Menu.printNonLoggedInMenu();
					}
				}
				//handle menu options
				switch(option){
					case 1:
						loggedUser=Login.login(registeredUsers,loggedOutShoppingCart);
						if(loggedUser!=null&&(loggedUser.getClass().toString().split("\\."))[4].equals("Client")){
							((Client)loggedUser).addToShoppingCart(loggedOutShoppingCart);
						}
						break;
					case 2:
						Register.register(registeredUsers);
						break;
					case 3:
						//handles the productListMenu
						inProductMenu=true;
						while(inProductMenu){
							Menu.printProductsListMenu(productList,pageNumber);
							int optionProduct;
							//get productListMenu option
							while(true){
								try{
									if(pageNumber==1||(numberOfProducts%5==0&&pageNumber==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber==(numberOfProducts/5)+1)){
										if((numberOfProducts%5==0&&pageNumber==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber==(numberOfProducts/5)+1)){
											optionProduct=Option.getOption(0,1+numberOfProducts%5);
										}
										else{
											optionProduct=Option.getOption(0,6);
										}
									}
									else{
										if((numberOfProducts%5==0&&pageNumber==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber==(numberOfProducts/5)+1)){
											optionProduct=Option.getOption(0,2+numberOfProducts%5);
										}
										else{
											optionProduct=Option.getOption(0,7);
										}
									}
									break;
								}
								catch(OptionNotInRangeException err){
									System.out.println(err.getMessage());
									Menu.printProductsListMenu(productList,pageNumber);
								}
								catch(NumberFormatException err){
									System.out.println("Invalid option!");
									Menu.printProductsListMenu(productList,pageNumber);
								}
							}
							//show the specified page of products
							//page1
							if(pageNumber==1){
								if(productList.size()>5){
									switch(optionProduct){
										case 1:
											pageNumber++;
											break;
										case 2:
										case 3:
										case 4:
										case 5:
										case 6:
											Menu.productMenu(productList,optionProduct,loggedOutShoppingCart,false);
											break;
										case 0:
											inProductMenu=false;
											pageNumber=1;
											break;
									}
								}
								else{
									switch(optionProduct){
										case 1:
										case 2:
										case 3:
										case 4:
										case 5:
											Menu.productMenu(productList,optionProduct+1,loggedOutShoppingCart,false);
											break;
										case 0:
											inProductMenu=false;
											pageNumber=1;
											break;
									}
								}
							}
							//last page
							else if((numberOfProducts%5==0&&pageNumber==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber==(numberOfProducts/5)+1)){
								switch(optionProduct){
									case 1:
										pageNumber--;
										break;
									case 2:
									case 3:
									case 4:
									case 5:
									case 6:
										Menu.productMenu(productList,optionProduct+5*(pageNumber-1),loggedOutShoppingCart,false);
										break;
									case 0:
										inProductMenu=false;
										pageNumber=1;
										break;
								}
							}
							//any page in the middle
							else{
								switch(optionProduct){
									case 1:
										pageNumber++;
										break;
									case 2:
										pageNumber--;
										break;
									case 3:
									case 4:
									case 5:
									case 6:
									case 7:
										Menu.productMenu(productList,(optionProduct+5*(pageNumber-1))-1,loggedOutShoppingCart,false);
										break;
									case 0:
										inProductMenu=false;
										pageNumber=1;
										break;
								}
							}
						}
						break;
					case 4:
						if(loggedOutShoppingCart.size()==0){
							System.out.println("There aren't any products in the shopping cart!");
						}
						else{
							for(Product product:loggedOutShoppingCart){
								System.out.println(product.getDesignation()+":"+product.getUserPrice()+"€");
							}
						}
						break;
				}
			}
			//==================================== Menu for loggedIn users ====================================
			else{
				try{
					//prints the menu and gets the user type
					String userType;
					if(loggedUser==null){
						loggedIn=false;
						continue;
					}
					else{
						userType=Menu.printMenu(loggedUser);
					}
					
					//==================================== Handles admin menu options ====================================
					if(userType.equals("Admin")){
						while(true){
							try{
								option=Option.getOption(0,6);
								break;
							}
							catch(OptionNotInRangeException err){
								System.out.println(err.getMessage());
								Menu.printMenu(loggedUser);
							}
							catch(NumberFormatException err){
								System.out.println("Invalid option!");
								Menu.printMenu(loggedUser);
							}
						}
						
						switch(option){
							case 1:
								if(Menu.addTripMenu(productList)){
									numberOfProducts=productList.size();
								}
								break;
							case 2:
								//handles the productListMenu
								inProductMenu2=true;
								while(inProductMenu2){
									Menu.printProductsListMenu(productList,pageNumber2);
									int optionProduct2;
									//get productListMenu option
									while(true){
										try{
											if(pageNumber2==1||(numberOfProducts%5==0&&pageNumber2==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber2==(numberOfProducts/5)+1)){
												if((numberOfProducts%5!=0&&pageNumber2==(numberOfProducts/5)+1)){
													optionProduct2=Option.getOption(0,1+numberOfProducts%5);
												}
												else{
													optionProduct2=Option.getOption(0,6);
												}
											}
											else{
												if((numberOfProducts%5==0&&pageNumber2==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber2==(numberOfProducts/5)+1)){
													optionProduct2=Option.getOption(0,2+numberOfProducts%5);
												}
												else{
													optionProduct2=Option.getOption(0,7);
												}
											}
											break;
										}
										catch(OptionNotInRangeException err){
											System.out.println(err.getMessage());
											Menu.printProductsListMenu(productList,pageNumber2);
										}
										catch(NumberFormatException err){
											System.out.println("Invalid option!");
											Menu.printProductsListMenu(productList,pageNumber2);
										}
									}
									//show the specified page of products
									//page1
									if(pageNumber2==1){
										if(productList.size()>5){
											switch(optionProduct2){
												case 1:
													pageNumber2++;
													break;
												case 2:
												case 3:
												case 4:
												case 5:
												case 6:
													Menu.productMenu(productList,optionProduct2,loggedOutShoppingCart,true);
													break;
												case 0:
													inProductMenu2=false;
													pageNumber2=1;
													break;
											}
										}
										else{
											switch(optionProduct2){
												case 1:
												case 2:
												case 3:
												case 4:
												case 5:
													Menu.productMenu(productList,optionProduct2+1,loggedOutShoppingCart,true);
													break;
												case 0:
													inProductMenu2=false;
													pageNumber2=1;
													break;
											}
										}
									}
									//last page
									else if((numberOfProducts%5==0&&pageNumber2==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber2==(numberOfProducts/5)+1)){
										switch(optionProduct2){
											case 1:
												pageNumber2--;
												break;
											case 2:
											case 3:
											case 4:
											case 5:
											case 6:
												Menu.productMenu(productList,optionProduct2+5*(pageNumber2-1),loggedOutShoppingCart,true);
												break;
											case 0:
												inProductMenu2=false;
												pageNumber2=1;
												break;
										}
									}
									//any page in the middle
									else{
										switch(optionProduct2){
											case 1:
												pageNumber2++;
												break;
											case 2:
												pageNumber2--;
												break;
											case 3:
											case 4:
											case 5:
											case 6:
											case 7:
												Menu.productMenu(productList,(optionProduct2+5*(pageNumber2-1))-1,loggedOutShoppingCart,true);
												break;
											case 0:
												inProductMenu2=false;
												pageNumber2=1;
												break;
										}
									}
								}
								break;
							case 3:
								String opc;
								int opc2, ls=registeredUsers.size(), pg=1, lastPage;
								boolean b=true;
								if(ls%5==0){
									lastPage=ls/5;
								}
								else{
									lastPage=ls/5+1;
								}
								do{
									User.inspectCustomers(registeredUsers,pg,lastPage,ls);
									opc=Input.getString();
									try{
										opc2=Integer.parseInt(opc);
									}
									catch(NumberFormatException err){
										opc2=-1;
									}
									
									if(opc.equals("n")&&pg<lastPage){
										pg++;
									}
									else if(opc.equals("p")&&pg>1){
										pg--;
									}
									else if((pg==lastPage)&&(opc2>=1&&opc2<=ls%5)){
										User.showCustomerInfo(registeredUsers,opc2+5*(pg-1));
									}
									else if((pg!=lastPage)&&(opc2>=1&&opc2<=5)){
										User.showCustomerInfo(registeredUsers,opc2+5*(pg-1));
									}
									else if(opc2==0){
										b=false;
									}
									else{
										System.out.println("Invalid option!");
									}
								}
								while(b);
								break;
							case 4:
								Menu.firstStatisticMenu(statisticList,productList,registeredUsers);
								break;
							case 5:
								Menu.printSettingsMenuAdmin();
								int optionSettings;
								boolean inSettings=true;
								while(inSettings){
									while(true){
										try{
											optionSettings=Option.getOption(0,2);
											break;
										}
										catch(OptionNotInRangeException err){
											System.out.println(err.getMessage());
											Menu.printSettingsMenu();
										}
										catch(NumberFormatException err){
											System.out.println("Invalid option!");
											Menu.printSettingsMenu();
										}
									}
									switch(optionSettings){
										case 1:
											String passwordTemp;
											Pattern p=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*^&+=]).{8,}$");
											Matcher m;
											while(true){
												System.out.println("New Password:");
												passwordTemp=Input.getString();
												m=p.matcher(passwordTemp);
												if(m.find()){
													break;
												}
												else{
													System.out.println("Password too weak! The password must be, at least, 8 characters long and "+
														"contain one lower and upper case character, a digit, and a special character.");
												}
											}
											loggedUser.setPassword(passwordTemp);
											User.updateUser(registeredUsers,loggedUser);
											inSettings=false;
											break;
										case 2:
											String nameTemp;
											do{
												System.out.println("New Name:");
												nameTemp=Input.getString();
											}
											while(nameTemp.trim().isEmpty()||nameTemp.length()>30);
											loggedUser.setName(nameTemp);
											User.updateUser(registeredUsers,loggedUser);
											inSettings=false;
											break;
										case 0:
											inSettings=false;
											break;
									}
								}
								break;
							case 6:
								loggedIn=false;
								loggedUser=null;
								break;
						}
					}
					else{
						Client clientLogged=(Client)loggedUser;
						//==================================== Handles client menu options ====================================
						while(true){
							try{
								option=Option.getOption(0,6);
								break;
							}
							catch(OptionNotInRangeException err){
								System.out.println(err.getMessage());
								Menu.printMenu(loggedUser);
							}
							catch(NumberFormatException err){
								System.out.println("Invalid menu option!");
								Menu.printMenu(loggedUser);
							}
						}
						
						switch(option){
							case 1:
								//handles the productListMenu
								inProductMenu=true;
								while(inProductMenu){
									Menu.printProductsListMenu(productList,pageNumber);
									int optionProduct;
									//get productListMenu option
									while(true){
										try{
											if(pageNumber==1||(numberOfProducts%5==0&&pageNumber==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber==(numberOfProducts/5)+1)){
												if((numberOfProducts%5!=0&&pageNumber==(numberOfProducts/5)+1)){
													optionProduct=Option.getOption(0,1+numberOfProducts%5);
												}
												else{
													optionProduct=Option.getOption(0,6);
												}
											}
											else{
												if((numberOfProducts%5==0&&pageNumber==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber==(numberOfProducts/5)+1)){
													optionProduct=Option.getOption(0,2+numberOfProducts%5);
												}
												else{
													optionProduct=Option.getOption(0,7);
												}
											}
											break;
										}
										catch(OptionNotInRangeException err){
											System.out.println(err.getMessage());
											Menu.printProductsListMenu(productList,pageNumber);
										}
										catch(NumberFormatException err){
											System.out.println("Invalid option!");
											Menu.printProductsListMenu(productList,pageNumber);
										}
									}
									//show the specified page of products
									//page1
									if(pageNumber==1){
										if(productList.size()>5){
											switch(optionProduct){
												case 1:
													pageNumber++;
													break;
												case 2:
												case 3:
												case 4:
												case 5:
												case 6:
													Menu.productMenu(productList,optionProduct,(Client)loggedUser);
													break;
												case 0:
													inProductMenu=false;
													pageNumber=1;
													break;
											}
										}
										else{
											switch(optionProduct){
												case 1:
												case 2:
												case 3:
												case 4:
												case 5:
													Menu.productMenu(productList,optionProduct+1,(Client)loggedUser);
													break;
												case 0:
													inProductMenu=false;
													pageNumber=1;
													break;
											}
										}
									}
									//last page
									else if((numberOfProducts%5==0&&pageNumber==numberOfProducts/5)||(numberOfProducts%5!=0&&pageNumber==(numberOfProducts/5)+1)){
										switch(optionProduct){
											case 1:
												pageNumber--;
												break;
											case 2:
											case 3:
											case 4:
											case 5:
											case 6:
												Menu.productMenu(productList,optionProduct+5*(pageNumber-1),(Client)loggedUser);
												break;
											case 0:
												inProductMenu=false;
												pageNumber=1;
												break;
										}
									}
									//any page in the middle
									else{
										switch(optionProduct){
											case 1:
												pageNumber++;
												break;
											case 2:
												pageNumber--;
												break;
											case 3:
											case 4:
											case 5:
											case 6:
											case 7:
												Menu.productMenu(productList,(optionProduct+5*(pageNumber-1))-1,(Client)loggedUser);
												break;
											case 0:
												inProductMenu=false;
												pageNumber=1;
												break;
										}
									}
									User.updateUser(registeredUsers,loggedUser);
								}
								break;
							case 2:
								if(clientLogged.getShoppingCart().size()==0){
									System.out.println("There aren't any items in your shopping cart!");
								}
								else{
									System.out.println("============== Shopping cart ==============");
									for(Product p:clientLogged.getShoppingCart()){
										System.out.println("\033[1m"+p.getDesignation()+"\033[0m: "+((clientLogged.getIsVIP())?p.getVIPprice()+"€":p.getUserPrice()+"€"));
									}
									System.out.println("===========================================");
									Menu.printCheckoutMenu();
									int optionCart;
									boolean isInShoppingCart=true;
									while(isInShoppingCart){
										while(true){
											try{
												optionCart=Option.getOption(0,3);
												break;
											}
											catch(OptionNotInRangeException err){
												System.out.println(err.getMessage());
												Menu.printCheckoutMenu();
											}
											catch(NumberFormatException err){
												System.out.println("Invalid option!");
												Menu.printCheckoutMenu();
											}
										}
										switch(optionCart){
											case 1:
												((Client)loggedUser).addOrder();
												((Client)loggedUser).resetShoppingCart();
												System.out.println("Thank you for your purchase!");
												User.updateUser(registeredUsers,loggedUser);
												isInShoppingCart=false;
												break;
											case 2:
												((Client)loggedUser).resetShoppingCart();
												System.out.println("Shopping cart resetted!");
												isInShoppingCart=false;
												break;
											case 3:
												isInShoppingCart=false;
												break;
										}
									}
								}
								break;
							case 3:
								if(clientLogged.getWishlist().size()==0){
									System.out.println("Wishlist is empty");
								}
								else{
									System.out.println("============== WishList ==============");
									for(Product p:clientLogged.getWishlist()){
										System.out.println("\033[1m"+p.getDesignation()+"\033[0m: "+((clientLogged.getIsVIP())?p.getVIPprice()+"€":p.getUserPrice()+"€"));
									}
									System.out.println("===========================================");
									Menu.printWishlistMenu();
									int optionWishlist;
									boolean isInWishlist=true;
									while(isInWishlist){
										while(true){
											try{
												optionWishlist=Option.getOption(0,3);
												break;
											}
											catch(OptionNotInRangeException err){
												System.out.println(err.getMessage());
												Menu.printWishlistMenu();
											}
											catch(NumberFormatException err){
												System.out.println("Invalid option!");
												Menu.printWishlistMenu();
											}
										}
										switch(optionWishlist){
											case 1:
												((Client)loggedUser).addToShoppingCart(((Client)loggedUser).getWishlist());
												((Client)loggedUser).resetWishlist();
												System.out.println("Wishlist successfully converted to shopping cart!");
												User.updateUser(registeredUsers,loggedUser);
												isInWishlist=false;
												break;
											case 2:
												((Client)loggedUser).resetWishlist();
												System.out.println("Wishlist resetted!");
												isInWishlist=false;
												break;
											case 3:
												isInWishlist=false;
												break;
										}
									}
								}
								break;
							case 4:
								if(clientLogged.getOrders().size()==0){
									System.out.println("There aren't any orders made yet.");
									break;
								}
								System.out.println("============== \033[1mOrders\033[0m ==============");
								for(int i=0;i<clientLogged.getOrders().size();i++){
									System.out.println("Order ID: "+clientLogged.getOrders().get(i).getOrderNumber());
									for(int j=0;j<clientLogged.getOrders().get(i).getProducts().size();j++){
										System.out.println("Product Name: "+clientLogged.getOrders().get(i).getProducts().get(j).getDesignation());
									}
									System.out.println("Cost: "+clientLogged.getOrders().get(i).getPrice()+"€");
									if(i!=clientLogged.getOrders().size()-1){
										System.out.println();
									}
								}
								break;
							case 5:
								Menu.printSettingsMenu();
								int optionSettings;
								boolean inSettings=true;
								while(inSettings){
									while(true){
										try{
											optionSettings=Option.getOption(0,4);
											break;
										}
										catch(OptionNotInRangeException err){
											System.out.println(err.getMessage());
											Menu.printSettingsMenu();
										}
										catch(NumberFormatException err){
											System.out.println("Invalid option!");
											Menu.printSettingsMenu();
										}
									}
									switch(optionSettings){
										case 1:
											String passwordTemp;
											Pattern p=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*^&+=]).{8,}$");
											Matcher m;
											while(true){
												System.out.println("New Password:");
												passwordTemp=Input.getString();
												m=p.matcher(passwordTemp);
												if(m.find()){
													break;
												}
												else{
													System.out.println("Password too weak! The password must be, at least, 8 characters long and "+
														"contain one lower and upper case character, a digit, and a special character.");
												}
											}
											loggedUser.setPassword(passwordTemp);
											User.updateUser(registeredUsers,loggedUser);
											inSettings=false;
											break;
										case 2:
											String nameTemp;
											do{
												System.out.println("New Name:");
												nameTemp=Input.getString();
											}
											while(nameTemp.trim().isEmpty()||nameTemp.length()>30);
											loggedUser.setName(nameTemp);
											User.updateUser(registeredUsers,loggedUser);
											inSettings=false;
											break;
										case 3:
											int taxNumberTemp;
											while(true){
												try{
													System.out.println("New Tax Number:");
													taxNumberTemp=Register.getTaxNumber(registeredUsers);
													break;
												}
												catch(AlreadyRegisteredException|OptionNotInRangeException err){
													System.out.println(err.getMessage());
												}
											}
											((Client)(loggedUser)).setTaxNumber(taxNumberTemp);
											User.updateUser(registeredUsers,loggedUser);
											inSettings=false;
											break;
										case 4:
											if(clientLogged.getIsVIP()){
												System.out.println("You're already a VIP user. If you wish to give us more money, please consider donating to our onlyfans instead.");
												break;
											}
											Menu.printMembershipMenu();
											int membershipOption;
											while(true){
												try{
													membershipOption=Option.getOption(0,1);
													break;
												}
												catch(OptionNotInRangeException err){
													System.out.println(err.getMessage());
													Menu.printSettingsMenu();
												}
												catch(NumberFormatException err){
													System.out.println("Invalid option!");
													Menu.printSettingsMenu();
												}
											}
											if(membershipOption==1){
												((Client)(loggedUser)).setVIP(true);
												User.updateUser(registeredUsers,loggedUser);
											}
											inSettings=false;
											break;
										case 0:
											inSettings=false;
											break;
									}
								}
								break;
							case 6:
								loggedIn=false;
								loggedUser=null;
								break;
						}
					}
				}
				catch(IllegalClassNameException err){
					System.out.println(err.getMessage());
				}
			}
		}
		while(option!=0);
	}
}