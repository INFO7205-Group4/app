package com.todo.Interface;

import com.todo.model.Task;
import java.util.List;
import java.util.Map;

public interface TaskInterface {
  boolean createTask(String loggedInUser, Map<String, String> taskData);

  List<Task> getTasksForParticularUser(String loggedInUser, Integer listId);

  boolean deleteTask(String loggedInUser, Task task);

  boolean updateTask(String loggedInUser, Task task);

  boolean moveTask(String loggedInUser, Map<String, Integer> task);
}
