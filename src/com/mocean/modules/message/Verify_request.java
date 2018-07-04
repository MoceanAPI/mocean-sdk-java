package com.mocean.modules.message;
import com.mocean.system.Client;
import com.mocean.modules.Transmitter;
import java.util.HashMap;

public class Verify_request extends com.mocean.modules.MoceanFactory {

	public Verify_request(Client client) 
	{
		super(client);
		this.required_fields = new String[] {"mocean-api-key","mocean-api-secret","mocean-to","mocean-brand"};
	}
	
	public Verify_request setTo(String param) 
	{
		this.params.put("mocean-to",param);
		return this;
	}
	
	public Verify_request setBrand(String param) 
	{
		this.params.put("mocean-brand",param);
		return this;
	}
	
	public Verify_request setFrom(String param) 
	{
		this.params.put("mocean-from",param);
		return this;
	}
	
	public Verify_request setCodeLength(String param) 
	{
		this.params.put("mocean-code-length",param);
		return this;
	}
	
	public Verify_request setTemplate(String param) 
	{
		this.params.put("mocean-template",param);
		return this;
	}
	
	public Verify_request setPinValidity(String param) 
	{
		this.params.put("mocean-pin-validity",param);
		return this;
	}
	
	public Verify_request setNextEventWait(String param) 
	{
		this.params.put("mocean-next-event-wait",param);
		return this;
	}
	
	public Verify_request setRespFormat(String param) 
	{
		this.params.put("mocean-resp-format",param);
		return this;
	}
	
	public Verify_request create(HashMap<String,String> params) 
	{
		super.create(params);
		return this;
	}
	
	public String send() throws Exception
	{
		this.createFinalParams();
		this.isRequiredFieldsSet();
		Transmitter httpRequest = new Transmitter("/rest/1/verify/req","post",this.params);
		return httpRequest.getResponse();
	}
	
}
