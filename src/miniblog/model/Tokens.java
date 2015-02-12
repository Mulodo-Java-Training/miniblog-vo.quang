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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="Tokens")
public class Tokens implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@NotNull
	@Column(name="user_id")
	private int user_id;
	
	@Id
	@Column(nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	@NotNull
	@Column(name="access_token", unique=true)
	private String access_token;
	
	@NotEmpty
	@NotNull
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	
	@NotEmpty
	@NotNull
	@Column(name="expired")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expired;
	
	
	public Tokens()
	{		
	}	
	
	public Tokens(int id, String access_token, Date created_at, Date expired) 
	{
		this.id = id;
		this.access_token = access_token;
		this.created_at = created_at;
		this.expired = expired;
	}
	
	public int getUser_id() 
	{
		return user_id;
	}

	public void setUser_id(int user_id) 
	{
		this.user_id = user_id;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getAccess_token() 
	{
		return access_token;
	}

	public void setAccess_token(String access_token) 
	{
		this.access_token = access_token;
	}

	public Date getCreated_at() 
	{
		return created_at;
	}

	public void setCreated_at(Date created_at) 
	{
		this.created_at = created_at;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) 
	{
		this.expired = expired;
	}		
}
