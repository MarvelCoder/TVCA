package com.tv.factory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



/**
* Factory class for driver lifecycle
*
* @author Harsh Sengar
*/

public class DriverFactory {
	
	private WebDriver driver = null;
	public static Properties prop = new Properties();
	
	public static long DURATION;
	public static String TIMEUNIT;
	public static String BROWSER;

	/**
	 * Static block to initialize runtime parameters
	 *
	 */
	static {
		try {
			
			prop.load(DriverFactory.class.getClassLoader().getResourceAsStream("config.properties"));
			DURATION = Long.parseLong(prop.getProperty("implicitwaitduration"));
			TIMEUNIT = prop.getProperty("implicaitwaitunit");
			BROWSER = prop.getProperty("browser");
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Factory method for getting browsers
	 *
	 * @return WebDriver
	 */
	public WebDriver getDriver() {
	
		switch (BROWSER.toLowerCase()) {
			
			case "firefox":
				if (driver == null) {
				  System.setProperty("webdriver.gecko.driver", prop.getProperty("firefoxdriverpath"));		
				  driver = new FirefoxDriver();
				  setImplicitWait(driver);
				  setSize(driver);
				}
				break;
				
			case "ie":
				if (driver == null) {
					System.setProperty("webdriver.ie.driver",prop.getProperty("iedriverpath"));
					driver = new InternetExplorerDriver();
					setImplicitWait(driver);
					setSize(driver);
				}
				break;
				
			case "chrome":
				if (driver == null) {
					System.setProperty("webdriver.chrome.driver",prop.getProperty("chromedriverpath"));
					driver = new ChromeDriver();
					setImplicitWait(driver);
					setSize(driver);
				}
				break;
				
			}
		return driver;
	}

	/**
	 * Set implicit wait for driver session
	 *
	 * @param driver
	 */
	public void setImplicitWait(WebDriver driver) {
		
		switch (TIMEUNIT) {
		
			case "nanoseconds":
				driver.manage().timeouts().implicitlyWait(DURATION, TimeUnit.NANOSECONDS);
				break;
				
			case "milliseconds":
				driver.manage().timeouts().implicitlyWait(DURATION, TimeUnit.MILLISECONDS);
				break;
					
			case "seconds":
				driver.manage().timeouts().implicitlyWait(DURATION, TimeUnit.SECONDS);
				break;
				
			case "minutes":
				driver.manage().timeouts().implicitlyWait(DURATION, TimeUnit.MINUTES);
				break;	

			case "hours":
				driver.manage().timeouts().implicitlyWait(DURATION, TimeUnit.HOURS);
				break;	
		}
	}

	/**
	 * Set window size for current driver instance
	 *
	 * @param driver
	 */
	public void setSize(WebDriver driver) {

		try {
			driver.manage().window().setSize(new Dimension(1920,1080));
		}
		catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Closes current browser instance
	 *
	 * @param driver
	 */
	public void closeBrowser(WebDriver driver) {

		try {
			driver.close();
		}
		catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Closes current driver instance
	 *
	 * @param driver
	 */
	public void quitDriver(WebDriver driver) {

		try {
			driver.quit();
		}
		catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
