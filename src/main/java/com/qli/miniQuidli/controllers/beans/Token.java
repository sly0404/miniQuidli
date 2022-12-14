package com.qli.miniQuidli.controllers.beans;

//DTO used to manage response to display tokens of wallet
public class Token
{
	private String tokenName;
	private Double tokenBalance;
	private Double tokenBalanceFiatValue;
	
	public String getTokenName()
	{
		return tokenName;
	}
	public void setTokenName(String tokenName)
	{
		this.tokenName = tokenName;
	}
	public Double getTokenBalance()
	{
		return tokenBalance;
	}
	public void setTokenBalance(Double tokenBalance)
	{
		this.tokenBalance = tokenBalance;
	}
	public Double getTokenBalanceFiatValue()
	{
		return tokenBalanceFiatValue;
	}
	public void setTokenBalanceFiatValue(Double tokenBalanceFiatValue)
	{
		this.tokenBalanceFiatValue = tokenBalanceFiatValue;
	}
}
