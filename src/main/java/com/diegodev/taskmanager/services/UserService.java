package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.exceptions.RegistroNaoEncontradoException;
import com.diegodev.taskmanager.repositories.UserRepository;
import com.diegodev.taskmanager.validator.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserValidator userValidator, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User created(User user, Set<String> role_name){
        userValidator.validar(user);

        associateUserAndRole(user, role_name);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    private void associateUserAndRole(User user, Set<String> role_name) {
        for (String name : role_name){
            Role role = roleService.findByName(name);
            role.getUsers().add(user);
            user.getRoles().add(role);
        }
    }

    public List<User> read(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado!"));
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado!"));
    }

    public User update(User user, Long id){
        User userEncontrado = findById(id);

        updateUser(userEncontrado, user);

        return userRepository.save(userEncontrado);
    }

    private void updateUser(User userEncontrado, User user) {
        if (user.getLogin() != null && !user.getLogin().equals(userEncontrado.getLogin())) userEncontrado.setLogin(user.getLogin());
        if (user.getEmail() != null && !user.getEmail().equals(userEncontrado.getEmail())) userEncontrado.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().equals(userEncontrado.getPassword())) userEncontrado.setPassword(user.getPassword());
    }

    public void delete(Long id){
        userRepository.delete(findById(id));
    }
}
