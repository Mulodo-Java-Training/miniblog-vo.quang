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

@Entity
@Table(name="Comments")
public class Comments implements Serializable 
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", nullable=false)
	private int user_id;
	
	@Column(name="post_id", nullable=false)
	private int post_id;
	
	@Id
	@Column(nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="content", nullable=false)
	private String content;
	
	@Column(name="created_at", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	
	@Column(name="modified_at", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified_at;
	
	public Comments()
	{		
	}

	public Comments(int id, String content, Date created_at, Date modified_at) 
	{
		this.id = id;
		this.content = content;
		this.created_at = created_at;
		this.modified_at = modified_at;
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getContent() 
	{
		return content;
	}
	
	public void setContent(String content) 
	{
		this.content = content;
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

	public int getUser_id() 
	{
		return user_id;
	}

	public void setUser_id(int user_id) 
	{
		this.user_id = user_id;
	}

	public int getPost_id() 
	{
		return post_id;
	}

	public void setPost_id(int post_id) 
	{
		this.post_id = post_id;
	}
	
	
	
}
