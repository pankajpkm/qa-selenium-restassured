package com.objevsoft.utils;

import org.testng.annotations.DataProvider;

/**
 * @author Pankaj.Kumar 
 * 
 * <p>Utility Class to implement custom data providers</p>
 *
 */
public class CustomeDataProvider {

	@DataProvider(name="countryListTestData")
	public String[] getCountryTestData(){		
		return new String[]{"English","Español","日本語","Русский","Deutsch","Français","Italiano","中文","Português","Polski"};
	}
}
