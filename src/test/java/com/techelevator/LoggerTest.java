package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LoggerTest {
	
	File log = new File("Logger.txt");
	FileReader testReader = new FileReader();
	String initialInventoryPath = "vendingmachine.csv";
	VendingMachine testMachine = testReader.fillInventory(initialInventoryPath);
	Logger testLogger = new Logger();
	
	@Before
	public void setup() {
		testLogger.logClear();
	}
	
	
	@Test
	public void log_clear_creates_file() {
		Assert.assertTrue(log.exists());
		Assert.assertTrue(log.isFile());
	}
	
	@Test
	public void log_clear_creates_empty_file() {
		try (Scanner logReader = new Scanner(log)){
			Assert.assertTrue(!logReader.hasNextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void log_feed_creates_line_with_correct_info() {
		String moneyFed = "$1.00";
		String balance = "$1.00";
		testLogger.logFeed(moneyFed, balance);
		try (Scanner logReader = new Scanner(log)){
			String line = logReader.nextLine();
			String[] lineArray = line.split(" ");
			Assert.assertTrue(lineArray[5].equals(moneyFed));
			Assert.assertTrue(lineArray[6].equals(balance));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void log_purchase_creates_line_with_correct_info() {
		Product testProduct = new Product("B2","Cowtales",150,"Candy",5);
		String oldBalance = "$10.00";
		String newBalance = "$8.50";
		testLogger.logPurchase(testProduct, oldBalance, newBalance);
		try (Scanner logReader = new Scanner(log)){
			String line = logReader.nextLine();
			String[] lineArray = line.split(" ");
			Assert.assertTrue(lineArray[3].equals("Cowtales"));
			Assert.assertTrue(lineArray[4].equals("B2"));
			Assert.assertTrue(lineArray[5].equals(oldBalance));
			Assert.assertTrue(lineArray[6].equals(newBalance));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void log_change_creates_line_with_correct_info() {
		String changeGiven = "$10.00";
		testLogger.logChange(changeGiven);
		try (Scanner logReader = new Scanner(log)){
			String line = logReader.nextLine();
			String[] lineArray = line.split(" ");
			Assert.assertTrue(lineArray[5].equals(changeGiven));
			Assert.assertTrue(lineArray[6].equals("$0.00"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void stream_of_transactions_logged_correctly() {
		String month = String.valueOf(java.time.LocalDate.now().getMonthValue());
		if (java.time.LocalDate.now().getMonthValue() < 10) {
			month = "0" + month;
		}
		String day = String.valueOf(java.time.LocalDate.now().getDayOfMonth());
		if (java.time.LocalDate.now().getDayOfMonth() < 10) {
			day = "0" + day;
		}
		String date = month + "/" + day + "/" + java.time.LocalDate.now().getYear();
		String moneyFed1 = "$10.00";
		String balance1 = "$10.00";
		testLogger.logFeed(moneyFed1, balance1);
		String moneyFed2 = "$5.00";
		String balance2 = "$15.00";
		testLogger.logFeed(moneyFed2, balance2);
		Product testProduct1 = new Product("B2","Cowtales",150,"Candy",5);
		String balance3 = "$13.50";
		testLogger.logPurchase(testProduct1, balance2, balance3);
		testLogger.logChange(balance3);
		String moneyFed3 = "$2.00";
		String balance4 = "$2.00";
		testLogger.logFeed(moneyFed3, balance4);
		Product testProduct2 = new Product("C1","Cola",125,"Drink",5);
		String balance5 = "$0.75";
		testLogger.logPurchase(testProduct2, balance4, balance5);
		testLogger.logChange(balance5);
		
		try (Scanner logReader = new Scanner(log)){
			List<String[]> lineArrayList = new ArrayList<String[]>();
			while (logReader.hasNextLine()) {
				String line = logReader.nextLine();
				String[] lineArray = line.split(" ");
				lineArrayList.add(lineArray);
			}
			Assert.assertTrue(lineArrayList.get(0)[0].equals(date));
			Assert.assertTrue(lineArrayList.get(0)[5].equals("$10.00"));
			Assert.assertTrue(lineArrayList.get(0)[6].equals("$10.00"));
			Assert.assertTrue(lineArrayList.get(1)[5].equals("$5.00"));
			Assert.assertTrue(lineArrayList.get(1)[6].equals("$15.00"));
			Assert.assertTrue(lineArrayList.get(2)[5].equals("$15.00"));
			Assert.assertTrue(lineArrayList.get(2)[6].equals("$13.50"));
			Assert.assertTrue(lineArrayList.get(2)[3].equals("Cowtales"));
			Assert.assertTrue(lineArrayList.get(3)[5].equals("$13.50"));
			Assert.assertTrue(lineArrayList.get(3)[6].equals("$0.00"));
			Assert.assertTrue(lineArrayList.get(3)[4].equals("CHANGE:"));
			Assert.assertTrue(lineArrayList.get(4)[5].equals("$2.00"));
			Assert.assertTrue(lineArrayList.get(4)[6].equals("$2.00"));
			Assert.assertTrue(lineArrayList.get(5)[5].equals("$2.00"));
			Assert.assertTrue(lineArrayList.get(5)[6].equals("$0.75"));
			Assert.assertTrue(lineArrayList.get(5)[3].equals("Cola"));
			Assert.assertTrue(lineArrayList.get(6)[5].equals("$0.75"));
			Assert.assertTrue(lineArrayList.get(6)[6].equals("$0.00"));
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}


}
