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
import com.example.miniQuidli.controllers.beans.Balance;
import com.example.miniQuidli.controllers.beans.Token;
import com.example.miniQuidli.controllers.dto.CreditWithdrawTranscationUserDTO;
import com.example.miniQuidli.controllers.dto.TransferDTO;

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
					TransferUtils.subBTCAmount(sendindAccountOptional, transferDTO, userRepository);
					TransferUtils.addBTCAmount(receivingAccountOptional, transferDTO, userRepository);
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
					TransferUtils.subETHAmount(sendindAccountOptional, transferDTO, userRepository);
					TransferUtils.addETHAmount(receivingAccountOptional, transferDTO, userRepository);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return Optional.empty();
			case "BNB" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receivingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					TransferUtils.subBNBAmount(sendindAccountOptional, transferDTO, userRepository);
					TransferUtils.addBNBAmount(receivingAccountOptional, transferDTO, userRepository);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return Optional.empty();
			case "EUR" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receivingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					FiatUtils.subFiatAmount(sendindAccountOptional, transferDTO, userRepository);
					FiatUtils.addFiatAmount(receivingAccountOptional, transferDTO, userRepository);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return Optional.empty();
			default :
				throw new IllegalArgumentException("Unknown currency! ");
		}
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
					CreditWithdrawUtils.creditBTCAmount(userToCreditOptional, creditWithdrawTranscationUserDTO, userRepository);
				case "ETH" :
					CreditWithdrawUtils.creditETHAmount(userToCreditOptional, creditWithdrawTranscationUserDTO, userRepository);
				case "BNB" :
					CreditWithdrawUtils.creditBNBAmount(userToCreditOptional, creditWithdrawTranscationUserDTO, userRepository);
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
					CreditWithdrawUtils.withdrawBTCAmount(userToWithdrawOptional, creditWithdrawTranscationUserDTO, userRepository);
				case "ETH" :
					CreditWithdrawUtils.withdrawETHAmount(userToWithdrawOptional, creditWithdrawTranscationUserDTO, userRepository);
				case "BNB" :
					CreditWithdrawUtils.withdrawBNBAmount(userToWithdrawOptional, creditWithdrawTranscationUserDTO, userRepository);
				default :
			}
			return userToWithdrawOptional;
		}
		else
			return null;
	}
	
	
	//wallet of a specific user with balance of each token and total balance of wallet
	@GetMapping("/balance/{id}")
	public @ResponseBody Balance getBalance(@PathVariable Integer id)
	{
		Optional<User> userOptional = userRepository.findById(id);
		Balance balance = new Balance();
		if (userOptional.isPresent())
		{
			List<Double> eurosTokenPriceList = getEurosTokenPriceList();
			User user = userOptional.get();
			List<Token> tokenDTOList = new ArrayList<Token>();
			
			Token tokenBtc = new Token();
			tokenBtc.setTokenName("bitcoin");
			tokenBtc.setTokenBalance(user.getWallet().getBtc_amount());
			double tokenPrice = eurosTokenPriceList.get(0);
			tokenBtc.setTokenBalanceFiatValue(tokenPrice * user.getWallet().getBtc_amount());
			tokenDTOList.add(tokenBtc);
			
			Token tokenEth = new Token();
			tokenEth.setTokenName("ethereum");
			tokenEth.setTokenBalance(user.getWallet().getEth_amount());
			tokenPrice = eurosTokenPriceList.get(1);
			tokenEth.setTokenBalanceFiatValue(tokenPrice * user.getWallet().getEth_amount());
			tokenDTOList.add(tokenEth);
			
			Token tokenBnb = new Token();
			tokenBnb.setTokenName("binancecoin");
			tokenBnb.setTokenBalance(user.getWallet().getBnb_amount());
			tokenPrice = eurosTokenPriceList.get(2);
			tokenBnb.setTokenBalanceFiatValue(tokenPrice * user.getWallet().getBnb_amount());
			tokenDTOList.add(tokenBnb);
			
			balance.setUserTokensList(tokenDTOList);
			balance.setFiatBalance(user.getWallet().getFiat_amount());
			double totalTokensBalance = balance.getUserTokensList()
					.stream()
					.mapToDouble(token -> token.getTokenBalanceFiatValue())
					.sum();
			balance.setTotalBalance(user.getWallet().getFiat_amount() +totalTokensBalance);
			return balance;
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