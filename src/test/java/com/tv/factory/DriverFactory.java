package com.tv.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverFactory {

	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	
	public static Properties prop = new Properties();

	/**
	 * Factory method for getting browsers
	 *
	 * @return WebDriver
	 */
	public static WebDriver getBrowser() {
		WebDriver driver = null;
	
		switch (prop.getProperty("browser")) {
			
			case "Firefox":
				driver = drivers.get("Firefox");
				if (driver == null) {
				  System.setProperty("webdriver.firefox.driver", prop.getProperty("firefoxdriverpath"));		
				  driver = new FirefoxDriver();
				  drivers.put("Firefox", driver);
				}
				break;
			case "IE":
				driver = drivers.get("IE");
				if (driver == null) {
					System.setProperty("webdriver.ie.driver",prop.getProperty("iedriverpath"));
					driver = new InternetExplorerDriver();
					drivers.put("IE", driver);
				}
				break;
			case "Chrome":
				driver = drivers.get("Chrome");
				if (driver == null) {
					System.setProperty("webdriver.chrome.driver",prop.getProperty("chromedriverpath"));
					driver = new ChromeDriver();
					drivers.put("Chrome", driver);
				}
				break;
				
			}
		return driver;
	}

	public static void closeAllDriver() {
		for (String key : drivers.keySet()) {
			drivers.get(key).close();
			drivers.get(key).quit();
		}
	}

}
