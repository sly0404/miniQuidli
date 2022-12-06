package com.example.miniQuidli.controllers;

import java.util.Optional;

import com.example.miniQuidli.accessdata.User;
import com.example.miniQuidli.accessdata.UserRepository;
import com.example.miniQuidli.dto.CreditWithdrawTranscationUserDTO;

public class CreditWithdrawUtils
{
	//credit BTC amount of user BTC balance
		public static void creditBTCAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserRepository userRepository)
		{
			User receiveingAccount = receivingAccountOptional.get();
			double btcAmount = receiveingAccount.getBtc_amount();
			double newBtcAmount = btcAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
			receiveingAccount.setBtc_amount(newBtcAmount);
			userRepository.save(receiveingAccount);
		}
		
		//credit ETH amount of user ETH balance
		public static void creditETHAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserRepository userRepository)
		{
			User receiveingAccount = receivingAccountOptional.get();
			double ethAmount = receiveingAccount.getEth_amount();
			double newEthAmount = ethAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
			receiveingAccount.setEth_amount(newEthAmount);
			userRepository.save(receiveingAccount);
		}
		
		//credit BNB amount of user BNB balance
		public static void creditBNBAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserRepository userRepository)
		{
			User receiveingAccount = receivingAccountOptional.get();
			double bnbAmount = receiveingAccount.getBnb_amount();
			double newBnbAmount = bnbAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
			receiveingAccount.setBnb_amount(newBnbAmount);
			userRepository.save(receiveingAccount);
		}
		
		// withdraw BTC amount of user BTC balance if possible
		public static void withdrawBTCAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserRepository userRepository)
		{
			User withdrawAccount = withdrawAccountOptional.get();
			double btcAmount =  withdrawAccount.getBtc_amount();
			double newBtcAmount = btcAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
			if (newBtcAmount > 0)
				withdrawAccount.setBtc_amount(newBtcAmount);
			else
				throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
			userRepository.save(withdrawAccount);
		}
		
		// withdraw ETH amount of user ETH balance if possible
		public static void withdrawETHAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserRepository userRepository)
		{
			User withdrawAccount = withdrawAccountOptional.get();
			double ethAmount =  withdrawAccount.getEth_amount();
			double newEthAmount = ethAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
			if (newEthAmount > 0)
				withdrawAccount.setEth_amount(newEthAmount);
			else
				throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
			userRepository.save(withdrawAccount);
		}
		
		// withdraw BNB amount of user BNB balance if possible
		public static void withdrawBNBAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO, UserRepository userRepository)
		{
			User withdrawAccount = withdrawAccountOptional.get();
			double bnbAmount =  withdrawAccount.getBnb_amount();
			double newBnbAmount = bnbAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
			if (newBnbAmount > 0)
				withdrawAccount.setBnb_amount(newBnbAmount);
			else
				throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
			userRepository.save(withdrawAccount);
		}
}
