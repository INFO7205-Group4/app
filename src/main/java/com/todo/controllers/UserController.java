package com.todo.controllers;


import com.todo.model.Users;
import com.todo.repositories.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private List<Users> users = createList();

    //@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    @GetMapping("/users")
    public Object getUser(Authentication authentication) {
        return null;
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

    @PostMapping
    public String create(@RequestBody Users user) {
        users.add(user);
        Users testUser = userRepository.findByEmailAddress(user.getEmailAddress());
        System.out.println(user);
        return "Success";
    }


    @DeleteMapping
    public void deleteuUser(Authentication authentication){

    }

    @PutMapping
    public Users updateUser(Authentication authentication, @RequestBody Users user){
        return null;
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


