package com.mulodo.miniblog.responseformat;

public class Meta 
{	
	private int id;
	private Object message;
	
	public Meta()
	{		
	}
	
	public Meta(int id, Object message) 
	{
		this.id = id;
		this.message = message;		
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public Object getMessage() 
	{
		return message;
	}

	public void setMessage(Object message) 
	{
		this.message = message;
	}

	
}
