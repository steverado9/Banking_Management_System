package com.steverado9.Banking.Management.System.service;

import com.steverado9.Banking.Management.System.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();

    Account saveAccount(Account account);

    Account getAccountById(Long id);

    Account updateAccount(Account account);

    void deleteAccountById(Long id);
}
