package com.steverado9.Banking.Management.System.service;

import com.steverado9.Banking.Management.System.entity.User;

public interface UserService {
    User saveUser(User user);

    User getUserByEmail(String email);

    User getUserById(Long id);
}
