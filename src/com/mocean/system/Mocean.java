package com.mocean.system;
import com.mocean.modules.message.*;
import com.mocean.modules.account.*;


public class Mocean {

	private Client obj_auth; 
	
	public Mocean(Client obj_auth) 
	{
		if(obj_auth.getApiKey() == null ||obj_auth.getApiSecret() == null) 
		{
			throw new java.lang.Error("Api key and api secret for client object can't be empty.");
		}
		else 
		{
			this.obj_auth = obj_auth;
		}
	}	
	
	public Sms sms()
	{
		return new Sms(this.obj_auth);
	}
	
	public Sms flashSms() 
	{
		Sms sms = new Sms(this.obj_auth);
		sms.flashSms = true;
		return sms;
	}
	
	public Message_status message_status()
	{
		return new Message_status(this.obj_auth);
	}
	
	public Balance balance() 
	{
		return new Balance(this.obj_auth);
	}
	
	public Pricing pricing_list()
	{
		return new Pricing(this.obj_auth);
	}
	
	public Verify_request verify_request() 
	{
		return new Verify_request(this.obj_auth);
	}
	
	public Verify_validate verify_validate()
	{
		return new Verify_validate(this.obj_auth);
	}
}
