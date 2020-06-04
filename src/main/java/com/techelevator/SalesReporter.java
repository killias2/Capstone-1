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
	
	public void updateReport(VendingMachine reportMachine) {
		File report = new File("SalesReport.txt");
		File updatedReport = new File("SalesReportTemp.txt");
		
		try {
			if (!report.exists()) {
				report.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalSales = 0;
		Map<String,Integer> reportUpdates = new HashMap<String,Integer>();
		
		for (int i = 0; i < reportMachine.getSize(); i++) {
			if (reportMachine.getProduct(i).getCount() < 5) {
				reportUpdates.put( reportMachine.getProduct(i).getName(),( 5 - reportMachine.getProduct(i).getCount() ) );
				totalSales += (reportMachine.getProduct(i).getPrice() * (5 - reportMachine.getProduct(i).getCount() )) * 100;
			}
		}
		
		boolean checkTotalSales = false;
		
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
				}
				
				if (line.contains("$")) {
					double oldSales = Double.parseDouble(line.substring(1));
					int oldSalesInt = (int)(oldSales * 100);
					line = formatMoney(totalSales + oldSalesInt);
					checkTotalSales = true;
				}
				
				reportWriter.println(line);
				
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
			
			updatedReport.delete();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(!reportUpdates.isEmpty() || checkTotalSales == false) {
			
			try (PrintWriter reportWriter = new PrintWriter(new FileWriter(report,true))){
				
				for(Map.Entry<String, Integer> item : reportUpdates.entrySet()) {
					reportWriter.println(item.getKey() + "|" + item.getValue());
				}
				
				if (checkTotalSales == false) {
				reportWriter.println(formatMoney(totalSales));
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

}
