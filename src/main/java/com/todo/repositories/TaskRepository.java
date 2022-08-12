package com.todo.repositories;

import com.todo.model.Task;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Integer> {
  @Query("Select t from Task t where t.mList.list_Id=?1")
  List<Task> getTasks(Integer listId);

  @Query("Select t from Task t where t.task_Id=?1")
  Task findByTaskId(Integer task_id);

}
