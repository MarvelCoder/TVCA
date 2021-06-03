package com.tv.uicomponents.tests;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.tv.factory.DriverFactory;
import com.tv.uicomponents.pageobjects.Weather;

public class ComparisionTest {

	private WebDriver driver;
	private DriverFactory driverFactory;
	public  Properties prop = new Properties();
	private Weather objWeather;
	
	public String URL;
	
	@BeforeTest
	public void setup() {
		
		try {
			prop.load(ComparisionTest.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		URL = prop.getProperty("URL");
		
		driverFactory = new DriverFactory();
		driver = driverFactory.getDriver();
	}

	@BeforeClass
	public void openBrowser() {
	   
		driver.get(URL);
	}
  
	@BeforeMethod
	public void init() {
	   
		objWeather = PageFactory.initElements(driver, Weather.class);
	}
	
	@Test
	@Parameters({"pageTitle"})
	public void verifyWeatherPage(String title) {
   
		objWeather.verifyPageTitle(title);
	}

	@Test(dependsOnMethods = { "verifyWeatherPage" })
    @Parameters({"city"})
	public void setCity(String city) {
	
		 objWeather.searchAndSelectCity(city);
	 }

	@Test(dependsOnMethods = { "verifyWeatherPage" })
	@Parameters({"city"})
	public void verifyCityTemperature(String city) {
	
		 objWeather.verifyCityTemperature(city);
	 }

	@Test(dependsOnMethods = { "verifyWeatherPage" })
	@Parameters({"city"})
	public void verifyDetailedWeather(String city) {
		 
		 System.out.println(city+" \n"+objWeather.getWeather(city).toString());
	 }
	
	@AfterMethod
	public void destroy() {
		   
		objWeather = null;
	}
	
	@AfterClass
	public void closeBrowser() {
		
		driverFactory.closeBrowser(driver);
	}
	
	
	@AfterTest
	public void teardown() {
		
		driverFactory.quitDriver(driver);
	}
}
