package com.techelevator;

public class Product {
	
	private String location;
	private String name;
	private double price;
	private String type;
	private int count;
	
	
	public Product(String location, String name, double price, String type, int count) {
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
	public double getPrice() {
		return price;
	}
	public String getType() {
		return type;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
		
	}
	public String toString() {
		
		
	}
	
	

}
