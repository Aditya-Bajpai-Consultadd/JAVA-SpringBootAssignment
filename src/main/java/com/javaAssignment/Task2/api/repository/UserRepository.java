package com.javaAssignment.Task2.api.repository;

import com.javaAssignment.Task2.api.model.UsersModel;
import com.javaAssignment.Task2.api.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersModel, Integer> {
    UsersModel findByUsername(String username);
}
