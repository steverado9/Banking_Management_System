package com.steverado9.Banking.Management.System.repository;

import com.steverado9.Banking.Management.System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
