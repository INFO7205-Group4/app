package com.todo.controllers;

import com.todo.exception.ResourceNotFoundException;
import com.todo.model.Users;
import com.todo.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.Authenticator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Users> getUser(Authentication authentication) {
        Users users = userRepository.findByEmailAddress(authentication.getName());
        if (users == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Users> create(@RequestBody Users registerUser) {
        try {

            if (!isValidEmailAddress(registerUser.getEmailAddress())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            List<Users> users = userRepository.findAll();

            for (Users usr : users) {
                if (usr.getEmailAddress().equals(registerUser.getEmailAddress())) {
                    logger.info("**********User account already exists with this email ! **********");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            registerUser.setUserPassword(bCryptPasswordEncoder.encode(registerUser.getUserPassword()));
            registerUser.setEmailValidated(false);
            registerUser.setEmailSentTime(new Timestamp(System.currentTimeMillis()));
            registerUser.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
            registerUser.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
            userRepository.save(registerUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);

        } catch (Exception e) {
            logger.info("**********Exception while creating New User**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @DeleteMapping
    public ResponseEntity<Users> deleteUser(String email) {
        Users users = userRepository.findByEmailAddress(email);
        if (users != null) {
            userRepository.deleteById(users.getUserId());
            logger.info("**********User account deleted successfully ! **********");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return null;
    }

    // @PutMapping
    // public ResponseEntity<Users> updateUser(Authentication authentication,
    // @RequestBody Users user) {
    // Users users = userRepository.findByEmailAddress(authentication.getName());
    // if (users != null) {
    // users.setfName(user.getfName());
    // users.setmName(user.getmName());
    // users.setlName(user.getlName());
    // users.setEmailAddress(user.getEmailAddress());
    // userRepository.save(users);
    // logger.info("**********User updated successfully ! **********");
    // return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    // }
    // return null;
    // }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
