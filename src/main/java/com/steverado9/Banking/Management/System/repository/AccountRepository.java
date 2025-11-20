package com.steverado9.Banking.Management.System.repository;

import com.steverado9.Banking.Management.System.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(String accountNumber);

    Account findByOwnerId(Long userId);
}
