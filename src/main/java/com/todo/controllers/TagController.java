package com.todo.controllers;

import javax.servlet.http.HttpServletRequest;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;
import com.todo.Interface.TagInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.todo.model.Tag;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/v1")

public class TagController {
    @Autowired
    TagInterface Tag;
    @Autowired
    AuthServiceInterface AuthService;
    @Autowired
    TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @NeedLogin
    @RequestMapping(value = "/tag", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Tag> createTag(HttpServletRequest request, @RequestBody Tag newTag,
            @RequestParam Integer taskId) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            boolean status = Tag.createTag(loggedInUser, newTag, taskId);
            if (status) {
                return new ResponseEntity<Tag>(newTag, HttpStatus.CREATED);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            logger.info("**********Exception while creating New Tag**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
