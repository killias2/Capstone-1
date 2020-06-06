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
	int totalSales = 0;
	int oldSalesInt = 0;
	
	private Map<String,Integer> generateMap(VendingMachine reportMachine) {
		Map<String,Integer> reportUpdates = new HashMap<String,Integer>();
		
		for (int i = 0; i < reportMachine.getSize(); i++) {
			if (reportMachine.getProduct(i).getCount() < 5) {
				reportUpdates.put( reportMachine.getProduct(i).getName(),( 5 - reportMachine.getProduct(i).getCount() ) );
				totalSales += (reportMachine.getProduct(i).getPrice() * (5 - reportMachine.getProduct(i).getCount() ));
			}
		}
		return reportUpdates;
	}
	
	private void writeReport1(File report, File updatedReport, Map<String,Integer> reportUpdates) {
		try (Scanner reportReader = new Scanner(report);
				PrintWriter reportWriter = new PrintWriter(updatedReport)){
			updatedReport.createNewFile();
			while(reportReader.hasNextLine()) {
				String line = reportReader.nextLine();
				String[] arrayLine = line.split("\\|");
				
				if (!reportUpdates.containsKey(arrayLine[0]) && (!line.contains("$")) && (!line.equals(""))) {
					reportWriter.println(line);
				}
				if (reportUpdates.containsKey(arrayLine[0])){
					int newCount = Integer.parseInt(arrayLine[1]) + reportUpdates.get(arrayLine[0]);
					arrayLine[1] = String.valueOf(newCount);
					line = String.join("|", arrayLine);
					reportUpdates.remove(arrayLine[0]);
					reportWriter.println(line);
				}
				
				if (line.contains("$")) {
					String oldSalesNewString = line.substring(1,line.length() - 3) + line.substring(line.length() - 2);
					int oldSalesInt = Integer.parseInt(oldSalesNewString);
					totalSales += oldSalesInt;
					line = formatMoney(totalSales);
					if (reportUpdates.isEmpty()) {
						reportWriter.println();
						reportWriter.println(line);
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		
	private void writeReport2(File report, File updatedReport, Map<String,Integer> reportUpdates) {	
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
				
				reportWriter.println();
				reportWriter.println(formatMoney(totalSales + oldSalesInt));
				totalSales = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateReport(VendingMachine reportMachine) {
		File updatedReport = new File("SalesReportTemp.txt");
		Map<String,Integer> reportUpdates = generateMap(reportMachine);
		
		try {
			if (!report.exists()) {
				report.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		writeReport1(report,updatedReport,reportUpdates);
		writeReport2(report,updatedReport,reportUpdates);
		
	}
		
	private String formatMoney(int i) {
		int dollarAmt = i/ 100;
		int centsAmt = i % 100;
		if(centsAmt == 0)
		{
			return "$" + dollarAmt + "." + centsAmt + "0";
		}
		else if(centsAmt < 10) {
			return "$" + dollarAmt + "." + "0" + centsAmt;
		}
		return "$" + dollarAmt + "." + centsAmt;
	}
	
	public String generateReport(VendingMachine reportMachine) {
		try {
			if (!report.exists()) {
				report.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String generatedPath = java.time.LocalDate.now() + "_" + java.time.LocalTime.now()  + "_SalesReport.txt";
		File generatedReport = new File(generatedPath);
		Map<String,Integer> reportUpdates = generateMap(reportMachine);
		
		writeReport1(report, generatedReport,reportUpdates);
		
		if(!reportUpdates.isEmpty()) {
			
			try (PrintWriter reportWriter = new PrintWriter(new FileWriter(generatedReport,true))){
				
				for(Map.Entry<String, Integer> item : reportUpdates.entrySet()) {
					reportWriter.println(item.getKey() + "|" + item.getValue());
				}
			
				reportWriter.println();
				reportWriter.println(formatMoney(totalSales + oldSalesInt));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		totalSales = 0;
		oldSalesInt = 0;
		return generatedPath;
		
	}

}
