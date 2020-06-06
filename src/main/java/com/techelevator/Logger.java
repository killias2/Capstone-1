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
	
	private String timeBuilder() {
		int hour = java.time.LocalTime.now().getHour();
		String amPM = "AM";
		if (hour >= 12) {
			hour -= 12;
			amPM = "PM";
		}
		
		int minute = java.time.LocalTime.now().getMinute();
		int second = java.time.LocalTime.now().getSecond();
		
		String hourString = String.valueOf(hour);
		String minString = String.valueOf(minute);
		String secString = String.valueOf(second);
		
		if (hour < 10) {
			hourString = "0" + hourString;
		}
		
		
		if (minute < 10) {
			minString = "0" + minString;
		}
		
		
		if (second < 10) {
			secString = "0" + secString;
		}
		
		String time = hourString + ":" + minString + ":" + secString + " " + amPM;
		return time;
	}
	
	private String dateBuilder() {
		String month = String.valueOf(java.time.LocalDate.now().getMonthValue());
		if (java.time.LocalDate.now().getMonthValue() < 10) {
			month = "0" + month;
		}
		String day = String.valueOf(java.time.LocalDate.now().getDayOfMonth());
		if (java.time.LocalDate.now().getDayOfMonth() < 10) {
			day = "0" + day;
		}
		String date = month + "/" + day + "/" + java.time.LocalDate.now().getYear();
		
		return date;
	}
	
	public void logFeed(String moneyFed, String balance) {
		try (PrintWriter logger = new PrintWriter(new FileWriter(log,true))){
			
			logger.println(dateBuilder() + " " + timeBuilder()
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
			
			logger.println(dateBuilder() + " " + timeBuilder() + 
					" " + name.getName() + " " + name.getLocation() + " " + oldBalance + 
					" " + newBalance);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logChange(String changeGiven) {
		File log = new File("Logger.txt");
		
		try (PrintWriter logger = new PrintWriter(new FileWriter(log,true))){
			
			logger.println(dateBuilder() + " " + timeBuilder() + 
					" " + "GIVE CHANGE: " + changeGiven + " " + "$0.00");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}