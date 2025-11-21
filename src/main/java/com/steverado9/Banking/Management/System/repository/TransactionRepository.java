package com.steverado9.Banking.Management.System.repository;

import com.steverado9.Banking.Management.System.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);

    void deleteByAccountId(Long accountId);
}
