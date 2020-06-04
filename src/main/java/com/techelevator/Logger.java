package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Logger {
	
	public void logClear() {
		File log = new File("Logger.txt");
		try {
			log.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logFeed(String moneyFed, String balance) {
		File log = new File("Logger.txt");
		
		try (Scanner reader = new Scanner(log); PrintWriter logger = new PrintWriter(log)){
			while (reader.hasNext()) {
			}
			logger.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now() + " FEED MONEY: " + moneyFed + " " + balance);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	public void logPurchase(Product name, String oldBalance, String newBalance) {
//		File log = new File("Logger.txt");
//		
//		try (Scanner reader = new Scanner(log); PrintWriter logger = new PrintWriter(log)){
//			while (reader.hasNext()) {
//			}
//			logger.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now() + " " + name.getName() + " " + name.getLocation() + )
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void logChange(String balance) {
//		File log = new File("Logger.txt");
//		
//		try (Scanner reader = new Scanner(log); PrintWriter logger = new PrintWriter(log)){
//			while (reader.hasNext()) {
//			}
//			logger.log()
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

}



//if (!log.exists()){
//	try {
//		log.createNewFile();
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	}