package com.example.miniQuidli.dto;

public class CreditUserDTO
{
	private Integer userAccountId;
	private Double amountToCredit;
	private String amountCurrency;
	
	public Integer getUserAccountId()
	{
		return userAccountId;
	}
	public void setUserAccountId(Integer userAccountId)
	{
		this.userAccountId = userAccountId;
	}
	public Double getAmountToCredit()
	{
		return amountToCredit;
	}
	public void setAmountToCredit(Double amountToCredit)
	{
		this.amountToCredit = amountToCredit;
	}
	public String getAmountCurrency()
	{
		return amountCurrency;
	}
	public void setAmountCurrency(String amountCurrency)
	{
		this.amountCurrency = amountCurrency;
	}
	
	
}
