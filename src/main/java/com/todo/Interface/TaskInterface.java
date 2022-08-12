package com.todo.Interface;

import com.todo.model.Task;
import java.util.List;
import java.util.Map;

public interface TaskInterface {
  boolean createTask(Map<String, String> taskData);

  List<Task> getTasksForParticularUser(Integer listId);

  boolean deleteTask(Task task);
}
