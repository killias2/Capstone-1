package com.techelevator;

public class Drinks extends Product{
	public Drinks(String location, String name, int price, String type, int count) {
		super(location, name, price, type, count);
		
	}
	public String makeSound() {
		 return "Glug Glug, Yum!";
		
	}
}
