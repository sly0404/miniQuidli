package com.example.miniQuidli.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.miniQuidli.accessdata.User;
import com.example.miniQuidli.accessdata.UserRepository;
import com.example.miniQuidli.dto.BalanceDTO;
import com.example.miniQuidli.dto.CreditWithdrawTranscationUserDTO;
import com.example.miniQuidli.dto.TokenDTO;
import com.example.miniQuidli.dto.TransferDTO;

/*import com.example.miniQuidli.accessdata.Wallet;
import com.example.miniQuidli.accessdata.WalletRepository;*/
import org.springframework.web.bind.annotation.PathVariable;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;

import com.litesoftwares.coingecko.constant.Currency;

@Controller
public class UserController 
{
	private static String TOKEN_LIST = "bitcoin,ethereum,binancecoin";
	@Autowired
	private UserRepository userRepository;
	
	//return list of prices of tokens in TOKEN_LIST
	private List<Double> getEurosTokenPriceList()
	{	
		List<Double> eurosTokenPriceList = new ArrayList<Double>();
		CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
		Map<String, Map<String, Double>> tokenMap = client.getPrice(TOKEN_LIST,Currency.EUR);
		List<String> tokenList = Arrays.asList(TOKEN_LIST.split(","));
		for (String tokenCoinGeckoName : tokenList)
		{
			double tokenPrice = tokenMap.get(tokenCoinGeckoName).get(Currency.EUR);
			Double tokenPriceDouble = new Double(tokenPrice);
			eurosTokenPriceList.add(tokenPriceDouble);
		}
		client.shutdown();
		return eurosTokenPriceList;
	}
	
	//transfer crypto or fiat from account user to another account user
	
	/*example of body request
	
	{ 
	    "sendingAccountId":1, 
	    "receivingAccountId":2, 
	    "transactionAmount":0.1 ,
	    "transactionCurrency":"BTC"	
	 }*/
	
	@PostMapping("/transfer")
	public @ResponseBody Optional<User> transferFund(@RequestBody TransferDTO transferDTO)
	{
		Optional<User> sendindAccountOptional;
		Optional<User> receivingAccountOptional;
		switch (transferDTO.getTransactionCurrency())
		{
			case "BTC" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receivingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					subBTCAmount(sendindAccountOptional, transferDTO);
					addBTCAmount(receivingAccountOptional, transferDTO);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					//to change, manage if the user does not exist
					return Optional.empty();
			case "ETH" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receivingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					subETHAmount(sendindAccountOptional, transferDTO);
					addETHAmount(receivingAccountOptional, transferDTO);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return Optional.empty();
			case "BNB" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receivingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					subBNBAmount(sendindAccountOptional, transferDTO);
					addBNBAmount(receivingAccountOptional, transferDTO);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return Optional.empty();
			case "EUR" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receivingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					subFiatAmount(sendindAccountOptional, transferDTO);
					addFiatAmount(receivingAccountOptional, transferDTO);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return Optional.empty();
			default :
				throw new IllegalArgumentException("Unknown currency! ");
		}
	}
	
	
	//substract amount of user BTC balance if possible
	private void subBTCAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double btcAmount = sendindAccount.getBtc_amount();
		Double newBtcAmount = btcAmount - transferDTO.getTransactionAmount();
		if (newBtcAmount > 0)
			sendindAccount.setBtc_amount(newBtcAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userRepository.save(sendindAccount);
	}
	
	//add amount of user BTC balance
	private void addBTCAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double btcAmount = receiveingAccount.getBtc_amount();
		Double newBtcAmount = btcAmount + transferDTO.getTransactionAmount();
		receiveingAccount.setBtc_amount(newBtcAmount);
		userRepository.save(receiveingAccount);
	}
	
	//substract amount of user ETH balance if possible
	private void subETHAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double ethAmount = sendindAccount.getEth_amount();
		Double newEthAmount = ethAmount - transferDTO.getTransactionAmount();
		if (newEthAmount > 0)
			sendindAccount.setEth_amount(newEthAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userRepository.save(sendindAccount);
	}
	
	//add amount of user ETH balance

	private void addETHAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double ethAmount = receiveingAccount.getEth_amount();
		Double newEthAmount = ethAmount + transferDTO.getTransactionAmount();
		receiveingAccount.setEth_amount(newEthAmount);
		userRepository.save(receiveingAccount);
	}
	
	//substract amount of user BNB balance if possible
	private void subBNBAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double bnbAmount = sendindAccount.getBnb_amount();
		Double newBnbAmount = bnbAmount - transferDTO.getTransactionAmount();
		if (newBnbAmount > 0)
			sendindAccount.setBnb_amount(newBnbAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userRepository.save(sendindAccount);
	}
	
	//add amount of user BNB balance
	private void addBNBAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double bnbAmount = receiveingAccount.getBnb_amount();
		Double newBnbAmount = bnbAmount + transferDTO.getTransactionAmount();
		receiveingAccount.setBnb_amount(newBnbAmount);
		userRepository.save(receiveingAccount);
	}
	
	//substract amount of user Fiat balance if possible
	private void subFiatAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double fiatAmount = sendindAccount.getFiat_amount();
		Double newFiatAmount = fiatAmount - transferDTO.getTransactionAmount();
		if (newFiatAmount > 0)
			sendindAccount.setFiat_amount(newFiatAmount);
		else
			throw new IllegalArgumentException("Amount to send exceeds balance!");
		userRepository.save(sendindAccount);
	}
	
	
	//add amount of user Fiat balance
	private void addFiatAmount(Optional<User> receivingAccountOptional, TransferDTO transferDTO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double fiatAmount = receiveingAccount.getFiat_amount();
		Double newFiatAmount = fiatAmount + transferDTO.getTransactionAmount();
		receiveingAccount.setFiat_amount(newFiatAmount);
		userRepository.save(receiveingAccount);
	}
	
	
	
	//credit BTC amount of user BTC balance
	private void creditBTCAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double btcAmount = receiveingAccount.getBtc_amount();
		Double newBtcAmount = btcAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
		receiveingAccount.setBtc_amount(newBtcAmount);
		userRepository.save(receiveingAccount);
	}
	
	//credit ETH amount of user ETH balance
	private void creditETHAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double ethAmount = receiveingAccount.getEth_amount();
		Double newEthAmount = ethAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
		receiveingAccount.setEth_amount(newEthAmount);
		userRepository.save(receiveingAccount);
	}
	
	//credit BNB amount of user BNB balance
	private void creditBNBAmount(Optional<User> receivingAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO)
	{
		User receiveingAccount = receivingAccountOptional.get();
		Double bnbAmount = receiveingAccount.getBnb_amount();
		Double newBnbAmount = bnbAmount + creditWithdrawTranscationUserDTO.getTransactionAmount();
		receiveingAccount.setBnb_amount(newBnbAmount);
		userRepository.save(receiveingAccount);
	}
	
	//credit token balance of user of a specific amount
	
	/*example of body request
	{ 
	    "userAccountId":2,  
	    "transactionAmount":0.5 ,
	    "amountCurrency":"ETH"	
	 }*/
	
	@PostMapping("/credit")
	public @ResponseBody Optional<User> creditUser(@RequestBody CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO)
	{
		Optional<User> userToCreditOptional = userRepository.findById(creditWithdrawTranscationUserDTO.getUserAccountId());
		if (userToCreditOptional.isPresent())
		{
			switch (creditWithdrawTranscationUserDTO.getAmountCurrency())
			{
				case "BTC" :
					creditBTCAmount(userToCreditOptional, creditWithdrawTranscationUserDTO);
				case "ETH" :
					creditETHAmount(userToCreditOptional, creditWithdrawTranscationUserDTO);
				case "BNB" :
					creditBNBAmount(userToCreditOptional, creditWithdrawTranscationUserDTO);
				default :
			}
			return userToCreditOptional;
		}
		else
			return null;
	}
	
	//withdraw token balance of user of a specific amount
	
	/*example of body request
	{ 
	    "userAccountId":2,  
	    "transactionAmount":0.6 ,
	    "amountCurrency":"BNB"	
	 }*/
	
	@PostMapping("/withdraw")
	public @ResponseBody Optional<User> withdrawUser(@RequestBody CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO)
	{
		Optional<User> userToWithdrawOptional = userRepository.findById(creditWithdrawTranscationUserDTO.getUserAccountId());
		if (userToWithdrawOptional.isPresent())
		{
			switch (creditWithdrawTranscationUserDTO.getAmountCurrency())
			{
				case "BTC" :
					withdrawBTCAmount(userToWithdrawOptional, creditWithdrawTranscationUserDTO);
				case "ETH" :
					withdrawETHAmount(userToWithdrawOptional, creditWithdrawTranscationUserDTO);
				case "BNB" :
					withdrawBNBAmount(userToWithdrawOptional, creditWithdrawTranscationUserDTO);
				default :
			}
			return userToWithdrawOptional;
		}
		else
			return null;
	}
	
	// withdraw BTC amount of user BTC balance if possible
	private void withdrawBTCAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO)
	{
		User withdrawAccount = withdrawAccountOptional.get();
		Double btcAmount =  withdrawAccount.getBtc_amount();
		Double newBtcAmount = btcAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
		if (newBtcAmount > 0)
			withdrawAccount.setBtc_amount(newBtcAmount);
		else
			throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
		userRepository.save(withdrawAccount);
	}
	
	// withdraw ETH amount of user ETH balance if possible
	private void withdrawETHAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO)
	{
		User withdrawAccount = withdrawAccountOptional.get();
		Double ethAmount =  withdrawAccount.getEth_amount();
		Double newEthAmount = ethAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
		if (newEthAmount > 0)
			withdrawAccount.setEth_amount(newEthAmount);
		else
			throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
		userRepository.save(withdrawAccount);
	}
	
	// withdraw BNB amount of user BNB balance if possible
	private void withdrawBNBAmount(Optional<User> withdrawAccountOptional, CreditWithdrawTranscationUserDTO creditWithdrawTranscationUserDTO)
	{
		User withdrawAccount = withdrawAccountOptional.get();
		Double bnbAmount =  withdrawAccount.getBnb_amount();
		Double newBnbAmount = bnbAmount - creditWithdrawTranscationUserDTO.getTransactionAmount();
		if (newBnbAmount > 0)
			withdrawAccount.setBnb_amount(newBnbAmount);
		else
			throw new IllegalArgumentException("Amount to withdraw exceeds balance!");
		userRepository.save(withdrawAccount);
	}
	
	//wallet of a specific user with balance of each token and total balance of wallet
	@GetMapping("/balance/{id}")
	public @ResponseBody BalanceDTO getBalance(@PathVariable Integer id)
	{
		Optional<User> userOptional = userRepository.findById(id);
		BalanceDTO balanceDTO = new BalanceDTO();
		if (userOptional.isPresent())
		{
			List<Double> eurosTokenPriceList = getEurosTokenPriceList();
			User user = userOptional.get();
			List<TokenDTO> tokenDTOList = new ArrayList<TokenDTO>();
			
			TokenDTO tokenBtcDTO = new TokenDTO();
			tokenBtcDTO.setTokenName("bitcoin");
			tokenBtcDTO.setTokenBalance(user.getBtc_amount());
			Double tokenPrice = eurosTokenPriceList.get(0);
			tokenBtcDTO.setTokenBalanceFiatValue(tokenPrice * user.getBtc_amount());
			tokenDTOList.add(tokenBtcDTO);
			
			TokenDTO tokenEthDTO = new TokenDTO();
			tokenEthDTO.setTokenName("ethereum");
			tokenEthDTO.setTokenBalance(user.getEth_amount());
			tokenPrice = eurosTokenPriceList.get(1);
			tokenEthDTO.setTokenBalanceFiatValue(tokenPrice * user.getEth_amount());
			tokenDTOList.add(tokenEthDTO);
			
			TokenDTO tokenBnbDTO = new TokenDTO();
			tokenBnbDTO.setTokenName("binancecoin");
			tokenBnbDTO.setTokenBalance(user.getBnb_amount());
			tokenPrice = eurosTokenPriceList.get(2);
			tokenBnbDTO.setTokenBalanceFiatValue(tokenPrice * user.getBnb_amount());
			tokenDTOList.add(tokenBnbDTO);
			
			balanceDTO.setUserTokensList(tokenDTOList);
			balanceDTO.setFiatBalance(user.getFiat_amount());
			Double totalTokensBalance = balanceDTO.getUserTokensList()
					.stream()
					.mapToDouble(tokenDTO -> tokenDTO.getTokenBalanceFiatValue())
					.sum();
			balanceDTO.setTotalBalance(user.getFiat_amount() +totalTokensBalance);
			return balanceDTO;
		}
		else
			return null;
	}
	/*exemple de réponse
	{
	    "userTokensList": [
	        {
	            "tokenName": "bitcoin",
	            "tokenBalance": 11.2,
	            "tokenBalanceFiatValue": 232500.8
	        },
	        {
	            "tokenName": "ethereum",
	            "tokenBalance": 8.4,
	            "tokenBalanceFiatValue": 13312.572
	        },
	        {
	            "tokenName": "binancecoin",
	            "tokenBalance": 300.3,
	            "tokenBalanceFiatValue": 101059.959
	        }
	    ],
	    "fiatBalance": 1000.1,
	    "totalBalance": 347873.431
	}*/
	
}