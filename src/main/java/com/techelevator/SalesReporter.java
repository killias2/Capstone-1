package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SalesReporter {
	File report = new File("SalesReport.txt");
	
	public void updateReport(VendingMachine reportMachine) {
		File updatedReport = new File("SalesReportTemp.txt");
		
		try {
			if (!report.exists()) {
				report.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalSales = 0;
		int oldSalesInt = 0;
		Map<String,Integer> reportUpdates = new HashMap<String,Integer>();
		
		for (int i = 0; i < reportMachine.getSize(); i++) {
			if (reportMachine.getProduct(i).getCount() < 5) {
				reportUpdates.put( reportMachine.getProduct(i).getName(),( 5 - reportMachine.getProduct(i).getCount() ) );
				totalSales += (reportMachine.getProduct(i).getPrice() * (5 - reportMachine.getProduct(i).getCount() ));
			}
		}
		
		boolean moveSales = false;
		
		try (Scanner reportReader = new Scanner(report);
				PrintWriter reportWriter = new PrintWriter(updatedReport)){
			updatedReport.createNewFile();
			while(reportReader.hasNextLine()) {
				String line = reportReader.nextLine();
				String[] arrayLine = line.split("\\|");
				
				if (reportUpdates.containsKey(arrayLine[0])){
					int newCount = Integer.parseInt(arrayLine[1]) + reportUpdates.get(arrayLine[0]);
					arrayLine[1] = String.valueOf(newCount);
					line = String.join("|", arrayLine);
					reportUpdates.remove(arrayLine[0]);
					reportWriter.println(line);
				}
				
				if (line.contains("$")) {
					double oldSales = Double.parseDouble(line.substring(1));
					oldSalesInt = (int)(oldSales * 100);
					line = formatMoney(totalSales + (oldSalesInt));
					if (reportUpdates.isEmpty()) {
						reportWriter.println(line);
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		try (Scanner reportReader = new Scanner(updatedReport);
				PrintWriter reportWriter = new PrintWriter(report)){
			while(reportReader.hasNextLine()) {
				String line = reportReader.nextLine();
				reportWriter.println(line);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			updatedReport.delete();
		}
		
		if(!reportUpdates.isEmpty()) {
			
			try (PrintWriter reportWriter = new PrintWriter(new FileWriter(report,true))){
				
				for(Map.Entry<String, Integer> item : reportUpdates.entrySet()) {
					reportWriter.println(item.getKey() + "|" + item.getValue());
				}
				
				if (moveSales == false) {
					reportWriter.println();
					reportWriter.println(formatMoney(totalSales + oldSalesInt));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
		
	private String formatMoney(int i) {
		int dollarAmt = i/ 100;
		int centsAmt = i % 100;
		if(centsAmt == 0)
		{
			return "$" + dollarAmt + "." + centsAmt + "0";
		}
		return "$" + dollarAmt + "." + centsAmt;
		}
	
	public void generateReport() {
		String generatedPath = java.time.LocalDate.now() + "_" + java.time.LocalTime.now()  + "_SalesReport.txt";
		File generatedReport = new File(generatedPath);
		
		try (Scanner reportReader = new Scanner(report);
				PrintWriter reportWriter = new PrintWriter(generatedReport)){
			while(reportReader.hasNextLine()) {
				String line = reportReader.nextLine();
				reportWriter.println(line);
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
