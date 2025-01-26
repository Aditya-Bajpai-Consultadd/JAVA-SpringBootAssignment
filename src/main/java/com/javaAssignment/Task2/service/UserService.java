package com.javaAssignment.Task2.service;

import com.javaAssignment.Task2.api.model.UsersModel;
import com.javaAssignment.Task2.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private UserRepository repository;
    @Autowired
    AuthenticationManager manager;

    public UsersModel register(UsersModel user){
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public String verify(UsersModel user) {
        Authentication authentication =
                manager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()) return "Bearer " +jwtService.generateToken(user.getUsername());
        return "Invalid Credentials";
    }


}
