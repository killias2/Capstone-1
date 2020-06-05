package com.techelevator;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
	
	private List<Product> inventory = new ArrayList<Product>();

	public List<Product> getInventory() {
		return inventory;
	}
	
	public List<Product> add(Product product){
		inventory.add(product);
		return inventory;
	}
	
	public Product getProduct(int location) {
		return inventory.get(location);
	}
	
	public int getSize() {
		return inventory.size();
	}
	
	
	
	

}
