package com.techelevator;

public class Chips extends Product {
	public Chips(String location, String name, int price, String type, int count) {
		super(location, name, price, type, count);
		
	}
	public String makeSound() {
		 return "Crunch Crunch, Yum!";
		
	}
}
