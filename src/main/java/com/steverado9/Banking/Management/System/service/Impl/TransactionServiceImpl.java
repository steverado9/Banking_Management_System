package com.steverado9.Banking.Management.System.service.Impl;

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

    public TransactionServiceImpl(TransactionRepository transactionRepository, ) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void deposit(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId).
                orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);

        Transaction tx = new Transaction();
        tx.setTransactionType("DEPOSIT");
        tx.setAmount(amount);
        tx.setAccount(account);
        tx.setDescription("Deposit to account");
        tx.setBalanceAfterTransaction(account.getBalance());

        transactionRepository.save(tx);
        accountRepository.save(account);
    }

    @Override
    public void withdraw(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction tx = new Transaction();
        tx.setTransactionType("WITHDRAWAL");
        tx.setAmount(amount);
        tx.setAccount(account);
        tx.setDescription("Withdrawal from account");
        tx.setBalanceAfterTransaction(account.getBalance());

        transactionRepository.save(tx);
        accountRepository.save(account);
    }

    @Override
    public void transfer(Long fromAccountId, String toAccountNumber, double amount) {
        Account sender = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
//        Account receiver = accountRepository.findByAccountNumber(toAccountNumber)
//                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

//        sender.setBalance(sender.getBalance() - amount);
//        receiver.setBalance(receiver.getBalance() + amount);

        // Record sender transaction
        Transaction txSender = new Transaction();
        txSender.setTransactionType("TRANSFER");
        txSender.setAmount(amount);
        txSender.setAccount(sender);
        txSender.setDestinationAccountNumber(toAccountNumber);
        txSender.setDescription("Transfer to " + toAccountNumber);
        txSender.setBalanceAfterTransaction(sender.getBalance());
        transactionRepository.save(txSender);

        // Record receiver transaction
//        Transaction txReceiver = new Transaction();
//        txReceiver.setTransactionType("TRANSFER");
//        txReceiver.setAmount(amount);
//        txReceiver.setAccount(receiver);
//        txReceiver.setDestinationAccountNumber(sender.getAccountNumber());
//        txReceiver.setDescription("Transfer from " + sender.getAccountNumber());
//        txReceiver.setBalanceAfterTransaction(receiver.getBalance());
//        transactionRepository.save(txReceiver);
//
//        accountRepository.save(sender);
//        accountRepository.save(receiver);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Long accountId) {
        return transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
    }
}
