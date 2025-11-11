package com.steverado9.Banking.Management.System.service.Impl;

import com.steverado9.Banking.Management.System.entity.Account;
import com.steverado9.Banking.Management.System.repository.AccountRepository;
import com.steverado9.Banking.Management.System.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account saveAccount(Account account) {
        if (account.getAccountNumber() == null || account.getAccountNumber().isEmpty()) {
            account.setAccountNumber(uniqueAccountNumber());
        }
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public String uniqueAccountNumber() {
        String number;
        do {
            number = String.valueOf((long) (Math.random() * 9000000000L) + 1000000000L);
        } while (accountRepository.existsByAccountNumber(number));
        return number;
    }
}
