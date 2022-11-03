package com.example.miniQuidli.dto;

//DTO used to manage transfert tokens from an account to another account
public class TransferDTO
{
	private Integer sendingAccountId;
	private Integer receivingAccountId;
	private Double transactionAmount;
	private String transactionCurrency;
	
	public Integer getSendingAccountId()
	{
		return sendingAccountId;
	}
	public void setSendingAccountId(Integer sendingAccountId)
	{
		this.sendingAccountId = sendingAccountId;
	}
	public Integer getReceivingAccountId()
	{
		return receivingAccountId;
	}
	public void setReceivingAccountId(Integer receivingAccountId)
	{
		this.receivingAccountId = receivingAccountId;
	}
	public Double getTransactionAmount()
	{
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount)
	{
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionCurrency()
	{
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency)
	{
		this.transactionCurrency = transactionCurrency;
	}
}
