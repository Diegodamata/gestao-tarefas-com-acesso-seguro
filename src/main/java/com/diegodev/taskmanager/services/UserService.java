package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.exceptions.RegistroNaoEncontradoException;
import com.diegodev.taskmanager.repositories.UserRepository;
import com.diegodev.taskmanager.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserService(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    public User created(User user){
        userValidator.validar(user);
        return userRepository.save(user);
    }

    public List<User> read(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado!"));
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
