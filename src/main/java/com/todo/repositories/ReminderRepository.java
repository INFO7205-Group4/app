package com.todo.repositories;

import com.todo.model.Comment;
import com.todo.model.Reminder;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {

  @Query("Select r from Reminder r where r.rTask.task_Id=?1")
  List<Reminder> getRemindersByTaskId(Integer taskId);

  @Query("Select r.reminder_DateTime from Reminder r where r.rTask.task_Id=?1")
  List<Timestamp> getReminderDateTimeByTaskId(Integer taskId);

  @Query("Select r from Reminder r where r.reminder_Id=?1")
  Reminder findByReminderId(Integer reminderId);

}
