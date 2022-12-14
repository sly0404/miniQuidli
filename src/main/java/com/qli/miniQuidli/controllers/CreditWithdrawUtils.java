package com.qli.miniQuidli.controllers;

import java.util.Optional;

import com.qli.miniQuidli.accessdata.User;
import com.qli.miniQuidli.accessdata.dao.UserDAO;
import com.qli.miniQuidli.accessdata.UserRepository;
import com.qli.miniQuidli.controllers.dto.CreditWithdrawTranscationUserDTO;

public class CreditWithdrawUtils
{
	//credit BTC amount of user BTC balance
		public static void creditBTCAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserDAO userDAO)
		{
			User receiveingAccount = receivingAccountOptional.get();
			double btcAmount = receiveingAccount.getWallet().getBtc_amount();
			double newBtcAmount = btcAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
			receiveingAccount.getWallet().setBtc_amount(newBtcAmount);
			userDAO.saveUser(receiveingAccount);
		}
		
		//credit ETH amount of user ETH balance
		public static void creditETHAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserDAO userDAO)
		{
			User receiveingAccount = receivingAccountOptional.get();
			double ethAmount = receiveingAccount.getWallet().getEth_amount();
			double newEthAmount = ethAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
			receiveingAccount.getWallet().setEth_amount(newEthAmount);
			userDAO.saveUser(receiveingAccount);
		}
		
		//credit BNB amount of user BNB balance
		public static void creditBNBAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserDAO userDAO)
		{
			User receiveingAccount = receivingAccountOptional.get();
			double bnbAmount = receiveingAccount.getWallet().getBnb_amount();
			double newBnbAmount = bnbAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
			receiveingAccount.getWallet().setBnb_amount(newBnbAmount);
			userDAO.saveUser(receiveingAccount);
		}
		
		// withdraw BTC amount of user BTC balance if possible
		public static void withdrawBTCAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserDAO userDAO)
		{
			User withdrawAccount = withdrawAccountOptional.get();
			double btcAmount =  withdrawAccount.getWallet().getBtc_amount();
			double newBtcAmount = btcAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
			if (newBtcAmount > 0)
				withdrawAccount.getWallet().setBtc_amount(newBtcAmount);
			else
				throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
			userDAO.saveUser(withdrawAccount);
		}
		
		// withdraw ETH amount of user ETH balance if possible
		public static void withdrawETHAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserDAO userDAO)
		{
			User withdrawAccount = withdrawAccountOptional.get();
			double ethAmount =  withdrawAccount.getWallet().getEth_amount();
			double newEthAmount = ethAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
			if (newEthAmount > 0)
				withdrawAccount.getWallet().setEth_amount(newEthAmount);
			else
				throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
			userDAO.saveUser(withdrawAccount);
		}
		
		// withdraw BNB amount of user BNB balance if possible
		public static void withdrawBNBAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserDAO userDAO)
		{
			User withdrawAccount = withdrawAccountOptional.get();
			double bnbAmount =  withdrawAccount.getWallet().getBnb_amount();
			double newBnbAmount = bnbAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
			if (newBnbAmount > 0)
				withdrawAccount.getWallet().setBnb_amount(newBnbAmount);
			else
				throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
			userDAO.saveUser(withdrawAccount);
		}
}
