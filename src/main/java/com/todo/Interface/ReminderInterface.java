package com.todo.Interface;

import com.todo.model.Reminder;
import java.util.List;

public interface ReminderInterface {

    Reminder create(String loggedInUser, Reminder reminder, Integer taskId);

    List<Reminder> getReminder(String loggedInUser, Integer taskId);

    Reminder updateReminder(String loggedInUser, Reminder updatedReminder, Integer taskId);

    boolean deleteReminder(String loggedInUser, Integer reminderId);
}