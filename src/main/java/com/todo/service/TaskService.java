package com.todo.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import com.todo.Interface.TaskInterface;
import com.todo.model.List;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repositories.ListRepository;
import com.todo.repositories.TaskRepository;
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
    public boolean createTask(Map<String, String> taskData) {
        try {
            if (taskData.get("taskName") == null || taskData.get("taskName").isEmpty()
                    || taskData.get("taskName").isBlank()) {
                logger.info("**********Task Name is Required**********");
                return false;
            } else if (taskData.get("list_Id") == null || taskData.get("list_Id").isEmpty()
                    || taskData.get("list_Id").isBlank()) {
                logger.info("**********List Id is Required**********");
                return false;
            }
            Task task = validateInput(taskData);
            if (task.getTaskName() == null) {
                logger.info("**********Exception while creating Task **********");
                return false;
            }
            List list = listRepository.findByListId(Integer.parseInt(taskData.get("list_Id")));
            task.setmList(list);
            taskRepository.save(task);
            logger.info("**********Task saved successfully **********");
            return true;
        } catch (Exception exception) {
            logger.info("**********Exception while creating Task **********");
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public java.util.List<Task> getTasksForParticularUser(Integer listId) {
        try {
            java.util.List<Task> tasks = taskRepository.getTasks(listId);
            logger.info("********** Task retrieved successfully **********");
            return tasks;
        } catch (Exception exception) {
            logger.info("**********Exception while getting task **********");
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteTask(Task task) {
        try {
            if (task.getTask_Id() == null) {
                logger.info("**********Task Id is Required**********");
                return false;
            }
            Task existingTask = taskRepository.findByTaskId(task.getTask_Id());
            if (existingTask != null) {
                taskRepository.delete(existingTask);
                logger.info("**********Task deleted successfully **********");
                return true;
            }
            logger.info("**********Task does not exist **********");
            return false;
        } catch (Exception exception) {
            logger.info("**********Exception while deleting Task **********");
            exception.printStackTrace();
            return false;
        }
    }

    private Task validateInput(Map<String, String> taskData) {
        try {
            Task task = new Task();
            task.setTaskName(taskData.get("taskName"));
            if (taskData.get("taskSummary") != null) {
                task.setTaskSummary(taskData.get("taskSummary"));
            }
            if (taskData.get("dueDate") != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                task.setDueDate(new Timestamp(simpleDateFormat.parse(taskData.get("dueDate")).getTime()));
            }
            if (taskData.get("taskPriority") != null) {
                task.setTaskPriority(Short.parseShort(taskData.get("taskPriority")));
            }
            if (taskData.get("taskState") != null) {
                task.setTaskState(Short.parseShort(taskData.get("taskState")));
            }
            task.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
            task.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
            return task;
        } catch (Exception exception) {
            logger.info("**********Exception while validating input **********");
            exception.printStackTrace();
            return new Task();
        }
    }

}