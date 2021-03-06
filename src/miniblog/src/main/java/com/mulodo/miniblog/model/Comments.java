package com.mulodo.miniblog.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="Comments")
public class Comments implements Serializable 
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

//	@Column(name="user_id", nullable=false)
//	private int user_id;
	
	@ManyToOne
    @JoinColumn(nullable = false)
    @ForeignKey(name = "fk_comment_user")
    private Users user;
	
	@ManyToOne
    @JoinColumn(nullable = false)
    @ForeignKey(name = "fk_comment_post")
    private Posts post;
	
//	@Column(name="post_id", nullable=false)
//	private int post_id;
	
	@Id
	@Column(nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="content", nullable=false)
	private String content;
	
	@JsonIgnore
	@Column(name="created_at", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	
	@SuppressWarnings("unused")
	private String created_at_fm;
	
	@JsonIgnore
	@Column(name="modified_at", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified_at;
	
	@SuppressWarnings("unused")
	private String modified_at_fm;
	
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

//	public int getUser_id() 
//	{
//		return user_id;
//	}
//
//	public void setUser_id(int user_id) 
//	{
//		this.user_id = user_id;
//	}

//	public int getPost_id() 
//	{
//		return post_id;
//	}
//
//	public void setPost_id(int post_id) 
//	{
//		this.post_id = post_id;
//	}

	@SuppressWarnings("deprecation")
	public String getCreated_at_fm() {
		return created_at.toGMTString();
	}

	public void setCreated_at_fm(String created_at_fm) {
		this.created_at_fm = created_at_fm;
	}

	@SuppressWarnings("deprecation")
	public String getModified_at_fm() {
		return modified_at.toGMTString();
	}

	public void setModified_at_fm(String modified_at_fm) {
		this.modified_at_fm = modified_at_fm;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Posts getPost() {
		return post;
	}

	public void setPost(Posts post) {
		this.post = post;
	}	
}
