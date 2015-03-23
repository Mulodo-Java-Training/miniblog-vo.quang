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
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="Tokens")
public class Tokens implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@NotEmpty
//	@NotNull
//	@Column(name="user_id")
//	private int user_id;
	
	@ManyToOne
    @JoinColumn(nullable = false)
    @ForeignKey(name = "fk_token_user")
    @JsonIgnore
    private Users user;
	
	@Id
	@Column(nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	@NotNull
	@Column(name="access_token", unique=true)
	private String access_token;
	
	@JsonIgnore
	@NotEmpty
	@NotNull
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
		
	@SuppressWarnings("unused")
	private String created_at_fm;
	
	@JsonIgnore
	@NotEmpty
	@NotNull
	@Column(name="expired")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expired;
	
	@SuppressWarnings("unused")
	private String expired_fm;
	
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
	
//	public int getUser_id() 
//	{
//		return user_id;
//	}
//
//	public void setUser_id(int user_id) 
//	{
//		this.user_id = user_id;
//	}

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

	public Date getExpired() 
	{
		return expired;
	}

	public void setExpired(Date expired) 
	{
		this.expired = expired;
	}

	@SuppressWarnings("deprecation")
	public String getCreated_at_fm() {
		return created_at.toGMTString();
	}

	public void setCreated_at_fm(String created_at_fm) {
		this.created_at_fm = created_at_fm;
	}

	@SuppressWarnings("deprecation")
	public String getExpired_fm() {
		return expired.toGMTString();
	}

	public void setExpired_fm(String expired_fm) {
		this.expired_fm = expired_fm;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}		
}
