package com.todo.Interface;

import com.todo.model.Task;
import com.todo.model.User;
import java.util.List;

public interface TaskInterface {
    boolean createTask(Task task, Integer listId);

  List<Task> getTask(Integer listId);

  boolean deleteTask(Task task);
}
