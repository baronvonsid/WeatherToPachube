package WeatherToPachube;

import java.io.IOException; 
import org.apache.commons.digester3.Digester; 
import org.apache.commons.digester3.binder.DigesterLoader; 
import org.xml.sax.SAXException; 

public class GetBBCWeather {

	final String url = "http://news.bbc.co.uk/weather/forecast/8/Next3DaysRSS.rss";

	public WeatherClass getForecast() throws SAXException, IOException { 
		WeatherClass result = null; 
		DigesterLoader digesterLoader = DigesterLoader.newLoader(new WeatherRules()); 
		Digester digester = digesterLoader.newDigester(); 
		
		result = digester.parse(url);
		return result; 
	} 
	
	
}




