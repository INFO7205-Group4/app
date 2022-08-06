package com.todo.repositories;

import com.todo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {

    Users findByEmailAddress(String emailAddress);

}
