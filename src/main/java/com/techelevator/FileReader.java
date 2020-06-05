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
				
				Product newProduct = new Product(productArray[0],productArray[1],
						(int)(Double.parseDouble(productArray[2]) * 100),productArray[3], 5);
				initVendingMachine.add(newProduct);
				
//				if (productArray[3].equals("Chip")) {
//					Chips newChips = new Chips(productArray[0],productArray[1],
//							(int)(Double.parseDouble(productArray[2]) * 100),productArray[3], 5);
//					initVendingMachine.add(newChips);
//					
//				}
//				if (productArray[3].equals("Candy")) {
//					Candy newCandy = new Candy(productArray[0],productArray[1],
//							(int)(Double.parseDouble(productArray[2]) * 100),productArray[3], 5);
//					initVendingMachine.add(newCandy);
//					
//				}
//				if (productArray[3].equals("Drink")) {
//					Drinks newDrinks = new Drinks(productArray[0],productArray[1],
//							(int)(Double.parseDouble(productArray[2]) * 100),productArray[3], 5);
//					initVendingMachine.add(newDrinks);
//					
//				}
//				if (productArray[3].equals("Gum")) {
//					ChewingGum newGum = new ChewingGum(productArray[0],productArray[1],
//							(int)(Double.parseDouble(productArray[2]) * 100),productArray[3], 5);
//					initVendingMachine.add(newGum);
//				}
				
			}
			return initVendingMachine;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
