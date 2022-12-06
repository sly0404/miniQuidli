package com.example.miniQuidli.controllers;

import java.util.Optional;

import com.example.miniQuidli.accessdata.User;
import com.example.miniQuidli.accessdata.UserRepository;
import com.example.miniQuidli.dto.TransferDTO;

public class TransferUtils
{
	//substract amount of user BTC balance if possible
	public static void subBTCAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO, UserRepository userRepository)
	{
		User sendindAccount = sendindAccountOptional.get();
		double btcAmount = sendindAccount.getWallet().getBtc_amount();
		double newBtcAmount = btcAmount - transferDTO.getTransactionAmount();
		if (newBtcAmount > 0)
			sendindAccount.getWallet().setBtc_amount(newBtcAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userRepository.save(sendindAccount);
	}
		
		//add amount of user BTC balance
	public static void addBTCAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO, UserRepository userRepository)
	{
		User receiveingAccount = receivingAccountOptional.get();
		double btcAmount = receiveingAccount.getWallet().getBtc_amount();
		double newBtcAmount = btcAmount + transferDTO.getTransactionAmount();
		receiveingAccount.getWallet().setBtc_amount(newBtcAmount);
		userRepository.save(receiveingAccount);
	}

	//substract amount of user ETH balance if possible
	public static void subETHAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO, UserRepository userRepository)
	{
		User sendindAccount = sendindAccountOptional.get();
		double ethAmount = sendindAccount.getWallet().getEth_amount();
		double newEthAmount = ethAmount - transferDTO.getTransactionAmount();
		if (newEthAmount > 0)
			sendindAccount.getWallet().setEth_amount(newEthAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userRepository.save(sendindAccount);
	}

	//add amount of user ETH balance

	public static void addETHAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO, UserRepository userRepository)
	{
		User receiveingAccount = receivingAccountOptional.get();
		double ethAmount = receiveingAccount.getWallet().getEth_amount();
		double newEthAmount = ethAmount + transferDTO.getTransactionAmount();
		receiveingAccount.getWallet().setEth_amount(newEthAmount);
		userRepository.save(receiveingAccount);
	}

	//substract amount of user BNB balance if possible
	public static void subBNBAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO, UserRepository userRepository)
	{
		User sendindAccount = sendindAccountOptional.get();
		double bnbAmount = sendindAccount.getWallet().getBnb_amount();
		double newBnbAmount = bnbAmount - transferDTO.getTransactionAmount();
		if (newBnbAmount > 0)
			sendindAccount.getWallet().setBnb_amount(newBnbAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userRepository.save(sendindAccount);
	}

	//add amount of user BNB balance
	public static void addBNBAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO, UserRepository userRepository)
	{
		User receiveingAccount = receivingAccountOptional.get();
		double bnbAmount = receiveingAccount.getWallet().getBnb_amount();
		double newBnbAmount = bnbAmount + transferDTO.getTransactionAmount();
		receiveingAccount.getWallet().setBnb_amount(newBnbAmount);
		userRepository.save(receiveingAccount);
	}
}