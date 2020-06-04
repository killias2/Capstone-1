package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Logger {
	
	File log = new File("Logger.txt");
	
	public void logClear() {
		try {
			log.delete();
			log.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logFeed(String moneyFed, String balance) {
		try (PrintWriter logger = new PrintWriter(new FileWriter(log,true))){
			
			logger.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now()
			+ " FEED MONEY: " + moneyFed + " " + balance);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void logPurchase(Product name, String oldBalance, String newBalance) {
		File log = new File("Logger.txt");
		
		try (PrintWriter logger = new PrintWriter(new FileWriter(log,true))){
			
			logger.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now() + 
					" " + name.getName() + " " + name.getLocation() + oldBalance + " " + newBalance);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logChange(String changeGiven) {
		File log = new File("Logger.txt");
		
		try (PrintWriter logger = new PrintWriter(new FileWriter(log,true))){
			
			logger.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now() + 
					" " + "GIVE CHANGE: " + changeGiven + " " + "$0.00");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}