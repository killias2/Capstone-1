package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
	
	public int assertSalesHelper() {
		int totalSales = 0;
		try (Scanner reportReader = new Scanner(report)) {
			while(reportReader.hasNextLine()) {
				String line = reportReader.nextLine();
				if (line.contains("$")) {
					String dollarSales = line.substring(1,line.length()-3);
					String centSales = line.substring(line.length()-2);
					String salesString = dollarSales + centSales;
					totalSales = Integer.parseInt(salesString);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return totalSales;
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
	
	@Test
	public void purchase_double_digit_moonpie_no_previous_report() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		for (int i = 0; i < 10; i++) {
			testMachine.buy("B1");
		}
		report.delete();
		testReporter.updateReport(testMachine);
		String productName = "Moonpie";
		
		int expectedQuantity = 10;
		
		int reportedQuantity = assertHelper(productName);
		Assert.assertEquals(expectedQuantity, reportedQuantity);
		
		if (didExist = true) {
			backupHelper2(origReport);
		}
		
	}
	
	@Test
	public void stream_of_purchases_moonpie_stackers_heavy_no_previous_report() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		report.delete();
		testMachine.buy("B1");
		testReporter.updateReport(testMachine);
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		testMachine.buy("B1");
		testMachine.buy("A2");
		testReporter.updateReport(testMachine);
		testMachine = testReader.fillInventory(initialInventoryPath);
		testMachine.buy("B1");
		testMachine.buy("A2");
		testMachine.buy("C4");
		testReporter.updateReport(testMachine);
		String productName1 = "Moonpie";
		String productName2 = "Stackers";
		String productName3 = "Heavy";
		
		int expectedQuantity1 = 3;
		int expectedQuantity2 = 2;
		int expectedQuantity3 = 1;
		
		int expectedTotalSales = (expectedQuantity1 * 180) + 
				(expectedQuantity2 * 145) + (expectedQuantity3 * 150);
		
		int reportedQuantity1 = assertHelper(productName1);
		int reportedQuantity2 = assertHelper(productName2);
		int reportedQuantity3 = assertHelper(productName3);
		Assert.assertEquals(expectedQuantity1, reportedQuantity1);
		Assert.assertEquals(expectedQuantity2, reportedQuantity2);
		Assert.assertEquals(expectedQuantity3, reportedQuantity3);
		Assert.assertEquals(expectedTotalSales, assertSalesHelper());
		if (didExist = true) {
			backupHelper2(origReport);
		}
		
	}
	
	@Test
	public void format_against_test_file() {
		
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		report.delete();
		testMachine.buy("B1");
		testReporter.updateReport(testMachine);
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		testMachine.buy("B1");
		testMachine.buy("A2");
		testReporter.updateReport(testMachine);
		testMachine = testReader.fillInventory(initialInventoryPath);
		testMachine.buy("B1");
		testMachine.buy("A2");
		testMachine.buy("C4");
		testReporter.updateReport(testMachine);
		
		File testReport = new File("testFile.txt");
		List<String> generatedReportList = new ArrayList<String>();
		List<String> testReportList = new ArrayList<String>();
		try (Scanner reportReader = new Scanner(report)){
			while (reportReader.hasNextLine()) {
				generatedReportList.add(reportReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Scanner reportReader = new Scanner(testReport)){
			while (reportReader.hasNextLine()) {
				testReportList.add(reportReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(generatedReportList.equals(testReportList));
		
		
		if (didExist = true) {
			backupHelper2(origReport);
		}
	}
	
	@Test
	public void generated_report_is_same_as_old_report() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		Queue<String> genReport = new LinkedList<String>();
		String generatedPath = testReporter.generateReport(testMachine);
		File generatedReport = new File(generatedPath);
		try (Scanner reportReader = new Scanner(generatedReport)){
			while (reportReader.hasNextLine()) {
				genReport.offer(reportReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(origReport.equals(genReport));
		
	}
	
	@Test
	public void generated_report_is_same_as_report_made_in_test() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		
		report.delete();
		testMachine.buy("B1");
		testReporter.updateReport(testMachine);
		VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
		testMachine.buy("B1");
		testMachine.buy("A2");
		testReporter.updateReport(testMachine);
		testMachine = testReader.fillInventory(initialInventoryPath);
		testMachine.buy("B1");
		testMachine.buy("A2");
		testMachine.buy("C4");
		String generatedPath = testReporter.generateReport(testMachine);
		testReporter.updateReport(testMachine);
		
		Queue<String> newReport = new LinkedList<String>();
		try (Scanner reportReader = new Scanner(report)){
			while (reportReader.hasNextLine()) {
				newReport.offer(reportReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Queue<String> genReport = new LinkedList<String>();
		File generatedReport = new File(generatedPath);
		try (Scanner reportReader = new Scanner(generatedReport)){
			while (reportReader.hasNextLine()) {
				genReport.offer(reportReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(newReport.equals(genReport));
		
		if (didExist = true) {
			backupHelper2(origReport);
		}
	}
	
	@Test
	public void generated_report_has_correct_name() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		String generatedPath = testReporter.generateReport(testMachine);
		
		String[] reportNameArray = generatedPath.split("_");
		String dateReport = reportNameArray[0];
		String timeReport = reportNameArray[1];
		String shouldBeSRT = reportNameArray[2];
		String dateNow = (java.time.LocalDate.now()).toString();
		
		String[] timeReportArray = timeReport.split(":");
		int timeReportHour = Integer.parseInt(timeReportArray[0]);
		int localHour = java.time.LocalTime.now().getHour();
		int timeReportMinute = Integer.parseInt(timeReportArray[1]);
		int localMinute = java.time.LocalTime.now().getMinute();
		
		//seconds not tested due to excessive sensitivity of microseconds
		
		
		Assert.assertTrue(dateReport.equals(dateNow));
		Assert.assertTrue(timeReportHour == localHour);
		Assert.assertTrue(timeReportMinute == localMinute);
		Assert.assertTrue(shouldBeSRT.equals("SalesReport.txt"));
		
	}
	
	@Test
	public void generated_report_is_blank() {
		Queue<String> origReport = new LinkedList<String>();
		boolean didExist = false;
		if (report.exists()) {
			didExist = true;
			backupHelper1(origReport);
		}
		
		report.delete();
		String generatedPath = testReporter.generateReport(testMachine);
		
		Queue<String> genReport = new LinkedList<String>();
		File generatedReport = new File(generatedPath);
		try (Scanner reportReader = new Scanner(generatedReport)){
			while (reportReader.hasNextLine()) {
				genReport.offer(reportReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(genReport.isEmpty());
		
		if (didExist = true) {
			backupHelper2(origReport);
		}
		
	}
	

}
