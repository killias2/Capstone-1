package com.techelevator;

import org.junit.Test;

public class SalesReporterTest {
	
	SalesReporter testReporter = new SalesReporter();
	FileReader testReader = new FileReader();
	
	@Test
	public void test_sales_reporter() {
		VendingMachine testMachine = testReader.fillInventory();
		testReporter.updateReport(testMachine);
	}

}
