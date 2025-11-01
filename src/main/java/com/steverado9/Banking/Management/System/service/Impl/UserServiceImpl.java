package com.steverado9.Banking.Management.System.service.Impl;

import com.steverado9.Banking.Management.System.entity.User;
import com.steverado9.Banking.Management.System.repository.UserRepository;
import com.steverado9.Banking.Management.System.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }
}
