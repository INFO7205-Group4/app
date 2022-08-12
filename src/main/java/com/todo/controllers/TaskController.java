package com.todo.controllers;

import com.todo.Interface.NeedLogin;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.TaskInterface;
import com.todo.model.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @NeedLogin
    @RequestMapping(value = "/list/task", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Map<String, String>> createTasks(@RequestBody Map<String, String> taskData,
            HttpServletRequest request) {
        try {
            boolean status = Task.createTask(taskData);
            if (status) {
                return ResponseEntity.status(HttpStatus.CREATED).body(taskData);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            logger.info("**********Exception while creating New Task**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/list/task", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Task>> getTasksForParticularUser(@RequestBody Integer listId) {
        try {
            java.util.List<Task> tasks = Task.getTasksForParticularUser(listId);
            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        } catch (Exception e) {
            logger.info("**********Exception while retrieving Tasks**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "list/deleteTask", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<com.todo.model.List> deleteTask(@RequestBody com.todo.model.Task task) {
        try {
            boolean status = Task.deleteTask(task);
            if (status) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while deleting List**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
