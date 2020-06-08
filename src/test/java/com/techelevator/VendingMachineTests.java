package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class VendingMachineTests {
	@Test
	public void inventoryTests() {
		VendingMachine a = new VendingMachine();
		VendingMachine b = new VendingMachine();
		a.add(new Chips("A2", "Lays", 105, "Chips", 5));
		Assert.assertEquals(1, a.getSize());
		Assert.assertEquals(0, b.getSize());
		Assert.assertEquals(new Chips("A2", "Lays", 105, "Chips", 5).makeSound(), a.getProduct("A2").makeSound());
		Assert.assertEquals(new Chips("A2", "Lays", 105, "Chips", 5).getPrice(), a.getProduct("A2").getPrice());
		a.add(new Candy("C3", "Heath", 205, "Candy", 5));
		Assert.assertEquals(2,  a.getSize());
		Assert.assertEquals(205, a.getProduct("C3").getPrice());
		Assert.assertEquals("Heath", a.buy("C3").getName());
		Assert.assertEquals(4, a.getProduct("C3").getCount());
	}

}
