package com.todo.controllers;

import com.todo.model.Task;
import javax.servlet.http.HttpServletRequest;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.TaskInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/v1")
public class TaskController {
    @Autowired
    TaskInterface Task;
    @Autowired
    AuthServiceInterface AuthService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @RequestMapping(value = "/list/task", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<com.todo.model.Task> createList(@RequestBody Task newTask, HttpServletRequest request,
            @RequestParam Integer listId) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            boolean status = Task.createTask(newTask, loggedInUser, listId);
            if (status) {
                return new ResponseEntity<Task>(newTask, HttpStatus.CREATED);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            logger.info("**********Exception while creating New User**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
