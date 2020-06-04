package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderTest {

	FileReader testReader = new FileReader();

	
	@Test
	public void test_inventory() {
		VendingMachine testMachine = testReader.fillInventory();
		Product testProduct = new Product("A1","Potato Crisps",3.05,"Chip");
		Assert.assertTrue(testMachine.getProduct(0).getPrice() == testProduct.getPrice());
	}
	

}
