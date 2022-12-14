package com.example.miniQuidli.accessdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Wallet
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer wallet_id;

	private double btc_amount;

	private double eth_amount;

	private double bnb_amount;

	private double fiat_amount;

	private Integer user_id;

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
