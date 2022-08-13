package com.todo.service;

import com.todo.Interface.ReminderInterface;
import com.todo.model.Reminder;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repositories.ListRepository;
import com.todo.repositories.ReminderRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderService implements ReminderInterface {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ReminderRepository reminderRepository;
    @Autowired
    ListRepository listRepository;
    @Autowired
    UserRepository userRepository;

    final Logger logger = LoggerFactory.getLogger(ReminderService.class);

    /**
     * @param reminder
     * @param taskId
     * @return
     */
    @Override
    public Reminder create(String loggedInUser, Reminder reminder, Integer taskId) {
        try {
            boolean authorizedUser = validateStatus(taskId, loggedInUser);
            if (authorizedUser == false) {
                logger.info("Task does not belong to this user");
                return null;
            }
            boolean status = validateInput(reminder, taskId);
            if (!status) {
                logger.info("Invalid input");
                return null;
            }
            Task task = taskRepository.findByTaskId(taskId);
            if (task == null) {
                logger.info("Task does not exist");
                return null;
            }
            List<Timestamp> totalReminders = reminderRepository.getReminderDateTimeByTaskId(taskId);
            if (totalReminders.size() >= 5) {
                logger.info("**********Max Reminder Limit reached**********");
                return null;
            }
            if (totalReminders.contains(reminder.getReminderDateTime())) {
                logger.info("**********Duplicate Reminder create error**********");
                return null;
            }
            reminder.setrTask(task);
            reminder.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
            reminder.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
            reminderRepository.save(reminder);
            logger.info("**********Created reminder successfully **********");
            return reminder;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while retrieving the task reminder! **********");
            return null;
        }
    }

    /**
     * @param taskId
     * @return
     */
    @Override
    public List<Reminder> getReminder(String loggedInUser, Integer taskId) {
        try {
            boolean authorizedUser = validateStatus(taskId, loggedInUser);
            if (authorizedUser == false) {
                logger.info("Task does not belong to this user");
                return null;
            }
            if (String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")) {
                logger.info("**********Invalid input**********");
                return null;
            }
            Task task = taskRepository.findByTaskId(taskId);
            if (task == null) {
                return null;
            }
            List<Reminder> reminders = reminderRepository.getRemindersByTaskId(taskId);
            return reminders;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while retrieving the task reminders! **********");
            return null;
        }
    }

    /**
     * @param updatedReminder
     * @param taskId
     * @return
     */
    @Override
    public Reminder updateReminder(String loggedInUser, Reminder updatedReminder, Integer taskId) {
        try {
            boolean authorizedUser = validateStatus(taskId, loggedInUser);
            if (authorizedUser == false) {
                logger.info("Task does not belong to this user");
                return null;
            }
            boolean status = validateInput(updatedReminder, taskId);
            if (!status) {
                logger.info("Invalid input");
                return null;
            }
            if (updatedReminder.getReminder_Id() == null
                    || String.valueOf(updatedReminder.getReminder_Id()).equals("")) {
                logger.info("**********Invalid input**********");
                return null;
            }
            Task task = taskRepository.findByTaskId(taskId);
            if (task == null) {
                logger.info("**********No task found **********");
                return null;
            }
            Reminder reminder = reminderRepository.findByReminderId(updatedReminder.getReminder_Id());
            if (reminder == null) {
                logger.info("**********No reminder found**********");
                return null;
            }
            List<Timestamp> totalReminders = reminderRepository.getReminderDateTimeByTaskId(taskId);
            if (totalReminders.contains(updatedReminder.getReminderDateTime())) {
                logger.info("**********Duplicate Reminder update error**********");
                return null;
            }
            reminder.setReminderDateTime(updatedReminder.getReminderDateTime());
            reminder.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
            reminderRepository.save(reminder);
            logger.info("**********Updated reminder successfully **********");
            return reminder;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while updating the task reminder! **********");
            return null;
        }
    }

    /**
     * @param reminderId
     * @return
     */
    @Override
    public boolean deleteReminder(String loggedInUser, Integer reminderId) {
        try {
            if (String.valueOf(reminderId) == null || String.valueOf(reminderId).equals("")) {
                logger.info("**********Empty or null value for reminderId **********");
                return false;
            }
            Reminder reminder = reminderRepository.findByReminderId(reminderId);
            if (reminder == null) {
                logger.info("**********No reminder found **********");
                return false;
            }
            reminderRepository.delete(reminder);
            logger.info("**********Deleted reminder successfully **********");
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while deleting the reminder! **********");
            return false;
        }
    }

    private boolean validateInput(Reminder reminder, Integer taskId) {
        if (reminder.getReminderDateTime() == null || reminder.getReminderDateTime().equals("")) {
            return false;
        }
        if (taskId == null || String.valueOf(taskId).equals("")) {
            return false;
        }
        if (!isValidTimeStamp(reminder.getReminderDateTime().toString())) {
            return false;
        }
        if (reminder.getReminderDateTime().before(new Timestamp(System.currentTimeMillis()))) {
            return false;
        }
        return true;
    }

    private boolean isValidTimeStamp(String timeStamp) {
        SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateFormat.parse(timeStamp);
            return true;
        } catch (Exception e) {
            logger.info("**********Exception --> Invalid Reminder timestamp **********");
            return false;
        }
    }

    private boolean validateStatus(Integer taskId, String loggedInUser) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task == null) {
            return false;
        }
        User user = userRepository.findByEmailAddress(loggedInUser);
        java.util.List<com.todo.model.List> userList = listRepository.findList(user.getUserId());
        for (com.todo.model.List list : userList) {
            if (list.getListId().equals(task.getmList().getListId())) {
                return true;
            }
        }
        return false;
    }
}