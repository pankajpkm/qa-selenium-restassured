package com.objevsoft.restassured;

import io.restassured.RestAssured;

/**
 * @author Pankaj.Kumar 
 * 
 * <p>Utility Class to extract API Response code to validate broken links</p>
 *
 */
public class ResponseCodeExtractor {

	public int getHTTPResponseCodeUsingGet(String url){
		return RestAssured.get(url).statusCode();
	}

}
