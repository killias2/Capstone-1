package com.techelevator;

public class Product {
	
	private String location;
	private String name;
	private int price;
	private String type;
	private int count;
	
	
	public Product(String location, String name, int price, String type, int count) {
		this.location = location;
		this.name = name;
		this.price = price;
		this.type = type;
		this.count = count;
	}
	
	public String getLocation() {
		return location;
	}
	public String getName() {
		return name;
	}
	public int getPrice() {
		return price;
	}
	public String getType() {
		return type;
	}
	public int getCount() {
		return count;
	}
	public void buy() {
		count --;
	}
	public void setCount(int count) {
		this.count = count;
		
	}
	public String toString() {
		if(this.count == 0) {
			return this.location + ") " + this.name + " SOLD OUT";
		}
		return this.location + ") " + this.name + " | PRICE: " + formatMoney(price)  + " | QTY: " + this.count;
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
	
	public String makeSound() {
		return "";
	}
	

}
