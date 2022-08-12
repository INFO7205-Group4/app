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
    ListRepository listRepository;

    final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Override
    public boolean createTask(Task task, Integer listId) {
        try {
            List list = listRepository.findByListId(listId);
            task.setmList(list);
            task.setDueDate(new Timestamp(System.currentTimeMillis()));
            task.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
            task.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
            try {
                taskRepository.save(task);
                logger.info("**********Task saved successfully **********");
                return true;
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.info("**********Exception while creating the task ! **********");
                return false;
            }
        } catch (Exception exception) {
            logger.info("**********Exception while creating Task **********");
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * @param listId 
     * @return
     */
    @Override
    public java.util.List<Task> getTask(Integer listId) {
        try {
            java.util.List<Task> tasks = taskRepository.getTasks(listId);
            logger.info("********** Task retrieved successfully **********");
            return tasks;
        } catch(Exception exception){
            logger.info("**********Exception while getting task **********");
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * @param task
     * @return
     */
    @Override
    public boolean deleteTask(Task task) {
        try {
            Task deleteTask = taskRepository.findByTaskId(task.getTask_Id());
            if (deleteTask != null) {
                taskRepository.delete(deleteTask);
                logger.info("**********Task deleted successfully **********");
                return true;
            }
            return false;
        }catch (Exception exception){
            logger.info("**********Exception while deleting Task **********");
            exception.printStackTrace();
            return false;
    }
}

}