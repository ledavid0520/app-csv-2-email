package com.csvemail.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.csvemail.project.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

}
