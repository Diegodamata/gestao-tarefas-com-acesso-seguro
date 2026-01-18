package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User created(User user){
        return userRepository.save(user);
    }

    public List<User> read(){
        return userRepository.findAll();
    }
}
