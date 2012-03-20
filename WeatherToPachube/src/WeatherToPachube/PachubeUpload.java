package WeatherToPachube;

//import Pachube.Data;
//import Pachube.Feed; 
//import Pachube.FeedFactory; 
//import Pachube.Pachube; 
//import Pachube.PachubeException; 

public class PachubeUpload {

	final String myKey = "uNO8GN7BUbEFN9B9lSmePwDKEgGcbMpcICw2DIn6yBY";
	final String pachubeUri = "api.pachube.com";
	final String pachubeFeedPath = "http://api.pachube.com/v2/feeds/";
	final int feedId = 38342;
	
	private HttpClient pachubeClient;
	
		
	public void getFeed()
	{ 
		pachubeClient = new HttpClient("api.pachube.com");
		
		HttpRequest hr = new HttpRequest(this.pachubeFeedPath + feedId + ".csv"); 
		hr.setMethod(HttpMethod.GET); 
		hr.addHeaderItem("X-PachubeApiKey", this.myKey); 
		
		HttpResponse g = this.pachubeClient.send(hr); 
	
		if (g.getHeaderItem("Status").equals("HTTP/1.1 200 OK"))
		{
			System.out.println("Got feed from Pachube");
			System.out.println(g.getBody());
		}
		else
		{
			System.out.println("Failed to get feed from Pachube" + g.getHeaderItem("Status").toString());	
		}
		
	}	
		

	public boolean uploadToPachube(String newValues) 
	{ 
		pachubeClient = new HttpClient("api.pachube.com");
		
		HttpRequest hr = new HttpRequest(this.pachubeFeedPath + feedId); 
		hr.setMethod(HttpMethod.PUT);
		hr.addHeaderItem("X-PachubeApiKey", this.myKey);
		hr.addHeaderItem("content-type", "text/csv");
		
		String body = "WEATHERPACKET," + newValues;
		
		hr.setBody(body); 
		System.out.println(hr.getHttpCommand()); 
		
		HttpResponse g = this.pachubeClient.send(hr); 
		
		if (g.getHeaderItem("Status").equals("HTTP/1.1 200 OK"))
		{
			System.out.println("Updated feed from Pachube");
			System.out.println(g.getBody());
			return true;
		}
		else
		{
			System.out.println("Failed to update feed" + g.getHeaderItem("Status").toString());
			System.out.println(g.getBody());
			return false;
		}
		
	}

}
