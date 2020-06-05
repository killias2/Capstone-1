package com.techelevator;

public class ChewingGum extends Product {


	public ChewingGum(String location, String name, int price, String type, int count) {
		super(location, name, price, type, count);
		
	}
	public String printSound() {
		 return "Chew Chew, Yum!";
		
	}
}
