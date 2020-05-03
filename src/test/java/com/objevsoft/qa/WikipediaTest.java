package com.objevsoft.qa;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.objevsoft.constants.Constants;
import com.objevsoft.restassured.ResponseCodeExtractor;
import com.objevsoft.selenium.ElementFinder;
import com.objevsoft.utils.CustomeDataProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class verifies elements on the wikipedia homepage.
 */
public class WikipediaTest {

    private Logger log = LoggerFactory.getLogger(WikipediaTest.class);

    private WebDriver driver;
    private ElementFinder finder;
    private int itrCountText;
    private int itrCountLinks;
    
    @BeforeClass
    public void setup() {

        /*
            WebDriverManager.chromedriver().setup() should automatically use the right
             driver for your Chrome version.  If it does not, you can choose a version manually
            see https://sites.google.com/a/chromium.org/chromedriver/downloads
            and update it as needed.

            WebDriverManager.chromedriver().version("74.0.3729.6").setup();
        */

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        finder = new ElementFinder(driver);

        // adjust timeout as needed
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://www.wikipedia.org/");
    }

    @Test
    public void sloganPresent() {

        String sloganClass = "localized-slogan";
        WebElement slogan = finder.findElement(By.className(sloganClass));

        Assert.assertNotNull(slogan, String.format("Unable to find slogan div by class: %s", sloganClass));

        log.info("Slogan text is {}", slogan.getText());

        Assert.assertEquals(slogan.getText(), "The Free Encyclopedia");
    }

    @Test(dataProviderClass=CustomeDataProvider.class, dataProvider=Constants.COUNTRIES_LISTS_DP)
    public void countriesNamePresentTest(String countries){
    	String countriesXpath="//div[@class='central-featured']//a/strong";
    	
    	List<WebElement> countriesElements = finder.findElements(By.xpath(countriesXpath));
    	
    	Assert.assertNotNull(countriesElements, String.format("Unable to find anchor>strong tag by xpath: %s", countriesXpath));
    	
    	log.info("Link text is {}", countriesElements.get(itrCountText).getText());
        
        Assert.assertEquals(countriesElements.get(itrCountText).getText(), countries);
        itrCountText++;
    }
    
    @Test(dataProviderClass=CustomeDataProvider.class, dataProvider=Constants.COUNTRIES_LISTS_DP)
    public void httpResponseCode_CountriesLinksTest(String countries){
    	String linkClass="link-box";
    	
    	List<WebElement> countriesLinks = finder.findElements(By.className(linkClass));
    	
    	Assert.assertNotNull(countriesLinks, String.format("Unable to find countries links by class: %s", linkClass));
    	
    	String href=countriesLinks.get(itrCountLinks).getAttribute("href");
		log.info("Link href attribute is {}", href);
        int statusCode=new ResponseCodeExtractor().getHTTPResponseCodeUsingGet(href);
        log.info("HTTP Response code is {}", statusCode);
        Assert.assertEquals(statusCode, 200);
        itrCountLinks++;
    }
        
    @AfterClass
    public void closeBrowser() {

        if(driver!=null) {
            driver.close();
        }
    }
}
