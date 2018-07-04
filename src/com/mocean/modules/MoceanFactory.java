package com.mocean.modules;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import com.mocean.system.Client;

public class MoceanFactory {

	protected HashMap<String,String> params;
	protected String[] required_fields;
	private Client obj_auth;
	
	public MoceanFactory(Client obj_auth)
	{
		this.params = new HashMap<String,String>();
		this.params.put("mocean-api-key", obj_auth.getApiKey());
		this.params.put("mocean-api-secret", obj_auth.getApiSecret());
		this.obj_auth = obj_auth;
		this.required_fields = new String[0];
		
	}
	
	protected Boolean isRequiredFieldsSet()
	{
		for(String value : this.required_fields) 
		{
			if(this.params.get(value) == null) 
			{
				throw new java.lang.Error(value +" is mandatory field, can't be empty.") ;
			}
		}
		return true;
	}
	
	protected void createFinalParams() 
	{
		HashMap<String,String> newParams = new HashMap<String,String>();
		Pattern prefix_regex = Pattern.compile("mocean-");
		
		for(String key : this.params.keySet()) 
		{
			if(this.params.get(key) == null) 
			{
				continue;
			}
			
			Matcher prefix = prefix_regex.matcher(key);
			
			if(!prefix.find())
			{
				newParams.put("mocean-"+key,this.params.get(key));
			}
			else
			{
				newParams.put(key,this.params.get(key));
			}
			
		}
		
		this.params = newParams;
	}
	
	protected MoceanFactory create(HashMap<String,String> params) 
	{
		this.params.putAll(params);
		return this;
	}
	
	protected void reset() 
	{
		this.params = new HashMap<String,String>();
		this.params.put("mocean-api-key",this.obj_auth.getApiKey());
		this.params.put("mocean-api-secret",this.obj_auth.getApiSecret());
		
	}
}
