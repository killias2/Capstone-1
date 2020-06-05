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
	
	public Product getProduct(String location) {
		for(Product p: inventory) {
			if(p.getLocation().equals(location)) {
				return p;
			}
		}
		return null;
	}
	public Product getProduct(int location) {
		return inventory.get(location);
	}
	public int getSize() {
		return inventory.size();
	}
	
	public Product buy(String location) {
		for(Product p: inventory) {
			if(p.getLocation().equals(location)) {
				p.setCount(p.getCount()-1);
				return p;
			}
		}
		return null;
		
	}
	

	
	public boolean productExists(String location) {
		for(Product p: inventory) {
			if(p.getLocation().equals(location)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	

}
