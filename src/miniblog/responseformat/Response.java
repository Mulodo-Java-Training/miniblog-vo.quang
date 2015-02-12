package com.mulodo.miniblog.responseformat;

public class Response 
{
	private Meta meta;
	private Object data;
	
	public Response()
	{		
	}
	
	public Response(Meta meta, Object data) 
	{
		this.meta = meta;
		this.data = data;
	}

	public Meta getMeta() 
	{
		return meta;
	}

	public void setMeta(Meta meta) 
	{
		this.meta = meta;
	}

	public Object getData() 
	{
		return data;
	}

	public void setData(Object data) 
	{
		this.data = data;
	}	
}
