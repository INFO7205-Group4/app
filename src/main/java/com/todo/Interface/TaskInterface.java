package com.todo.Interface;

import com.todo.model.Task;
import com.todo.model.User;

public interface TaskInterface {
    boolean createTask(Task task, String email, Integer listId);

    User getUserDetails(String email);
}
