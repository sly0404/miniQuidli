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
  
  private Integer user_age;
  
  private Double btc_amount;
  
  private Double eth_amount;
  
  private Double bnb_amount;
  
  private Double fiat_amount;
  
  /*
  @OneToOne
  @JoinColumn(name = "user_idwallet")
  @JoinColumn(name = "wallet_id")
  private Wallet wallet;*/

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

  public Integer getUser_age()
  {
	  return user_age;
  }

  public void setUser_age(Integer user_age)
  {
	  this.user_age = user_age;
  }
  
  public Double getBtc_amount()
  {
  	return btc_amount;
  }

  public void setBtc_amount(Double btc_amount)
  {
  	this.btc_amount = btc_amount;
  }

  public Double getEth_amount()
  {
  	return eth_amount;
  }

  public void setEth_amount(Double eth_amount)
  {
  	this.eth_amount = eth_amount;
  }

  public Double getBnb_amount()
  {
  	return bnb_amount;
  }

  public void setBnb_amount(Double bnb_amount)
  {
  	this.bnb_amount = bnb_amount;
  }

  public Double getFiat_amount()
  {
  	return fiat_amount;
  }

  public void setFiat_amount(Double fiat_amount)
  {
  	this.fiat_amount = fiat_amount;
  }
  
  

  /*
  public Wallet getWallet()
  {
  	return wallet;
  }

  public void setWallet(Wallet wallet)
  {
  	this.wallet = wallet;
  }*/
  
}