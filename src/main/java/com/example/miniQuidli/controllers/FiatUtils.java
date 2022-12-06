package com.example.miniQuidli.controllers;

import java.util.Optional;

import com.example.miniQuidli.accessdata.User;
import com.example.miniQuidli.accessdata.UserRepository;
import com.example.miniQuidli.dto.TransferDTO;

public class FiatUtils
{
	//substract amount of user Fiat balance if possible
	public static void subFiatAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO, UserRepository userRepository)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double fiatAmount = sendindAccount.getWallet().getFiat_amount();
		Double newFiatAmount = fiatAmount - transferDTO.getTransactionAmount();
		if (newFiatAmount > 0)
			sendindAccount.getWallet().setFiat_amount(newFiatAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userRepository.save(sendindAccount);
	}


	//add amount of user Fiat balance
	public static void addFiatAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO, UserRepository userRepository)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double fiatAmount = receiveingAccount.getWallet().getFiat_amount();
		Double newFiatAmount = fiatAmount + transferDTO.getTransactionAmount();
		receiveingAccount.getWallet().setFiat_amount(newFiatAmount);
		userRepository.save(receiveingAccount);
	}
}
