package com.mulodo.miniblog.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="Users")
public class Users implements Serializable 
{	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int id;
	
	@Size(min=2, max=45, message="Username must be in betweeen 2-45 characters.")
	@NotNull(message="Username can not be null.") 
	@NotEmpty(message="Username can not be empty.")
	@Column(name="username", unique=true)
	private String username;
	
	@Size(min=6, max=45, message="Password must be in between 6-45 characters.")
	@NotNull(message="Password can not be null.") 
	@NotEmpty(message="Password can not be empty.")
	@Column(name="password")
	private String password;
	
	@NotNull(message="Lastname can not be null.") 
	@NotEmpty(message="Lastname can not be empty.")
	@Column(name="lastname", nullable=false)
	private String lastname;
	
	@NotNull(message="Firstname can not be null.")
	@NotEmpty(message="Firstname can not be empty.")
	@Column(name="firstname", nullable=false)
	private String firstname;
	
	@NotNull(message="Email can not be null.") 
	@NotEmpty(message="Email can not be empty.")
	@Pattern(regexp="^[\\w-_+]*[\\.]?[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", message="")
	@Column(name="email", unique=true)
	private String email;
	
	@Column(name="image")
	private String image;
	
	@NotNull
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	
	@NotNull
	@Column(name="modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified_at;
	
	@Transient
	private String access_token;

	public Users()
	{		
	}
	
	public Users(int id, String username, String password, String firstname, String lastname, String email, 
			String image, Date created_at, Date modified_at, String access_token) 
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.image = image;
		this.created_at = created_at;
		this.modified_at = modified_at;
		this.access_token = access_token;
	}

	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
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
	
	public Date getCreated_at() 
	{
		return created_at;
	}
	
	public void setCreated_at(Date created_at) 
	{
		this.created_at = created_at;
	}
	
	public Date getModified_at() 
	{
		return modified_at;
	}
	
	public void setModified_at(Date modified_at) 
	{
		this.modified_at = modified_at;
	}
	
	public String getAccess_token() 
	{
		return access_token;
	}

	public void setAccess_token(String access_token) 
	{
		this.access_token = access_token;
	}
}
