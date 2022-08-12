package com.todo.repositories;

import com.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("FROM User WHERE emailAddress = ?1")
    User findByEmailAddress(String emailAddress);

}
