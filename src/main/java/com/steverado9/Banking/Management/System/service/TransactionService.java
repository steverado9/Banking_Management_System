package com.steverado9.Banking.Management.System.service;

import com.steverado9.Banking.Management.System.entity.Transaction;

import java.util.List;

public interface TransactionService {
     void deposit(Long accountId, Transaction transaction);

     void withdraw(Long accountId, Transaction transaction);

     void transfer(Long fromAccountId, Transaction transaction);

     List<Transaction> getTransactionsByAccountId(Long accountId);
}
