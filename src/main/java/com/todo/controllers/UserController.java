package com.todo.controllers;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;
import com.todo.Interface.UserInterface;
import com.todo.model.User;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    AuthServiceInterface AuthService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> registerUser(@RequestBody User newUser) {
        try {
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

    @NeedLogin
    @RequestMapping(value = "/resendLink", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> resendValidationLink(HttpServletRequest request) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            boolean status = User.resendValidationEmail(loggedInUser);
            if (status) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while resending validation link**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<User> deleteUser(HttpServletRequest request) {
        try {
            String email = AuthService.getUserName(request);
            boolean status = User.deleteUser(email);
            if (status) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while deleting user**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            User userDetails = User.getUserDetails(loggedInUser);
            if (userDetails != null) {
                return new ResponseEntity<User>(userDetails, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while retrieving user's details **********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @NeedLogin
    @RequestMapping(value = "/updateUser", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<String> updateUser(HttpServletRequest request, @RequestBody User updatedUser) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            boolean status = User.updateUser(loggedInUser, updatedUser);
            if (status) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while retrieving user's details **********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @RequestMapping(value = "/confirmationLink", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> confirmUpdateEmailAddress(@RequestParam String oldLink, @RequestParam String newLink) {
        try {
            boolean status = User.sendUpdatedEmailVerificationLink(oldLink, newLink);
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

    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> validateUpdatedEmailAddress(@RequestParam String oldLink,
            @RequestParam String newLink) {
        try {
            boolean status = User.updateVerificationEmailAddress(oldLink, newLink);
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
}
