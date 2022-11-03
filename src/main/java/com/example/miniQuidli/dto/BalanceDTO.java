package com.example.miniQuidli.dto;

import java.util.List;

import com.example.miniQuidli.accessdata.User;

//DTO used to manage response to display wallet of a user

public class BalanceDTO
{
	private List<TokenDTO> userTokensList;
	private Double fiatBalance;;
	private Double totalBalance;

	public List<TokenDTO> getUserTokensList()
	{
		return userTokensList;
	}

	public void setUserTokensList(List<TokenDTO> userTokensList)
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
