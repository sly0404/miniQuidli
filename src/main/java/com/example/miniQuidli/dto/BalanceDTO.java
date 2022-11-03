package com.example.miniQuidli.dto;

import com.example.miniQuidli.accessdata.User;

public class BalanceDTO
{
	private Iterable<TokenDTO> userTokensList;

	public Iterable<TokenDTO> getUserTokensList()
	{
		return userTokensList;
	}

	public void setUserTokensList(Iterable<TokenDTO> userTokensList)
	{
		this.userTokensList = userTokensList;
	}
}
