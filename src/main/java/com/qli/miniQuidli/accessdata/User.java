package com.qli.miniQuidli.accessdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class User 
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer user_id;
  
  private String user_name;
  
  private String user_last_name;
  
  private int user_age;
  
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private Wallet wallet;
  
  public Integer getUser_id()
  {
	return user_id;
  }

  public void setUser_id(Integer user_id)
  {
	this.user_id = user_id;
  }

  public String getUser_name()
  {
	return user_name;
  }

  public void setUser_name(String user_name)
  {
	  this.user_name = user_name;
  }

  public String getUser_last_name()
  {
	  return user_last_name;
  }

  public void setUser_last_name(String user_last_name)
  {
	  this.user_last_name = user_last_name;
  }

  public int getUser_age()
  {
	  return user_age;
  }

  public void setUser_age(int user_age)
  {
	  this.user_age = user_age;
  }

  public Wallet getWallet()
  {
	  return wallet;
  }

  public void setWallet(Wallet wallet)
  {
	  this.wallet = wallet;
  }
}