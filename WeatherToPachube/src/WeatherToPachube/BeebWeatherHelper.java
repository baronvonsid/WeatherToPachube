package WeatherToPachube;

import java.io.IOException; 
import org.apache.commons.digester3.Digester; 
import org.apache.commons.digester3.binder.DigesterLoader; 
import org.xml.sax.SAXException; 

public class BeebWeatherHelper {

	public WeatherClass getForecast(Integer region) throws SAXException { 
		WeatherClass result = null; 
		DigesterLoader digesterLoader = DigesterLoader.newLoader(new WeatherRules()); 
		Digester digester = digesterLoader.newDigester(); 
		digester.setNamespaceAware(false);
		
		//http://news.bbc.co.uk/weather/forecast/8/Next3DaysRSS.rss
		String url = "http://news.bbc.co.uk/weather/forecast/" + region.toString() + "/Next3DaysRSS.rss";
		
		try {
			result = digester.parse(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return result;
	} 

}




