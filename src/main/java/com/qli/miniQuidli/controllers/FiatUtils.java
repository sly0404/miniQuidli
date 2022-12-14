package com.qli.miniQuidli.controllers;

import java.util.Optional;

import com.qli.miniQuidli.accessdata.User;
import com.qli.miniQuidli.accessdata.dao.UserDAO;
import com.qli.miniQuidli.accessdata.UserRepository;
import com.qli.miniQuidli.controllers.dto.TransferDTO;

public class FiatUtils
{
	//substract amount of user Fiat balance if possible
	public static void subFiatAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO, UserDAO userDAO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double fiatAmount = sendindAccount.getWallet().getFiat_amount();
		Double newFiatAmount = fiatAmount - transferDTO.getTransactionAmount();
		if (newFiatAmount > 0)
			sendindAccount.getWallet().setFiat_amount(newFiatAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userDAO.saveUser(sendindAccount);
	}


	//add amount of user Fiat balance
	public static void addFiatAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO, UserDAO userDAO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double fiatAmount = receiveingAccount.getWallet().getFiat_amount();
		Double newFiatAmount = fiatAmount + transferDTO.getTransactionAmount();
		receiveingAccount.getWallet().setFiat_amount(newFiatAmount);
		userDAO.saveUser(receiveingAccount);
	}
}
