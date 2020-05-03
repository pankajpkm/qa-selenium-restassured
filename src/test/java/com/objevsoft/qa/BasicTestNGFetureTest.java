package com.objevsoft.qa;

import org.testng.annotations.Test;

public class BasicTestNGFetureTest {

	@Test(groups={"TestA"})
	public void testMethod1(){
		System.out.println("Group Test A");
	}
	
	@Test(groups={"TestB","TestC"})
	public void testMethod2(){
		System.out.println("Grout Test B,C");
	}
	@Test(dependsOnGroups={"TestA"}, groups={"TestD"})
	public void testMethod3(){
		System.out.println("Depends on group A, group D");
	}
	
	@Test(enabled=false)
	public void testMethod4(){
		System.out.println("Included in TestNg but disabled in test");
	}
}
