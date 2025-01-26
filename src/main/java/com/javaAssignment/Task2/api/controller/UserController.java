package com.javaAssignment.Task2.api.controller;

import com.javaAssignment.Task2.api.model.UsersModel;
import com.javaAssignment.Task2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UsersModel register(@RequestBody UsersModel usersModel){

        return userService.register(usersModel);
    }

    @PostMapping("/loginForm")
    public String login(@RequestBody UsersModel user){

        return userService.verify(user);

    }
}
