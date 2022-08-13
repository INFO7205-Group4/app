package com.todo.Interface;

import com.todo.model.Reminder;
import java.util.List;

public interface ReminderInterface {

  Reminder create(Reminder reminder, Integer taskId);

  List<Reminder> getReminder(Integer taskId);

  Reminder updateReminder(Reminder updatedReminder, Integer taskId);

  boolean deleteReminder(Integer reminderId);
}
