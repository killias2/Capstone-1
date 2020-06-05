package com.techelevator;

import java.util.List;
import java.util.Scanner;

public class VendingInterface {
	private int menuNum = 0;
	private Scanner userInput;
	private VendingMachine machine;
	private Logger purchaseLog;
	int balance = 0; //kept in cents
	private boolean keepVending = true;
	SalesReporter salesReport;
	public VendingInterface() {
		// TODO Auto-generated method stub
		FileReader machineReader = new FileReader();
		machine = machineReader.fillInventory();
		salesReport = new SalesReporter();
		purchaseLog = new Logger();
		purchaseLog.logClear();
		userInput = new Scanner(System.in);
	}
	public void vend() {
		while(keepVending) {
			showMenu(menuNum);
		}
	}
	private void stopVending() {
		keepVending = false;
		salesReport.updateReport(machine);
	}
	private void showMenu(int i) {
		if(i == 0) {
			mainMenu();
		}
		if(i == 1) {
			purchaseMenu();
		}
		if(i == 2) {
			productSelectMenu();
		}
		if(i == 3) {
			feedMoneyMenu();
		}
	}
	private void mainMenu() {
		System.out.println("(1) Display Vending Machine Items");
		System.out.println("(2) Purchase");
		System.out.println("(3) Exit");
		String input = userInput.nextLine();
		if(input.equals("1")) {
			List<Product> productList = machine.getInventory();
			for(Product p: productList) {
				System.out.println(p.toString());
			}
		}
		else if(input.equals("2")) {
			menuNum = 1;
		}
		else if(input.equals("3")) {
			stopVending();
		}
		else if(input.equals("4")){
			salesReport.generateReport(machine);
			System.out.println("Sales report generated!");		
		}
		else {
			System.out.println("Invalid input please try again!");
		}
	}
	private void purchaseMenu() {
		System.out.println("(1) Feed Money");
		System.out.println("(2) Select Product");
		System.out.println("(3) Finish Transaction");
		String input = userInput.nextLine();
		if(input.equals("1")) {
			menuNum = 3;
		}
		else if(input.equals("2")) {
			menuNum = 2;
		}
		else if(input.equals("3")) {
			menuNum = 0;
			if(balance > 0)
			{
				int[] change = makeChange();
				System.out.println("You have been given " + change[0] + " quarters, " + change[1] + " dimes, and " + change[2] + " nickels.");
				System.out.println("Current Balance: " + formatMoney(balance));
			}
		}
		else{
			System.out.println("Invalid input please try again!");
		}
		
	}
	private void feedMoneyMenu() { //completed
		System.out.println("Current Balance: " + formatMoney(balance));
		System.out.println("Please input bills to add more money (1, 2, 5, 10 are valid input types)");
		System.out.println("Enter 0 to return to previous menu after bills have been added");
		String input = userInput.nextLine();
		int numUserInput = Integer.parseInt(input);
		if(numUserInput == 0) {
			menuNum = 1;
		}
		else if(numUserInput == 1 || numUserInput == 2 || numUserInput == 5 || numUserInput == 10) {
			balance += numUserInput * 100;
			purchaseLog.logFeed(formatMoney(numUserInput * 100), formatMoney(balance));
			
		}
		else{
			System.out.println("Invalid input!");
		}
		
	}
	private void productSelectMenu() {
		List<Product> productList = machine.getInventory();
		for(Product p: productList) {
			System.out.println(p.toString());
		}
		System.out.println("Enter a number of a product to buy or 0 to go back!");
		String input = userInput.nextLine();
		input = input.toUpperCase();
		if(input.equals("0")){
			menuNum = 1;
		}
		else if (machine.productExists(input))
		{
			if(machine.getProduct(input).getCount() > 0) {
				if(machine.getProduct(input).getPrice() <= balance) {
					 dispense(machine.buy(input));
					
				}
				else {
					System.out.println("Not enough money!");
				}
			}
			else {
				System.out.println("Product is sold out!");
			}
		}
		else {
			System.out.println("Product with specified location doesn't exist!");
			
		}
		menuNum = 1;
	}
	private int[] makeChange() { //return format is [quarters, dimes, nickels]
		int[] rtn = new int[3];
		purchaseLog.logChange(formatMoney(balance));
		rtn[0] = balance / 25;
		balance = balance % 25;
		rtn[1] = balance / 10;
		balance = balance % 10;
		rtn[2] = balance / 5;
		balance = balance % 5;
		return rtn;
		
	
	}
	private void dispense(Product p) {
		int oldBalance = balance;
		balance -= p.getPrice();
		purchaseLog.logPurchase(p, formatMoney(oldBalance), formatMoney(balance));
		System.out.println("You have purchased a " + p.getName() + " for " + formatMoney(p.getPrice()) + ". Your remaining balance is: " + formatMoney(balance));
		System.out.println(p.makeSound());
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

}
