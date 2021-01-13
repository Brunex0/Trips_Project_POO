package com.g24.main.writeToFile;

//imports
import java.io.*;

/**
 * Class to save the objects to a file
 */
public abstract class WriteToFile{
	/**
	 * Saves a list of objects to a file
	 * @param path the path of the file
	 * @param objectList the objectList to save
	 */
	public static void saveListToFile(File path, Object objectList){
		try{
			ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(path));
			o.writeObject(objectList);
			o.flush();
			System.out.println("Saving was successful.");
		}
		catch(IOException err){
			System.out.println(err.getMessage());
			System.out.println("Saving was unsuccessful.");
		}
	}
}