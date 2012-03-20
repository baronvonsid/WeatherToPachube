package WeatherToPachube;

import java.io.IOException;
import org.xml.sax.SAXException;
import java.util.Date;


public class MainProcess {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void main(String[] args) throws SAXException, IOException {

		String packet = "";
		
		System.out.println("Beginning java process to upload weather to pachube"); 
		
		if (args.length == 3)
		{
			
			System.out.println("Three in values received, so ignoring beeb."); 
			packet = "SXS" + args[0] + args[1] + args[2] + "000000000000000000FXF";
			if (packet.length() != 33)
			{
				System.out.println("It appears you entered some dodgy value, try again.");
				return;
			}
		}
		else
		{
			GetBBCWeather weatherHelper = new GetBBCWeather();
			
			WeatherClass weather = weatherHelper.getForecast();
			System.out.println(weather.getDateOfForecast().toString());
			
			Date today = new Date();		
			
			if (weather.getDateOfForecast().getDay() != today.getDay())
			{
				System.out.println("It appears that the beeb feed is not work-ed for today");
				System.out.println("Forecast date: " + weather.getDateOfForecast().toString());
				System.out.println("Forecast date: " + today.toString());
				return;			
			}
			
			if (weather.getDays().size() != 3)
			{
				System.out.println("It appears that the beeb feed didn't return three days");
				return;
			}
			
			for (int i = 0; i < weather.getDays().size(); i++)
			{
				DayForecast day = weather.getDays().get(i);
				System.out.println(day.getTitle());
				System.out.println(day.getMidTemp());
			}
		
			packet = weather.getRGBPacket();
			System.out.println("Successfully got the beeb values."); 
			
			PersistFeedToDatabase dbHelper = new PersistFeedToDatabase();
			
			if (dbHelper.PersistReading(weather) == true)
			{
				System.out.println("SQL work-ed");
			}
			else
			{
				System.out.println("SQL update not work-ed");
			}
		}
		

		
		PachubeUpload upload = new PachubeUpload();
		if (upload.uploadToPachube(packet) == true)
		{
			System.out.println("It work-ed");
		}

		//upload.getFeed();
		
		System.out.println("Finished"); 
	}

}
