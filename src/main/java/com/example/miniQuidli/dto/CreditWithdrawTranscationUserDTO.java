package com.example.miniQuidli.dto;

//DTO used to credit or withdraw an amount of token to a user
public class CreditWithdrawTranscationUserDTO
{
	private Integer userAccountId;
	private Double transactionAmount;
	private String amountCurrency;
	
	public Integer getUserAccountId()
	{
		return userAccountId;
	}
	public void setUserAccountId(Integer userAccountId)
	{
		this.userAccountId = userAccountId;
	}
	
	public Double getTransactionAmount()
	{
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount)
	{
		this.transactionAmount = transactionAmount;
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
