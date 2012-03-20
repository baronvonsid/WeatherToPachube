package WeatherToPachube;

import org.apache.commons.digester3.binder.AbstractRulesModule; 

public class WeatherRules extends AbstractRulesModule { 

protected void configure() { 
	// Register a ObjectCreatRule on matching pattern "family". Later on, in the parsing phase, 
	// when encounters a <family> element, the digester will fire this rule to create a Family object. 
	// Also register a SetPropertiesRule on the same pattern. Later on, in the parsing phase, 
	// the digester will fire this rule to set properties of the Family object 
	// with the attribute values of the <family> element 
	// For the setProperties() to work this way, a property name must be the same as the attribute name. 
	
	//Hopefully should populate the WeatherClass object, with the element pubDate.
	forPattern("rss/channel").createObject().ofType("WeatherToPachube.WeatherClass"); 
	forPattern("rss/channel/pubDate").setBeanProperty(); 
	
	//For each item element, should create a DayForecast object, then add it to the WeatherClass list with addDay.
	forPattern("rss/channel/item").createObject().ofType("WeatherToPachube.DayForecast").then().setNext("addDay");
	
	forPattern("rss/channel/item/title").setBeanProperty(); 
	forPattern("rss/channel/item/description").setBeanProperty(); 
	}
}
