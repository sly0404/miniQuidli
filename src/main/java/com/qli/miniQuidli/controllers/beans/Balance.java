package com.qli.miniQuidli.controllers.beans;

import java.util.List;


//DTO used to manage response to display wallet of a user

public class Balance
{
	private List<Token> userTokensList;
	private Double fiatBalance;;
	private Double totalBalance;

	public List<Token> getUserTokensList()
	{
		return userTokensList;
	}

	public void setUserTokensList(List<Token> userTokensList)
	{
		this.userTokensList = userTokensList;
	}

	public Double getFiatBalance()
	{
		return fiatBalance;
	}

	public void setFiatBalance(Double fiatBalance)
	{
		this.fiatBalance = fiatBalance;
	}

	public Double getTotalBalance()
	{
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance)
	{
		this.totalBalance = totalBalance;
	}
	
	
}
