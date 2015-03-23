package com.mulodo.miniblog.responseformat;

public class ResponseFormat 
{
	public Meta meta = new Meta();
	public Object data;
		
	public ResponseFormat()
	{
	}
	
	public ResponseFormat(Meta meta, Object data) 
	{
		this.meta = meta;
		this.data = data;
	}	
	
}
