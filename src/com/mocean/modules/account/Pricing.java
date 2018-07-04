package com.mocean.modules.account;
import com.mocean.system.Client;
import java.util.HashMap;
import com.mocean.modules.Transmitter;

public class Pricing extends com.mocean.modules.MoceanFactory{

	public Pricing(Client obj_auth) 
	{
		super(obj_auth);
		this.required_fields = new String[] {"mocean-api-key","mocean-api-secret"};
	}
	
	public Pricing setMcc(String param)
	{
		this.params.put("mocean-mcc",param);
		return this;
	}
	
	public Pricing setMnc(String param)
	{
		this.params.put("mocean-mnc",param);
		return this;
	}
	
	public Pricing setDelimiter(String param)
	{
		this.params.put("mocean-delimiter",param);
		return this;
	}
	
	public Pricing setRespFormat(String param)
	{
		this.params.put("mocean-resp-format",param);
		return this;
	}
	
	public String inquiry() throws Exception
	{
		return this.send();
	}
	
	public String inquiry(HashMap<String,String> params) throws Exception
	{
		this.create(params);
		return this.send();
	}
	
	private String send() throws Exception
	{
		this.createFinalParams();
		this.isRequiredFieldsSet();
		Transmitter httpRequest = new Transmitter("/rest/1/account/pricing","get",this.params);
		return httpRequest.getResponse();
	}

}
