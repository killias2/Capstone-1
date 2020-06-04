package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
	
	File initialInventory = new File("vendingmachine.csv");
	
	public VendingMachine fillInventory() {
		VendingMachine initVendingMachine = new VendingMachine();
		try (Scanner reader = new Scanner(initialInventory)){
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String[] productArray = line.split("\\|");
				Product newProduct = new Product(productArray[0],productArray[1],Double.parseDouble(productArray[2]),productArray[3]);
//				Product newProduct = new Product(productArray[0],productArray[1],Double.parseDouble(productArray[2]),productArray[3],5);
				initVendingMachine.add(newProduct);
			}
			return initVendingMachine;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
