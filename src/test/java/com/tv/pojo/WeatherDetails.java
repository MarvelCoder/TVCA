package com.tv.pojo;

public class WeatherDetails {

	private String condition;

	private String wind;
	
	private String humidity;
	
	private double celsius;
	
	private double fahrenheit;
	
	public WeatherDetails(String condition, String wind, String humidity, double celsius, double fahrenheit) {
		super();
		this.condition = condition;
		this.wind = wind;
		this.humidity = humidity;
		this.celsius = celsius;
		this.fahrenheit = fahrenheit;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public double getCelsius() {
		return celsius;
	}

	public void setCelsius(double celsius) {
		this.celsius = celsius;
	}

	public double getFahrenheit() {
		return fahrenheit;
	}

	public void setFahrenheit(double fahrenheit) {
		this.fahrenheit = fahrenheit;
	}

	@Override
	public String toString() {
		return "Condition : " + condition + "\n Wind:" + wind + "\n Humidity: " + humidity + "\n Temp in Degrees:"
				+ celsius + "\n Temp in Fahrenheit:" + fahrenheit;
	}

}
