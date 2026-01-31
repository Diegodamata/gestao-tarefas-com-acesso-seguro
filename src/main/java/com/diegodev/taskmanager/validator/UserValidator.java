package com.diegodev.taskmanager.validator;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.exceptions.RegistroDuplicadoException;
import com.diegodev.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validar(User user){
        if (userExist(user)){
            throw new RegistroDuplicadoException("Usuario já cadastrado!");
        }
    }

    private boolean userExist(User user){
        return userRepository
                .existsByLoginAndEmailAndPassword(
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword());
    }
}
