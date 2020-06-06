package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import org.junit.Assert;
import org.junit.Test;

public class SalesReporterTest {
	
	SalesReporter testReporter = new SalesReporter();
	FileReader testReader = new FileReader();
	String initialInventoryPath = "vendingmachine.csv";
	VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
	File report = new File("SalesReport.txt");
	
	public void backupHelper1(Queue<String> origReport) {

			try (Scanner reportReader = new Scanner(report)){
				while (reportReader.hasNextLine()) {
					String line = reportReader.nextLine();
					origReport.offer(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	public void backupHelper2(Queue<String> origReport) {

		try {
			if (report.exists()){
				report.delete();
			}
			report.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (Scanner reportReader = new Scanner(report);
				PrintWriter reportWriter = new PrintWriter(report)){
			while (!origReport.isEmpty()) {
				reportWriter.println(origReport.poll());
			}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public int assertHelper(String productName) {
		int reportedQuantity = 0;
		try (Scanner reportReader = new Scanner(report)) {
			while(reportReader.hasNextLine()) {
				String line = reportReader.nextLine();
				if (line.contains(productName)) {
					reportedQuantity = Integer.parseInt(line.substring(productName.length() + 1));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return reportedQuantity;
	}
	
	@Test
	public void purchase_one_potato_crisps() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		
		testMachine.buy("A1");
		testReporter.updateReport(testMachine);
		String productName = "Potato Crisps";
		
		int expectedQuantity = 1;
		
		if (didExist) {
			for (String product : origReport) {
				if (product.contains(productName)) {
					
					expectedQuantity += Integer.parseInt(product.substring(productName.length() + 1));
			
				}
			}
		}
		
		int reportedQuantity = assertHelper(productName);
		Assert.assertEquals(expectedQuantity, reportedQuantity);
		
		if (didExist = true) {
			backupHelper2(origReport);
		}
		
	}
	
	@Test
	public void purchase_two_potato_crisps() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		
		testMachine.buy("A1");
		testMachine.buy("A1");
		testReporter.updateReport(testMachine);
		String productName = "Potato Crisps";
		
		int expectedQuantity = 2;
		
		if (didExist) {
			for (String product : origReport) {
				if (product.contains(productName)) {
					
					expectedQuantity += Integer.parseInt(product.substring(productName.length() + 1));
			
				}
			}
		}
		
		int reportedQuantity = assertHelper(productName);
		Assert.assertEquals(expectedQuantity, reportedQuantity);
		
		if (didExist = true) {
			backupHelper2(origReport);
		}
		
	}
	
	@Test
	public void purchase_one_of_each() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		
		for (int i = 0; i < testMachine.getSize(); i++){
			testMachine.buy(testMachine.getProduct(i).getLocation());
		}
		
		testReporter.updateReport(testMachine);
		
		
		for (int i = 0; i < testMachine.getSize(); i++){
			int expectedQuantity = 1;
			String productName = testMachine.getProduct(i).getName();
			if (didExist) {
				for (String product : origReport) {
					if (product.contains(productName)) {
						
						expectedQuantity += Integer.parseInt(product.substring(productName.length() + 1));
				
					}
				}
			}
			int reportedQuantity = assertHelper(productName);
			Assert.assertEquals(expectedQuantity, reportedQuantity);
		}
		
		if (didExist = true) {
			backupHelper2(origReport);
		}
		
	}
	
	
	@Test
	public void purchase_one_potato_crisps_no_previous_report() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		
		testMachine.buy("A1");
		report.delete();
		testReporter.updateReport(testMachine);
		String productName = "Potato Crisps";
		
		int expectedQuantity = 1;
		
		int reportedQuantity = assertHelper(productName);
		Assert.assertEquals(expectedQuantity, reportedQuantity);
		
		if (didExist = true) {
			backupHelper2(origReport);
		}
		
	}
	

}
