package WeatherToPachube;

public class DayForecast {

	//<title>Monday: white cloud, Max Temp: 13°C (55°F), Min Temp: 10°C (50°F)</title>
	private String dayOverview;
	private Float latitude;
	private Float longitude;
	
	
	//<description>Max Temp: 13°C (55°F), Min Temp: 10°C (50°F), Wind Direction: NE, Wind Speed: 9mph, Visibility: very good, Pressure: 1021mb, Humidity: 76%, UV risk: low, Pollution: low, Sunrise: 07:04GMT, Sunset: 16:23GMT</description>
	private String dayDetail;
	
	public String getTitle(){
		return dayOverview;
	}
	
	public void setTitle(String overview){
		this.dayOverview = overview;
	}
	
	public void setLatitude(Float latt) {
		latitude = latt;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLongitude(Float lon) {
		longitude = lon;
	}
	
	public float getLongitude() {
		return longitude;
	}	
	
	public String getDescription(){
		return dayDetail;
	}
	
	public void setDescription(String detail){
		this.dayDetail = detail;
	}
	
	public int getMidTemp()
	{
		int minTemp;
		int maxTemp;

		//Saturday: sunny intervals, Max Temp: 5°C (41°F), Min Temp: 0°C (32°F)
		
		int startMaxPos = dayOverview.indexOf("Max Temp: ") + 10;
		int endMaxPos = dayOverview.indexOf("°C");

		maxTemp = Integer.parseInt(dayOverview.substring(startMaxPos, endMaxPos));
		
		int startMinPos = dayOverview.indexOf("Min Temp: ") + 10;
		int endMinPos = dayOverview.lastIndexOf("°C");

		minTemp = Integer.parseInt(dayOverview.substring(startMinPos, endMinPos));

		return (maxTemp + minTemp) / 2;
	}

}
