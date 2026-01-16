package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void created(User user){
        userRepository.save(user);
    }
}
