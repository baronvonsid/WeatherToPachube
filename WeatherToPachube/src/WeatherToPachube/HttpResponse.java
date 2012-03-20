package WeatherToPachube;

import java.util.Hashtable;

/**
* The Class HttpResponse.
*/
public class HttpResponse {

   /** Body of the Response. */
   private String Body;

   /**
    * Header information, there is a Item called "Status" which is the HTTP
    * status code of the Response.
    */
   private Hashtable<String, String> header;

   /**
    * Constructor.
    */
   public HttpResponse() {
       this.header = new Hashtable<String, String>();
   }

   /**
    * Add an item to the Response header, this method is intended only to be
    * used by the HttpClient.
    * 
    * @param Key the key
    * @param Value the value
    */
   public void addHeaderItem(String Key, String Value) {
       this.header.put(Key, Value);
   }

   /**
    * Get an item from the Response header, there is an Item called "Status"
    * which is the HTTP stutus code of the Response.
    * 
    * @param Key the key
    * @return the header item
    */
   public String getHeaderItem(String Key) {
       return this.header.get(Key);
   }

   /**
    * Gets the body.
    * 
    * @return the body
    */
   public String getBody() {
       return this.Body;
   }

   /**
    * Sets the body.
    * 
    * @param body the body to set
    */
   public void setBody(String body) {
       this.Body = body;
   }

}

