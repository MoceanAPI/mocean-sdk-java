package com.mocean.system;
import java.util.Map;
import java.util.HashMap;

public class Client {

	private HashMap<String,String> params;
	
	public Client() 
	{
		 this.params = new HashMap<String,String>();
	}
	public Client(String api_key,String api_secret)
	{
		 this.params = new HashMap<String,String>();
		 this.params.put("mocean-api-key",api_key);
		 this.params.put("mocean-api-secret",api_secret);
	}
	
	public void setApiKey(String param) 
	{
		this.params.put("mocean-api-key",param);
	}
	
	public void setApiSecret(String param)
	{
		this.params.put("mocean-api-secret", param);
	}
	
	public String getApiKey() 
	{
		return this.params.get("mocean-api-key");
	}
	
	public String getApiSecret() 
	{
		return this.params.get("mocean-api-secret");
	}
}
