package com.mocean.modules.message;
import com.mocean.system.Client;
import com.mocean.modules.MoceanFactory;
import com.mocean.modules.Transmitter;
import java.util.HashMap;


public class Verify_validate extends com.mocean.modules.MoceanFactory {
	
	public Verify_validate(Client client) 
	{
		super(client);
		this.required_fields = new String[] {"mocean-api-key","mocean-api-secret","mocean-reqid","mocean-otp-code"};
	}
	
	public Verify_validate setReqid(String param)
	{
		this.params.put("mocean-reqid", param);
		return this;
	}
	
	public Verify_validate setOtpCode(String param)
	{
		this.params.put("mocean-otp-code", param);
		return this;
	}
	
	public Verify_validate setRespFormat(String param)
	{
		this.params.put("mocean-resp-format", param);
		return this;
	}
	
	
	public Verify_validate create(HashMap<String,String> params)
	{
		super.create(params);
		return this;
	}
	
	public String send() throws Exception
	{
		this.createFinalParams();
		this.isRequiredFieldsSet();
		
		Transmitter httpRequest = new Transmitter("/rest/1/verify/check","post",this.params);
		return httpRequest.getResponse();
	}

}
