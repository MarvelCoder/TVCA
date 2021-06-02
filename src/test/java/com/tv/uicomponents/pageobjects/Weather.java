package com.tv.uicomponents.pageobjects;

import java.time.Duration;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tv.pojo.WeatherDetails;


/**
* Page class for weather webpage
*
* @author Harsh Sengar
*/

public class Weather {
	
	private WebDriver driver;

	@CacheLookup
	@FindBy(id = "searchBox")
	private WebElement searchInput;
	
	@CacheLookup
	@FindBy(xpath = "//input[@class='defaultChecked']")
	private List<WebElement> defaultCityCheckbox;
	
	@CacheLookup
	@FindBy(xpath = "//div[@class='message' and not(@style)]//input")
	private WebElement cityCheckbox;
	
	@CacheLookup
	@FindBy(xpath = "//div[@class='outerContainer']")
	private List<WebElement> cityWeatherOuterContainer;
		
	@CacheLookup
	@FindBy(xpath = "//div[@class='leaflet-popup-content']//b")
	private List<WebElement> cityWeatherInfo;
	
	public Weather(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Search and select city required
	 *
	 * @return void
	 * @throws ElementNotInteractableException
	 * @throws WebDriverException
	 */
	public void searchAndSelectCity(String city){
		
		try {
			deSelectCities();
			isElementClickable(searchInput).sendKeys(city);
			isElementClickable(cityCheckbox).click();
			
		}catch(ElementNotInteractableException e) {
			throw new ElementNotInteractableException("Input field is not interactable.");
		}catch(WebDriverException e) {
			throw new WebDriverException("WebDriver Exception has occured.");
		}
	}
	
	/**
	 * Get city temperature i.e. celsius and fahrenheit
	 *
	 * @return double[]
	 * @throws ElementNotVisibleException
	 * @throws WebDriverException
	 */
	public double[] getCityTemperature(String city) {
		
		double[] dbl = null;
		
		try {
			
			for(WebElement el:cityWeatherOuterContainer) {
				if(el.getAttribute("title").equalsIgnoreCase(city)) {
					dbl = getCelsiusAndFahrenheit(el.findElements(By.xpath("div/span")));
				}
					
			}
			
		}catch(ElementNotVisibleException e) {
			throw new ElementNotVisibleException("Temperature is not visible.");
		}catch(WebDriverException e) {
			throw new WebDriverException("WebDriver issue.");
		}
		
		return dbl;
	}
	
	/**
	 * Get city weather
	 *
	 * @return WeatherDetails
	 * @throws ElementNotVisibleException
	 * @throws WebDriverException
	 */
	public WeatherDetails getWeather(String city) {
		
		WeatherDetails objWeatherDetails = null;
		
		try {
			for(WebElement el:cityWeatherOuterContainer) {
				if(el.getAttribute("title").equalsIgnoreCase(city)) {
					isElementClickable(el).click();
					break;
				}
					
			}

			objWeatherDetails = new WeatherDetails(isElementVisible(cityWeatherInfo.get(0)).getText().split(".*:\\s")[1],
													isElementVisible(cityWeatherInfo.get(1)).getText().split(".*:\\s")[1],
													isElementVisible(cityWeatherInfo.get(2)).getText().split(".*:\\s")[1],
													Double.parseDouble(isElementVisible(cityWeatherInfo.get(3)).getText().split(".*:\\s")[1]),
													Double.parseDouble(isElementVisible(cityWeatherInfo.get(4)).getText().split(".*:\\s")[1])
																);
			
		}catch(ElementNotVisibleException e) {
			throw new ElementNotVisibleException("Weather info is not visible.");
		}catch(WebDriverException e) {
			throw new WebDriverException("WebDriver issue.");
		}
		
		return objWeatherDetails;
	}
	
	/**
	 * Check if element is clickable
	 *
	 * @return WebElement
	 */
	private WebElement isElementClickable(WebElement el) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		return wait.until(ExpectedConditions.elementToBeClickable(el));
		
	}
	
	/**
	 * Check if element is visible
	 *
	 * @return WebElement
	 */
	private WebElement isElementVisible(WebElement el) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		return wait.until(ExpectedConditions.visibilityOf(el));
		
	}
	
	/**
	 * Get celsius and fahrenheit from WebElement
	 *
	 * @return double[]
	 */
	private double[] getCelsiusAndFahrenheit(List<WebElement> element) {
		
		double db[] = new double[2];
		
		int i=0;
		
		for(WebElement el:element) {
			db[i] = Double.parseDouble(el.getText().replaceAll("[°C°F].*", ""));
			i++;
		}
		
		return db;
	}
	
	/**
	 * Uncheck already selected cities
	 *
	 * @return void
	 */
	private void deSelectCities() {
		
		if(defaultCityCheckbox!=null) {
			for(WebElement el:defaultCityCheckbox) {
				el.click();
			}			
		}else {
			throw new NullPointerException("WebElement not initiliazed");
		}
		
	}
}
