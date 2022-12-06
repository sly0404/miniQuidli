package com.example.miniQuidli.accessdata;

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
  
  private double btc_amount;
  
  private double eth_amount;
  
  private double bnb_amount;
  
  private double fiat_amount;

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
  
  public double getBtc_amount()
  {
  	return btc_amount;
  }

  public void setBtc_amount(double btc_amount)
  {
  	this.btc_amount = btc_amount;
  }

  public double getEth_amount()
  {
  	return eth_amount;
  }

  public void setEth_amount(double eth_amount)
  {
  	this.eth_amount = eth_amount;
  }

  public double getBnb_amount()
  {
  	return bnb_amount;
  }

  public void setBnb_amount(double bnb_amount)
  {
  	this.bnb_amount = bnb_amount;
  }

  public double getFiat_amount()
  {
  	return fiat_amount;
  }

  public void setFiat_amount(double fiat_amount)
  {
  	this.fiat_amount = fiat_amount;
  }
}