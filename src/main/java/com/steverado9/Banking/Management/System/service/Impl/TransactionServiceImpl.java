package com.steverado9.Banking.Management.System.service.Impl;

import com.steverado9.Banking.Management.System.Enum.TransactionType;
import com.steverado9.Banking.Management.System.entity.Account;
import com.steverado9.Banking.Management.System.entity.Transaction;
import com.steverado9.Banking.Management.System.repository.AccountRepository;
import com.steverado9.Banking.Management.System.repository.TransactionRepository;
import com.steverado9.Banking.Management.System.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void deposit(Long accountId, Transaction transaction) {
        Account account = accountRepository.findById(accountId).
                orElseThrow(() -> new RuntimeException("Account not found"));

        double amount = transaction.getAmount();

        account.setBalance(account.getBalance() + amount);


        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setAccount(account);
        transaction.setDescription("Deposit to account");
        transaction.setBalanceAfterTransaction(account.getBalance());

        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    @Override
    public void withdraw(Long accountId, Transaction transaction) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        double amount = transaction.getAmount();

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);

        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setAccount(account);
        transaction.setDescription("Withdrawal from account");
        transaction.setBalanceAfterTransaction(account.getBalance());

        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    @Override
    public void transfer(Long fromAccountId, Transaction transaction) {
        Account account = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        double amount = transaction.getAmount();

        String toAccountNumber = transaction.getDestinationAccountNumber();

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);

        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setAmount(amount);
        transaction.setAccount(account);
        transaction.setDestinationAccountNumber(toAccountNumber);
        transaction.setDescription("Transfer to " + toAccountNumber);
        transaction.setBalanceAfterTransaction(account.getBalance());
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
    }
}
