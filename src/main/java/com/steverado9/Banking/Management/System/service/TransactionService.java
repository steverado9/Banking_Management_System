package com.steverado9.Banking.Management.System.service;

import com.steverado9.Banking.Management.System.entity.Transaction;

import java.util.List;

public interface TransactionService {
     void deposit(Long accountId, double amount);

     void withdraw(Long accountId, double amount);

     void transfer(Long fromAccountId, String toAccountNumber, double amount);

     List<Transaction> getTransactionsByAccountId(Long accountId);
}
