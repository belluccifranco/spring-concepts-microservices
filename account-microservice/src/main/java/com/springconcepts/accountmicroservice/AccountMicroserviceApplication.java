package com.springconcepts.accountmicroservice;

import com.springconcepts.accountmicroservice.model.Account;
import com.springconcepts.accountmicroservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class AccountMicroserviceApplication implements ApplicationRunner {

	private final AccountRepository accountRepository;

	@Autowired
	public AccountMicroserviceApplication(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountMicroserviceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.accountRepository.save(Account.builder()
				.userId("60fb3c7a75f1793de5849f4c")
				.balance(BigDecimal.ZERO)
				.build());
	}
}
