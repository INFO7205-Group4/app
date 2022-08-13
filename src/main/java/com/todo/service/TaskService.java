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
    @Autowired
    UserRepository userRepository;

    final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Override
    public boolean createTask(String loggedInUser, Map<String, String> taskData) {
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
            java.util.List<List> allList = getAllListForParticularUser(loggedInUser);
            if (allList.isEmpty()) {
                logger.info("**********No List Found**********");
                return false;
            }
            boolean isListIdValid = false;
            List oldList = new List();
            for (List list : allList) {
                if (list.getListId().equals(Integer.parseInt(taskData.get("list_Id")))) {
                    isListIdValid = true;
                    oldList = list;
                }
            }
            if (!isListIdValid) {
                logger.info("**********List Id does not belong to this user**********");
                return false;
            }
            Task task = validateInput(taskData);
            if (task.getTaskName() == null) {
                logger.info("**********Exception while creating Task **********");
                return false;
            }
            // List list =
            // listRepository.findByListId(Integer.parseInt(taskData.get("list_Id")));
            task.setmList(oldList);
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
    public java.util.List<Task> getTasksForParticularUser(String loggedInUser, Integer listId) {
        try {
            List userList = listRepository.findByListId(listId);
            if (userList == null) {
                logger.info("**********No List Found**********");
                return null;
            }
            if (userList.getmUsers().getEmailAddress().equals(loggedInUser)) {
                java.util.List<Task> tasks = taskRepository.getTasks(listId);
                logger.info("********** Task retrieved successfully **********");
                return tasks;
            } else {
                logger.info("********** List does not belong to this user **********");
                return null;
            }

        } catch (Exception exception) {
            logger.info("**********Exception while getting task **********");
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteTask(String loggedInUser, Task task) {
        try {
            if (task.getTask_Id() == null) {
                logger.info("**********Task Id is Required**********");
                return false;
            }
            java.util.List<List> allList = getAllListForParticularUser(loggedInUser);
            if (allList.isEmpty()) {
                logger.info("**********No List Found**********");
                return false;
            }
            Task existingTask = taskRepository.findByTaskId(task.getTask_Id());
            if (existingTask == null) {
                logger.info("**********No Task Found**********");
                return false;
            }
            for (List list : allList) {
                if (list.getListId().equals(existingTask.getmList().getListId())) {
                    taskRepository.delete(existingTask);
                    logger.info("**********Task deleted successfully **********");
                    return true;
                }
            }
            logger.info("**********Task does not exist for this user **********");
            return false;
        } catch (Exception exception) {
            logger.info("**********Exception while deleting Task **********");
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTask(String loggedInUser, Task task) {
        try {
            if (task.getTask_Id() == null) {
                logger.info("**********Task Id is Required**********");
                return false;
            }
            java.util.List<List> allList = getAllListForParticularUser(loggedInUser);
            if (allList.isEmpty()) {
                logger.info("**********No List Found**********");
                return false;
            }

            Task existingTask = taskRepository.findByTaskId(task.getTask_Id());
            if (existingTask == null) {
                logger.info("**********No Task Found**********");
                return false;
            }
            for (List list : allList) {
                if (list.getListId().equals(existingTask.getmList().getListId())) {
                    existingTask.setTaskName(
                            task.getTaskName() != null && !task.getTaskName().isEmpty()
                                    && !task.getTaskName().isBlank()
                                            ? task.getTaskName()
                                            : existingTask.getTaskName());
                    existingTask.setTaskSummary(
                            task.getTaskSummary() != null && !task.getTaskSummary().isEmpty()
                                    && !task.getTaskSummary().isBlank()
                                            ? task.getTaskSummary()
                                            : existingTask.getTaskSummary());
                    if (task.getDueDate() != null) {
                        existingTask.setDueDate(task.getDueDate());
                        if (task.getDueDate().before(new Timestamp(System.currentTimeMillis()))) {
                            existingTask.setTaskState((short) 2);
                        }
                        if (task.getDueDate().after(new Timestamp(System.currentTimeMillis()))) {
                            existingTask.setTaskState((short) 0);
                        }
                    } else {
                        existingTask.setDueDate(existingTask.getDueDate());
                        if (String.valueOf(task.getTaskState()) != null
                                && (existingTask.getTaskState() == 2 || existingTask.getTaskState() == 0)
                                && task.getTaskState() == 1) {
                            existingTask.setTaskState((short) 1);
                        }
                    }
                    existingTask.setTaskPriority(
                            String.valueOf(task.getTaskPriority()) != null
                                    && !String.valueOf(task.getTaskPriority()).equals(null)
                                            ? task.getTaskPriority()
                                            : existingTask.getTaskPriority());
                    existingTask.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
                    taskRepository.save(existingTask);
                    logger.info("**********Task updated successfully **********");
                    return true;
                }

            }
            logger.info("**********Task does not exist for this user **********");
            return false;

        } catch (Exception e) {
            logger.info("**********Task failed to update**********");
            return false;
        }
    }

    @Override
    public boolean moveTask(String loggedInUser, Map<String, Integer> task) {
        try {
            if (task.get("task_Id") == null) {
                logger.info("**********Task Id is Required**********");
                return false;
            }
            if (task.get("list_Id") == null) {
                logger.info("**********List Id is Required**********");
                return false;
            }
            java.util.List<List> allList = getAllListForParticularUser(loggedInUser);
            if (allList.isEmpty()) {
                logger.info("**********No List Found**********");
                return false;
            }

            Task existingTask = taskRepository.findByTaskId(task.get("task_Id"));
            if (existingTask == null) {
                logger.info("**********No Task Found**********");
                return false;
            }
            if (existingTask.getmList().getListId().equals(task.get("list_Id"))) {
                logger.info("**********Task is already in this list**********");
                return false;
            }
            for (List list : allList) {
                if (list.getListId().equals(existingTask.getmList().getListId())) {
                    List newList = listRepository.findByListId(task.get("list_Id"));
                    if (newList == null) {
                        logger.info("**********No List Found**********");
                        return false;
                    }
                    existingTask.setmList(newList);
                    existingTask.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
                    taskRepository.save(existingTask);

                    logger.info("**********Task moved successfully **********");
                    return true;
                }

            }
            logger.info("**********Task does not exist for this user **********");
            return false;
        } catch (Exception e) {
            logger.info("**********Task failed to move**********");
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
                if (task.getDueDate().before(new Timestamp(System.currentTimeMillis()))) {
                    task.setTaskState((short) 2);
                } else if (task.getDueDate().after(new Timestamp(System.currentTimeMillis()))) {
                    task.setTaskState((short) 0);
                }
            }
            if (taskData.get("taskPriority") != null) {
                task.setTaskPriority(Short.parseShort(taskData.get("taskPriority")));
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

    private java.util.List<List> getAllListForParticularUser(String loggedInUser) {
        User user = userRepository.findByEmailAddress(loggedInUser);
        java.util.List<List> userList = listRepository.findList(user.getUserId());
        return userList;
    }

}