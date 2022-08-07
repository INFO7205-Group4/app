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

    private List<Users> users = createList();

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Users> getUser(Authentication authentication) {
        Users users = userRepository.findByEmailAddress(authentication.getName());
            if(users == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else{
                return ResponseEntity.ok(users);
            }
    }

//    @DeleteMapping(path = { "/{id}" })
//    public Users delete(@PathVariable("id") int id) {
//        Users deletedUser = null;
//        for (Users user : users) {
//            if (user.getUserId().equals(id)) {
//                users.remove(user);
//                deletedUser = user;
//                break;
//            }
//        }
//        return deletedUser;
//    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Users> create(@RequestBody Users registerUser) {
        try{
//            Users user = new Users();
//            user.setEmailAddress("abc@example.com");
//            user.setfName("abc");
//            user.setlName("xyz");
//            user.setUserPassword("test");
        if (!isValidEmailAddress(registerUser.getEmailAddress())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
//        users.add(user);
//        Users testUser = userRepository.findByEmailAddress(user.getEmailAddress());
                //.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + user.getEmailAddress()));;
        List<Users> users = userRepository.findAll();
        //System.out.println("New user: " + newUser.toString());
        for (Users usr : users) {
            //System.out.println("Registered user: " + newUser.toString());
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
//        System.out.println(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
//    } else {
//        logger.info("**********Incorrect Request from User**********");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//    }
} catch (Exception e){
        logger.info("**********Exception while creating New User**********");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
//        }
    }


    @DeleteMapping
    public void deleteuUser(Authentication authentication){

    }

    @PutMapping
    public Users updateUser(Authentication authentication, @RequestBody Users user){
        return null;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private static List<Users> createList() {
        List<Users> tempEmployees = new ArrayList<>();
        Users emp1 = new Users();
//        emp1.setName("emp1");
//        emp1.setDesignation("manager");
//        emp1.setEmpId("1");
//        emp1.setSalary(3000);
//
//        Employee emp2 = new Employee();
//        emp2.setName("emp2");
//        emp2.setDesignation("developer");
//        emp2.setEmpId("2");
//        emp2.setSalary(3000);
//        tempEmployees.add(emp1);
//        tempEmployees.add(emp2);
        return tempEmployees;
    }
}


