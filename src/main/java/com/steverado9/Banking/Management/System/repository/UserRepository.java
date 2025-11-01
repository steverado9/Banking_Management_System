package com.steverado9.Banking.Management.System.repository;

import com.steverado9.Banking.Management.System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
