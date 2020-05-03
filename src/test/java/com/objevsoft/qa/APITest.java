package com.objevsoft.qa;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class APITest {
	
	RequestSpecBuilder reqSpecBuilder;
	RequestSpecification reqSpec;
	
	@BeforeClass
	public void init(){
		reqSpecBuilder= new RequestSpecBuilder();
		reqSpecBuilder.setBaseUri("http://www.mycookmaster.com/");
		reqSpecBuilder.setBasePath("/service");
		reqSpecBuilder.addQueryParam("grp", "Cook");
		reqSpec = reqSpecBuilder.build();
	}
	
	
	@Test
	public void googleSearchAPITest(){
		int statuscode = given()
		.spec(reqSpec)
		.when()
		.get("/search")
		.then()
		.extract().statusCode();
		
		assertThat("Status Code Should be 200", statuscode, equalTo(200));
		
	}
}
