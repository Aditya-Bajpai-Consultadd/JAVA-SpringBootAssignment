package com.javaAssignment.Task2.service;

import com.javaAssignment.Task2.api.model.UserPrincipal;
import com.javaAssignment.Task2.api.model.UsersModel;
import com.javaAssignment.Task2.api.model.UsersModel;
import com.javaAssignment.Task2.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsersModel user1 = userRepository.findByUsername(username);

        if(user1 == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found.");
        }
        return new UserPrincipal(user1);
    }
}
