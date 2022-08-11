package com.todo.service;

import java.sql.Timestamp;

import com.todo.Interface.ListInterface;
import com.todo.Interface.TaskInterface;
import com.todo.Interface.UserInterface;
import com.todo.model.List;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repositories.ListRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements TaskInterface {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserInterface userInterface;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ListRepository listRepository;
    @Autowired
    TaskInterface taskInterface;

    final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Override
    public boolean createTask(Task newTask, String email, Integer listId) {

        try {

            User user = userInterface.getUserDetails(email);
            List list = listRepository.findByListId(listId);
            // List list = new List(1,"listname",new
            // Timestamp(System.currentTimeMillis()),new
            // Timestamp(System.currentTimeMillis()),user);

            newTask.setmList(list);
            newTask.setTask_Name(newTask.getTask_Name());
            newTask.setTask_Summary(newTask.getTask_Summary());
            newTask.setTask_Priority(newTask.getTask_Priority());
            newTask.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
            newTask.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
            newTask.setDueDate(newTask.getDueDate());
            newTask.setTask_Priority(newTask.getTask_Priority());
            // newTask.setmUsers(user);
            // newList.setmUsers(user);
            try {
                taskRepository.save(newTask);
                logger.info("**********Task saved successfully **********");
                return true;
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.info("**********Exception while creating the task ! **********");
                return false;
            }

        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public User getUserDetails(String email) {
        try {
            User getUser = userRepository.findByEmailAddress(email);
            if (getUser != null) {
                return getUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        logger.info("**********User does not exist **********");
        return null;
    }
}