package com.diegodev.taskmanager.repositories;

import com.diegodev.taskmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginAndEmailAndPassword(String login, String email, String password);

    Optional<User> findByLogin(String login);

    @Query("""
            select u from User u
            join fetch u.roles
            where u.login = :login
            """)
    Optional<User> findByLoginFetchRoles(String login);

    @Query("""
            select u from User u
            join fetch u.roles
            where u.email = :email
            """)
    Optional<User> findByEmailFetchRoles(String email);
}
