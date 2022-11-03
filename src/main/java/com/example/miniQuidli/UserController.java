package com.example.miniQuidli;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.miniQuidli.accessdata.User;
import com.example.miniQuidli.accessdata.UserRepository;
import com.example.miniQuidli.dto.BalanceDTO;
import com.example.miniQuidli.dto.CreditUserDTO;
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
	@Autowired
	private UserRepository userRepository;
	
	
	/*
	UserController(UserRepository repository) 
	{
		this.userRepository = repository;
	}
	 */
	
	@GetMapping("/ter")
	public String index() 
	{
		return "La la la!";
	}
	
	/*
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	*/
	
	@GetMapping("/users")
	public @ResponseBody Iterable<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	@GetMapping("/user/{id}")
	public @ResponseBody BalanceDTO geBalance(@PathVariable Integer id)
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
			tokenDTOList.add(tokenEthDTO);
			
		
			
			balanceDTO.setUserTokensList(tokenDTOList);
			return balanceDTO;
		}
		else
			return null;
	}
	
	private List<Double> getEurosTokenPriceList()
	{	
		List<String> tokenList = new ArrayList<String>();
		tokenList.add("bitcoin");
		tokenList.add("ethereum");
		tokenList.add("binancecoin");
		List<Double> eurosTokenPriceList = new ArrayList<Double>();
		CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
		for (String tokenCoinGeckoName : tokenList)
		{
			Map<String, Map<String, Double>> token = client.getPrice(tokenCoinGeckoName,Currency.EUR);
			double tokenPrice = token.get(tokenCoinGeckoName).get(Currency.EUR);
			Double tokenPriceDouble = new Double(tokenPrice);
			eurosTokenPriceList.add(tokenPriceDouble);
		}
		client.shutdown();
		return eurosTokenPriceList;
	}
	
	@GetMapping("/coin")
	public @ResponseBody Double getPrice()
	{
		CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
		Map<String, Map<String, Double>> bitcoin = client.getPrice("bitcoin",Currency.EUR);
		double bitcoinPrice = bitcoin.get("bitcoin").get(Currency.EUR);
		Double bitcoinPriceDouble = new Double(bitcoinPrice);
		client.shutdown();
		return bitcoinPriceDouble;
	}
	
	@PostMapping("/transfer")
	public @ResponseBody Optional<User> transferFund(@RequestBody TransferDTO transferDTO)
	{
		
		//return transferDTO.getReceivingAccountId() + " " + transferDTO.getTransactionAmount();
		Optional<User> sendindAccountOptional;
		Optional<User> receiveingAccountOptional;
		switch (transferDTO.getTransactionCurrency())
		{
			case "BTC" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receiveingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					subBTCAmount(sendindAccountOptional, transferDTO);
					addBTCAmount(receiveingAccountOptional, transferDTO);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return null;
			case "ETH" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receiveingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					subETHAmount(sendindAccountOptional, transferDTO);
					addETHAmount(receiveingAccountOptional, transferDTO);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return null;
			case "BNB" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receiveingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					subBNBAmount(sendindAccountOptional, transferDTO);
					addBNBAmount(receiveingAccountOptional, transferDTO);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return null;
			case "EUR" :
				sendindAccountOptional = userRepository.findById(transferDTO.getSendingAccountId());
				receiveingAccountOptional = userRepository.findById(transferDTO.getReceivingAccountId());
				if (sendindAccountOptional.isPresent())
				{
					subFiatAmount(sendindAccountOptional, transferDTO);
					addFiatAmount(receiveingAccountOptional, transferDTO);
					return userRepository.findById(transferDTO.getSendingAccountId());
				}
				else
					return null;
			default :
				return null;
		}
	}
	
	private void subBTCAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double btcAmount = sendindAccount.getBtc_amount();
		Double newBtcAmount = btcAmount - transferDTO.getTransactionAmount();
		sendindAccount.setBtc_amount(newBtcAmount);
		userRepository.save(sendindAccount);
	}
	
	private void addBTCAmount(Optional<User> receiveingAccountOptional, TransferDTO transferDTO)
	{
		User receiveingAccount = receiveingAccountOptional.get();
		Double btcAmount = receiveingAccount.getBtc_amount();
		Double newBtcAmount = btcAmount + transferDTO.getTransactionAmount();
		receiveingAccount.setBtc_amount(newBtcAmount);
		userRepository.save(receiveingAccount);
	}
	
	private void subETHAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double ethAmount = sendindAccount.getEth_amount();
		Double newEthAmount = ethAmount - transferDTO.getTransactionAmount();
		sendindAccount.setEth_amount(newEthAmount);
		userRepository.save(sendindAccount);
	}
	
	private void addETHAmount(Optional<User> receiveingAccountOptional, TransferDTO transferDTO)
	{
		User receiveingAccount = receiveingAccountOptional.get();
		Double ethAmount = receiveingAccount.getEth_amount();
		Double newEthAmount = ethAmount + transferDTO.getTransactionAmount();
		receiveingAccount.setEth_amount(newEthAmount);
		userRepository.save(receiveingAccount);
	}
	
	private void subBNBAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double bnbAmount = sendindAccount.getBnb_amount();
		Double newBnbAmount = bnbAmount - transferDTO.getTransactionAmount();
		sendindAccount.setBnb_amount(newBnbAmount);
		userRepository.save(sendindAccount);
	}
	
	private void addBNBAmount(Optional<User> receiveingAccountOptional, TransferDTO transferDTO)
	{
		User receiveingAccount = receiveingAccountOptional.get();
		Double bnbAmount = receiveingAccount.getBnb_amount();
		Double newBnbAmount = bnbAmount + transferDTO.getTransactionAmount();
		receiveingAccount.setBnb_amount(newBnbAmount);
		userRepository.save(receiveingAccount);
	}
	
	private void subFiatAmount(Optional<User> sendindAccountOptional, TransferDTO transferDTO)
	{
		User sendindAccount = sendindAccountOptional.get();
		Double fiatAmount = sendindAccount.getFiat_amount();
		Double newFiatAmount = fiatAmount - transferDTO.getTransactionAmount();
		sendindAccount.setFiat_amount(newFiatAmount);
		userRepository.save(sendindAccount);
	}
	
	private void addFiatAmount(Optional<User> receiveingAccountOptional, TransferDTO transferDTO)
	{
		User receiveingAccount = receiveingAccountOptional.get();
		Double fiatAmount = receiveingAccount.getFiat_amount();
		Double newFiatAmount = fiatAmount + transferDTO.getTransactionAmount();
		receiveingAccount.setFiat_amount(newFiatAmount);
		userRepository.save(receiveingAccount);
	}
	
	private void creditBTCAmount(Optional<User> receiveingAccountOptional, CreditUserDTO creditUserDTO)
	{
		User receiveingAccount = receiveingAccountOptional.get();
		Double btcAmount = receiveingAccount.getBtc_amount();
		Double newBtcAmount = btcAmount + creditUserDTO.getAmountToCredit();
		receiveingAccount.setBtc_amount(newBtcAmount);
		userRepository.save(receiveingAccount);
	}
	
	private void creditETHAmount(Optional<User> receiveingAccountOptional, CreditUserDTO creditUserDTO)
	{
		User receiveingAccount = receiveingAccountOptional.get();
		Double ethAmount = receiveingAccount.getEth_amount();
		Double newEthAmount = ethAmount + creditUserDTO.getAmountToCredit();
		receiveingAccount.setEth_amount(newEthAmount);
		userRepository.save(receiveingAccount);
	}
	
	private void creditBNBAmount(Optional<User> receiveingAccountOptional, CreditUserDTO creditUserDTO)
	{
		User receiveingAccount = receiveingAccountOptional.get();
		Double bnbAmount = receiveingAccount.getBnb_amount();
		Double newBnbAmount = bnbAmount + creditUserDTO.getAmountToCredit();
		receiveingAccount.setBnb_amount(newBnbAmount);
		userRepository.save(receiveingAccount);
	}
	
	@PostMapping("/credit")
	public @ResponseBody Optional<User> creditUser(@RequestBody CreditUserDTO creditUserDTO)
	{
		Optional<User> userToCreditOptional = userRepository.findById(creditUserDTO.getUserAccountId());
		if (userToCreditOptional.isPresent())
		{
			switch (creditUserDTO.getAmountCurrency())
			{
				case "BTC" :
					creditBTCAmount(userToCreditOptional, creditUserDTO);
				case "ETH" :
					creditETHAmount(userToCreditOptional, creditUserDTO);
				case "BNB" :
					creditBNBAmount(userToCreditOptional, creditUserDTO);
				default :
			}
			return userToCreditOptional;
		}
		else
			return null;
	}
	
	/*
	@GetMapping("/balance/{id}")
	public @ResponseBody Optional<User> getBalance(@PathVariable Integer id)
	{
		Optional<User> user = userRepository.findById(id);
	}*/
	
	/*
	@GetMapping("/wallet/{id}")
	public @ResponseBody Optional<Wallet> getWallet(@PathVariable Integer id)
	{
		return walletRepository.findById(id);
	}*/

}