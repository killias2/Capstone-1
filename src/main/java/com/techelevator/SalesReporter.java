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
		
		try (Scanner reportReader = new Scanner(report); PrintWriter reportWriter = new PrintWriter(report)){
			while(reportReader.hasNextLine()) {
				String line = reportReader.nextLine();
				String[] arrayLine = line.split("\\|");
				if (reportUpdates.containsKey(arrayLine[0])){
					int newCount = Integer.parseInt(arrayLine[1]) + reportUpdates.get(arrayLine[0]);
					arrayLine[1] = String.valueOf(newCount);
					line = String.join("\\|", arrayLine);
					reportWriter.println(line);
					reportUpdates.remove(arrayLine[0]);
				}
				if (line.contains("$")) {
					double oldSales = Double.parseDouble(line.substring(1));
					int oldSalesInt = (int)(oldSales * 100);
					reportWriter.println(formatMoney(totalSales + oldSalesInt));
					checkTotalSales = true;
				}
			}
			
			if(!reportUpdates.isEmpty()) {
				try (PrintWriter logger = new PrintWriter(new FileWriter(report,true))){
				for(Map.Entry<String, Integer> item : reportUpdates.entrySet()) {
					reportWriter.println(item.getKey() + "\\|" + item.getValue());
				}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (checkTotalSales = false) {
				reportWriter.println(formatMoney(totalSales));
			}
			
			} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	private String formatMoney(int i) {
		int dollarAmt = i/ 100;
		int centsAmt = i % 100;
		if(centsAmt % 10 == 0)
		{
			return "$" + dollarAmt + "." + centsAmt + "0";
		}
		return "$" + dollarAmt + "." + centsAmt;
		}

}
