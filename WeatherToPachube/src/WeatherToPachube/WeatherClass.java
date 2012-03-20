package WeatherToPachube;


import java.util.ArrayList; 
import java.util.Collections; 
import java.util.Date;
import java.util.List; 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class WeatherClass {

	//<pubDate>Sat, 05 Nov 2011 09:40:00 +0000</pubDate>
	private Date dateOfForecast; 
	
	private List<DayForecast> days = new ArrayList<DayForecast>(); 
	
	public Date getDateOfForecast() { 
		return dateOfForecast; 
	}
	
	public void setPubDate(String pubDate) 
	{ 
		//EEE, dd MMM yyyy kk:MM:ss
		//Sat, 05 Nov 2011 09:40:00 +0000
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		try
		{
			this.dateOfForecast = format.parse(pubDate.substring(5,16));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}

	} 

	public List<DayForecast> getDays() { 
	return Collections.unmodifiableList(days); 
	} 
	
	public void addDay(DayForecast day) { 
		days.add(day); 
	} 
	
	public String getRGBPacket()
	{
		//Beginning marker - SXS
		//For each RGB: XXXXXX Three hex values for red, green and blue
		//V2 will add refresh frequency and display lights setting.
		//Finish marker - FXF
		
		
		String packet = "SXS";
		
		//Red will consider the max as 25 and the min as 5 - (12)
		//Green will remain neutral at 127
		//Blue will consider the min as -5 and the max as 15
		
		for (int i = 0; i < this.getDays().size(); i++)
		{
			int red = 0;
			int green = 127;
			int blue = 0;
			
			DayForecast day = this.getDays().get(i);
			int mid = day.getMidTemp();

			
			if (mid > 25)
			{
				red = 255;
			}
			else if (mid >= 5)
			{
				red = (mid - 5) * 12;
			}
			
			if (mid > 15)
			{
				blue = 0;
			}
			else if (mid < -5)
			{
				blue = 255;
			}
			else
			{
				int count = 20;
				for (int ii = -5; ii <= 15; ii++)
				{
					if (mid == ii)
					{
						blue = count * 12;
						continue;
					}
					count--;
				}
			}
			
			//packet = packet + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
			packet = packet + padInt(red) + padInt(green) + padInt(blue); 
		}
		packet = packet + "FXF";
		return packet;
	}

	private String padInt(int toPad)
	{
		if (toPad < 10)
		{
			return "00" + toPad;
		}
		else if (toPad < 100)
		{
			return "0" + toPad;
		}
		else
		{
			return String.valueOf(toPad);
		}
	}
}


