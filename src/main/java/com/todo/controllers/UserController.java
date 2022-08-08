package com.todo.controllers;

import com.todo.model.User;
import com.todo.Interface.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserInterface User;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> registerUser(@RequestBody User newUser) {
        try {
            System.out.println(newUser.getEmailAddress());
            boolean status = User.registerUser(newUser);
            if (status) {
                return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while creating New User**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // delete a user
    // @DeleteMapping
    // public ResponseEntity<Users> deleteUser(String email) {
    // Users users = userRepository.findByEmailAddress(email);
    // if (users != null) {
    // userRepository.deleteById(users.getUserId());
    // logger.info("**********User account deleted successfully ! **********");
    // return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    // }
    // return null;
    // }

    // update a user
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

    @RequestMapping(value = "/validateEmail", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> validateEmail(@RequestParam String email) {
        try {
            boolean status = User.validateEmailLink(email);
            if (status) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while validating email **********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/resendLink", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> resendValidationLink(@RequestParam String email) {
        try {
            boolean status = User.resendValidationEmail(email);
            if (status) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while resending validation link **********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
