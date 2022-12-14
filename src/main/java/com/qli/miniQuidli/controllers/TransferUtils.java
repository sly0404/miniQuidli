package com.qli.miniQuidli.controllers;

import java.util.Optional;

import com.qli.miniQuidli.accessdata.User;
import com.qli.miniQuidli.accessdata.dao.UserDAO;
import com.qli.miniQuidli.accessdata.UserRepository;
import com.qli.miniQuidli.controllers.dto.TransferDTO;

public class TransferUtils
{
	//substract amount of user BTC balance if possible
	public static void subBTCAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO, UserDAO userDAO)
	{
		User sendindAccount = sendindAccountOptional.get();
		double btcAmount = sendindAccount.getWallet().getBtc_amount();
		double newBtcAmount = btcAmount - transferDTO.getTransactionAmount();
		if (newBtcAmount > 0)
			sendindAccount.getWallet().setBtc_amount(newBtcAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userDAO.saveUser(sendindAccount);
	}
		
		//add amount of user BTC balance
	public static void addBTCAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO, UserDAO userDAO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		double btcAmount = receiveingAccount.getWallet().getBtc_amount();
		double newBtcAmount = btcAmount + transferDTO.getTransactionAmount();
		receiveingAccount.getWallet().setBtc_amount(newBtcAmount);
		userDAO.saveUser(receiveingAccount);
	}

	//substract amount of user ETH balance if possible
	public static void subETHAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO, UserDAO userDAO)
	{
		User sendindAccount = sendindAccountOptional.get();
		double ethAmount = sendindAccount.getWallet().getEth_amount();
		double newEthAmount = ethAmount - transferDTO.getTransactionAmount();
		if (newEthAmount > 0)
			sendindAccount.getWallet().setEth_amount(newEthAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userDAO.saveUser(sendindAccount);
	}

	//add amount of user ETH balance

	public static void addETHAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO, UserDAO userDAO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		double ethAmount = receiveingAccount.getWallet().getEth_amount();
		double newEthAmount = ethAmount + transferDTO.getTransactionAmount();
		receiveingAccount.getWallet().setEth_amount(newEthAmount);
		userDAO.saveUser(receiveingAccount);
	}

	//substract amount of user BNB balance if possible
	public static void subBNBAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO, UserDAO userDAO)
	{
		User sendindAccount = sendindAccountOptional.get();
		double bnbAmount = sendindAccount.getWallet().getBnb_amount();
		double newBnbAmount = bnbAmount - transferDTO.getTransactionAmount();
		if (newBnbAmount > 0)
			sendindAccount.getWallet().setBnb_amount(newBnbAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userDAO.saveUser(sendindAccount);
	}

	//add amount of user BNB balance
	public static void addBNBAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO, UserDAO userDAO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		double bnbAmount = receiveingAccount.getWallet().getBnb_amount();
		double newBnbAmount = bnbAmount + transferDTO.getTransactionAmount();
		receiveingAccount.getWallet().setBnb_amount(newBnbAmount);
		userDAO.saveUser(receiveingAccount);
	}
}
