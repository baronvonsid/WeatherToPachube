package WeatherToPachube;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

/**
* Models a HttpRequest including headers.
* 
* @author Sam Wilson
* @version 1.0
* 
*/
public class HttpRequest {

   /** URL the HttpRequest is intended for. */
   private URL url;

   /** HTTP Method that the object uses. */
   private HttpMethod method;

   /** Hashtable of the Header identifier and its value. */
   private Hashtable<String, String> header;

   /** Body of the HTTP Request. */
   private String Body = "";

   /**
    * Constructor.
    * 
    * @param url the url
    * @param method the method
    * @param header the header
    * @param body the body
    */
   public HttpRequest(URL url, HttpMethod method,
           Hashtable<String, String> header, String body) {

       this.method = method;
       this.header = header;
       this.Body = body;
       this.url = url;
   }

   /**
    * Default Constructor.
    */
   public HttpRequest() {
       this(null, null, new Hashtable<String, String>(), "");
   }

   /**
    * Constructor.
    * 
    * @param a The URL that this Request is intended for
    */
   public HttpRequest(String a) {
       super();
       try {
           this.url = new URL(a);
       } catch (MalformedURLException e) {
           e.printStackTrace();
       }
       this.header = new Hashtable<String, String>();
   }

   /**
    * Constructor.
    * 
    * @param url the url
    * @param method the method
    * @param body the body
    */
   public HttpRequest(URL url, HttpMethod method, String body) {
       super();
       this.method = method;
       this.Body = body;
       this.url = url;
   }

   /**
    * Add an header item, this is stored as a Key,Value pair. Can be used to
    * set session values, and send cookie information
    * 
    * @param Key the key
    * @param Value the value
    */
   public void addHeaderItem(String Key, String Value) {
       this.header.put(Key, Value);
   }

   /**
    * Generate the HTTP Command from the object.
    * 
    * @return Http Command
    */
   public String getHttpCommand() {
       String ret = "";
       if (this.method.equals(HttpMethod.GET)) {
           ret = "GET " + this.url.getPath() + " HTTP/1.1 \r\n" + "Host: "
                   + this.url.getHost() + "\r\n";

           Enumeration<String> e = this.header.keys();
           String field;
           while (e.hasMoreElements()) {
               field = e.nextElement();
               ret = ret + field + ": " + this.header.get(field) + " \r\n";

               ret = ret + "\r\n" + this.Body;
           }

       } else if (this.method.equals(HttpMethod.POST)) {

           ret = "POST " + this.url.getPath() + " HTTP/1.1 \r\n" + "Host: "
                   + this.url.getHost() + "\r\n";
           Enumeration<String> e = this.header.keys();
           String field;
           while (e.hasMoreElements()) {
               field = e.nextElement();
               ret = ret + field + ": " + this.header.get(field) + " \r\n";

           }

           ret = ret + "Content-Length: " + this.Body.length() + "\r\n\r\n"
                   + this.Body;

       } else if (this.method.equals(HttpMethod.PUT)) {

           ret = "POST " + this.url.getPath() + "?_method=put HTTP/1.1 \r\n"
                   + "Host: " + this.url.getHost() + "\r\n";

           Enumeration<String> e = this.header.keys();
           String field;
           while (e.hasMoreElements()) {
               field = e.nextElement();
               ret = ret + field + ": " + this.header.get(field) + " \r\n";

           }
           ret = ret + "Content-Length: " + this.Body.length() + "\r\n\r\n"
                   + this.Body;

       } else if (this.method.equals(HttpMethod.DELETE)) {

           ret = "DELETE " + this.url.getPath() + "?_method=put HTTP/1.1 \r\n"
                   + "Host: " + this.url.getHost() + "\r\n";
           Enumeration<String> e = this.header.keys();
           String field;
           while (e.hasMoreElements()) {
               field = e.nextElement();
               ret = ret + field + ": " + this.header.get(field) + " \r\n";
           }

           ret = ret + "Content-Length: " + this.Body.length() + "\r\n\r\n"
                   + this.Body;
       }

       return ret;
   }

   /**
    * Gets the url.
    * 
    * @return the url
    */
   public URL getUrl() {
       return this.url;
   }

   /**
    * Sets the url.
    * 
    * @param url the url to set
    */
   public void setUrl(URL url) {
       this.url = url;
   }

   /**
    * Gets the method.
    * 
    * @return the method
    */
   public HttpMethod getMethod() {
       return this.method;
   }

   /**
    * Sets the method.
    * 
    * @param method the method to set
    */
   public void setMethod(HttpMethod method) {
       this.method = method;
   }

   /**
    * Gets the header.
    * 
    * @return the header
    */
   public Hashtable<String, String> getHeader() {
       return this.header;
   }

   /**
    * Sets the header.
    * 
    * @param header the header to set
    */
   public void setHeader(Hashtable<String, String> header) {
       this.header = header;
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
