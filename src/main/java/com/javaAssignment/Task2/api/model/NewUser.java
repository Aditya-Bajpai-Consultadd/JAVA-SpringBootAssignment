package com.javaAssignment.Task2.api.model;

import com.javaAssignment.Task2.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class NewUser implements CommandLineRunner {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        UsersModel user = new UsersModel();
        user.setId(1);
        user.setUsername("aditya");
        String password = bCryptPasswordEncoder.encode("password");
        user.setPassword(password);

        userRepository.save(user);
        System.out.println("User inserted successfully!");
    }
}