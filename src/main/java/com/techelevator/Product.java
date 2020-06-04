package com.techelevator;

public class Product {
	
	private String location;
	private String name;
	private double price;
	private String type;
	
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
	
	public Product(String location, String name, double price, String type) {
		this.location = location;
		this.name = name;
		this.price = price;
		this.type = type;
	}
	

}
