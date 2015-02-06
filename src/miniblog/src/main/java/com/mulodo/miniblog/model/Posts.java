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

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;



@Entity
@Table(name="Posts")
public class Posts implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@NotNull
	@Column(name="user_id")
	private int user_id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	@NotNull
	@Column(name="title")
	private String title;
	
	@NotEmpty
	@NotNull
	@Column(name="description")
	private String description;
	
	@NotEmpty
	@NotNull
	@Column(name="content")
	private String content;
	
	@NotEmpty
	@NotNull
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	
	@NotEmpty
	@NotNull
	@Column(name="modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified_at;
	
	@NotEmpty
	@NotNull
	@Column(name="status")
	@Type(type="org.hibernate.type.NumericBooleanType") //map integer database value to boolean java value 
	private boolean status = true;
	
	
	@Column(name="image")
	private String image;

	public Posts() {}
	
	public Posts (Users user, int id, String title, String description, String content, Date created_at, 
			Date modified_at, boolean status, String image) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.created_at = created_at;
		this.modified_at = modified_at;
		this.status = status;
		this.image = image;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getModified_at() {
		return modified_at;
	}

	public void setModified_at(Date modified_at) {
		this.modified_at = modified_at;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
	
}
