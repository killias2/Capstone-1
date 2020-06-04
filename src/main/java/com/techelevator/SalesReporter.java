package com.techelevator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SalesReporter {
	
	File report = new File("SalesReport.txt");
	
	public void updateReport(VendingMachine reportMachine) {
		try {
			if (!report.exists()) {
				report.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Product> reportUpdates = new ArrayList<Product>();
		
		for (int i = 0; i < reportMachine.getSize(); i++) {
			if (reportMachine.getProduct(i).getCount() < 5) {
				reportUpdates.add(reportMachine.getProduct(i));
			}
		}
		
		try ()
	}

}
