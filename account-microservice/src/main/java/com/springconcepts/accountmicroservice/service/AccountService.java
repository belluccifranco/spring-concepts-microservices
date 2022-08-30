package com.springconcepts.accountmicroservice.service;

import com.springconcepts.accountmicroservice.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void updateBalance(String userId, BigDecimal amount) {
        accountRepository.findByUserId(userId)
                .ifPresent(account -> account.setBalance(account.getBalance().add(amount)));
    }

}
