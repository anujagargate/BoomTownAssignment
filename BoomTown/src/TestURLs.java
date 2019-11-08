import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestURLs {

	public static String parseandReturnString(String urlforParsing) throws ParseException, IOException
	{
		
		URL getAPIResponse= new URL ("https://api.github.com/orgs/BoomTownROI");
		
		HttpURLConnection connectionForBT = (HttpURLConnection) getAPIResponse.openConnection();
		connectionForBT.setRequestMethod("GET");
	
		   
		
		String returnedResult = "";
		Scanner sc = new Scanner(getAPIResponse.openStream());           //Open connection and get input stream
		while(sc.hasNext())												 
		{
			returnedResult += sc.next();			
		}
		sc.close();
		
		return returnedResult;
		
		
	}
	
	public static void main(String[] args) throws ParseException, IOException  {
		
		String returnedResult = parseandReturnString("https://api.github.com/orgs/BoomTownROI");
		
		JSONParser parser = new JSONParser();												//Parse the JSON object to get key-value pairs.
		JSONObject jObj = (JSONObject) parser.parse(returnedResult); 					

		
		@SuppressWarnings("unchecked")
		Collection<Object> values = jObj.values();         //   Collection of Object data-type to store String, boolean values in JSON
																//to make it iterable
		for (Object innerval : values) {
			try {
		    if(innerval.toString().startsWith("https://api.github.com/orgs/BoomTownROI") == true)   //check the values starting with given 	
		    																						//	api to get the response code
		    {
		    	System.out.println("Print value-----:"+innerval.toString());
		 
		    	
		    	URL getResponse= new URL (innerval.toString());
				
				HttpURLConnection testConn = (HttpURLConnection) getResponse.openConnection();
				testConn.setRequestMethod("GET");
				int responseCodeForTest = testConn.getResponseCode();
				
				System.out.println("Print Response Code ------:"+responseCodeForTest);
				
				
				if(responseCodeForTest == 200) {
				String resultfor200 = parseandReturnString(innerval.toString());
				
				JSONParser parser2 = new JSONParser();
				JSONObject objFor200 = (JSONObject) parser2.parse(resultfor200); 

				
				Collection<Object>keys200 = objFor200.keySet();
				for (Object innerKeys : keys200) {
					System.out.println("Keys for 200-----:"+innerKeys);
					System.out.println("Value for 200----:"+objFor200.get(innerKeys));
					
				}
				
				}
				else
					
					System.out.println("Print Response Code ------:"+testConn.getResponseMessage());
				
		    }
			}
	
			catch(Exception e)                    			//to catch the key-value pairs in JSON having null values.
			{
		//	System.out.println(e.toString());;
			}
			}
				
		
		
		//verify that the 'updated_at' value is later than the 'created_at' date.
		
		if(jObj.get("updated_at").toString().compareTo(jObj.get("created_at").toString()) >0 )
			System.out.println("Updated_at value > Created_at value as it returned positive int");
	
		
		//compare the 'public_repos' count against the repositories array returned from following the 'repos_url'
		
	
		if(Integer.parseInt(jObj.get("public_repos").toString())>(30));	
			int repoCount = 30;                                  
			//The public repositories resource only returns a default limit of 30 repo objects per request. So,
			//limiting count to 30 if >30.
		
		
		URL getAPIResponse1= new URL (jObj.get("repos_url").toString());
		HttpURLConnection connectionForBT1 = (HttpURLConnection) getAPIResponse1.openConnection();
		connectionForBT1.setRequestMethod("GET");
		
		String returnedResult1="";
		Scanner sc1 = new Scanner(getAPIResponse1.openStream());
		while(sc1.hasNext())
		{
			returnedResult1 += sc1.next();			
		}
		sc1.close(); 
	
		
		JSONParser parser1 = new JSONParser();
		org.json.simple.JSONArray jArray = (org.json.simple.JSONArray) parser1.parse(returnedResult1);
		
		System.out.println("Array Length returned by repos_url : "+ jArray.toArray().length);
		if ( repoCount == jArray.toArray().length)
			System.out.println("Counts returned by repos_url and public_repos are Equal----------------:" );
		
	

	}
	
	
}
