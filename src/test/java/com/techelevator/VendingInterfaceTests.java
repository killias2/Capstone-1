package com.techelevator;


import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VendingInterfaceTests {
	
	@Test
	public void formatMoneyTests() {
		VendingInterface v = new VendingInterface();
		Assert.assertEquals("$3.50", v.formatMoney(350));
		Assert.assertEquals("$2.75", v.formatMoney(275));
		Assert.assertEquals("$3.05", v.formatMoney(305));
		Assert.assertEquals("$13.50", v.formatMoney(1350));
	}
	
	
}
