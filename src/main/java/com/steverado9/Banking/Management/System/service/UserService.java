package com.steverado9.Banking.Management.System.service;

import com.steverado9.Banking.Management.System.entity.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> getUserByUsername(String username);

    User getUserById(Long id);
}
