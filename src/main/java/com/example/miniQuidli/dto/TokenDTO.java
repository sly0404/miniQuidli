package com.example.miniQuidli.dto;

public class TokenDTO
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
