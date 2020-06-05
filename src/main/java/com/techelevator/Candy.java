package com.techelevator;

public class Candy extends Product{
	public Candy(String location, String name, int price, String type, int count) {
		super(location, name, price, type, count);
		
	}
	public String makeSound() {
		 return "Munch Munch, Yum!";
		
	}
}
