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

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User update(User user, Long id){
        User userEncontrado = findById(id);

        updateUser(userEncontrado, user);

        return userRepository.save(userEncontrado);
    }

    private void updateUser(User userEncontrado, User user) {
        if (user.getName() != null && !user.getName().equals(userEncontrado.getName())) userEncontrado.setName(user.getName());
        if (user.getEmail() != null && !user.getEmail().equals(userEncontrado.getEmail())) userEncontrado.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().equals(userEncontrado.getPassword())) userEncontrado.setPassword(user.getPassword());
    }

    public void delete(Long id){
        userRepository.delete(findById(id));
    }
}
