package WeatherToPachube;

import java.io.IOException;
import org.xml.sax.SAXException;

import java.sql.SQLException;
import java.util.Date;


public class MainProcess {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SAXException, IOException, SQLException {

		String packet = "";
		
		System.out.println("Beginning java process to upload weather to pachube"); 
		
		
		if (1==2) {
			

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
			BeebWeatherHelper weatherHelper = new BeebWeatherHelper();
			
			WeatherClass weather = weatherHelper.getForecast(8);
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
			
		}
		
		PachubeUpload upload = new PachubeUpload();
		if (upload.uploadToPachube(packet) == true)
		{
			System.out.println("It work-ed");
		}

		//upload.getFeed();
		
		System.out.println("Finished"); 
		}
		
		PersistAllWeatherToDB();
	}
	
	private static void PersistAllWeatherToDB() throws SAXException, IOException, SQLException
	{
		BeebWeatherHelper weatherHelper = new BeebWeatherHelper();
		PersistToDatabase dbHelper = new PersistToDatabase();
		
		boolean continueProcess = true;
		Integer regionId = 1;
		
		while (continueProcess)
		{
			//Get Next format from Beeb
			WeatherClass weather = weatherHelper.getForecast(regionId);
			
			if (weather == null) {
				System.out.println("Nothing from the Beeb, region:" + regionId.toString());
				continueProcess = false;
			}
			else {
				
				//Validate\Update Region
				dbHelper.CheckUpdateRegion(regionId, weather);
				
				//Persist to DB
				if (dbHelper.PersistRegionForecast(regionId, weather) == 3) {
					System.out.println("Added forecast for region:" + regionId.toString());
				}
				else {
					System.out.println("SQL update not work-ed, region:" + regionId.toString());
					continueProcess = false;
				}
				
				regionId++;
			}
			

		}
	}
	
	

}
