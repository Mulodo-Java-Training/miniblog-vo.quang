package com.mulodo.miniblog.resteasy.form;

import javax.ws.rs.FormParam;

public class RegisterForm 
{
	@FormParam("username")
	private String username;
	
	@FormParam("password")
	private String password;
	
	@FormParam("lastname")
	private String lastname;
	
	@FormParam("firstname")
	private String firstname;
	
	@FormParam("email")
	private String email;
	
	@FormParam("image")
	private String image;

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getLastname() 
	{
		return lastname;
	}

	public void setLastname(String lastname) 
	{
		this.lastname = lastname;
	}

	public String getFirstname() 
	{
		return firstname;
	}

	public void setFirstname(String firstname) 
	{
		this.firstname = firstname;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getImage() 
	{
		return image;
	}

	public void setImage(String image) 
	{
		this.image = image;
	}
}
