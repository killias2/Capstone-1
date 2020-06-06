package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderTest {

	FileReader testReader = new FileReader();
	String initialInventoryPath = "vendingmachine.csv";
	

	
	@Test
	public void test_first_product_inventory_price() {
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		Product testProduct = new Product("A1","Potato Crisps",305,"Chip",5);
		Assert.assertTrue(testMachine.getProduct(0).getPrice() == testProduct.getPrice());
	}
	
	@Test
	public void test_last_product_inventory_price() {
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		Product testProduct = new Product("D4","Triplemint",75,"Gum",5);
		Assert.assertTrue(testMachine.getProduct(15).getPrice() == testProduct.getPrice());
	}
	
	@Test
	public void test_first_product_inventory_location() {
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		Product testProduct = new Product("A1","Potato Crisps",305,"Chip",5);
		Assert.assertTrue(testMachine.getProduct(0).getLocation().equals(testProduct.getLocation()));
	}
	
	@Test
	public void test_last_product_inventory_location() {
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		Product testProduct = new Product("D4","Triplemint",75,"Gum",5);
		Assert.assertTrue(testMachine.getProduct(15).getLocation().equals(testProduct.getLocation()));
	}
	
	@Test
	public void test_first_product_inventory_name() {
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		Product testProduct = new Product("A1","Potato Crisps",305,"Chip",5);
		Assert.assertTrue(testMachine.getProduct(0).getName().equals(testProduct.getName()));
	}
	
	@Test
	public void test_last_product_inventory_name() {
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		Product testProduct = new Product("D4","Triplemint",75,"Gum",5);
		Assert.assertTrue(testMachine.getProduct(15).getName().equals(testProduct.getName()));
	}
	
	@Test
	public void test_first_product_inventory_count() {
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		Product testProduct = new Product("A1","Potato Crisps",305,"Chip",5);
		Assert.assertTrue(testMachine.getProduct(0).getCount() == testProduct.getCount());
	}
	
	@Test
	public void given_wrong_file() {
		VendingMachine testMachine = testReader.fillInventory("no_type.csv");
		Assert.assertTrue(testMachine.getSize() == 0);
	}
	
	@Test
	public void test_inventory_size() {
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		File inventoryFile = new File(initialInventoryPath);
		List<String> inventoryList = new ArrayList<String>();
		try (Scanner reader = new Scanner(inventoryFile)){
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				inventoryList.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(testMachine.getSize() == inventoryList.size());
	}

}
