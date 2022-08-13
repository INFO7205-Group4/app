package com.todo.service;

import com.todo.Interface.ReminderInterface;
import com.todo.model.Comment;
import com.todo.model.Reminder;
import com.todo.model.Task;
import com.todo.repositories.ReminderRepository;
import com.todo.repositories.TaskRepository;
import java.sql.Timestamp;
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


  final Logger logger = LoggerFactory.getLogger(ReminderService.class);
  /**
   * @param reminder 
   * @param taskId
   * @return
   */
  @Override
  public Reminder create(Reminder reminder, Integer taskId) {
    try{
      Task task = taskRepository.findByTaskId(taskId);
      if (task == null) {
        return null;
      }
      List<Timestamp> totalReminders = reminderRepository.getReminderDateTimeByTaskId(taskId);
      if(totalReminders.size() >= 5){
        logger.info("**********Max Reminder Limit reached**********");
        return null;
      }
      if(totalReminders.contains(reminder.getReminder_DateTime())){
        logger.info("**********Duplicate Reminder create error**********");
        return null;
      }
      reminder.setrTask(task);
      reminder.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
      reminder.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
      reminderRepository.save(reminder);
      logger.info("**********Created reminder successfully **********");
      return reminder;
    }catch (Exception exception){
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
  public List<Reminder> getReminder(Integer taskId) {
    try {
      Task task = taskRepository.findByTaskId(taskId);
      if (task == null) {
        return null;
      }
      List<Reminder> reminders = reminderRepository.getRemindersByTaskId(taskId);
      return reminders;
    } catch (Exception exception){
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
  public Reminder updateReminder(Reminder updatedReminder, Integer taskId) {
    try{
      Task task = taskRepository.findByTaskId(taskId);
      if (task == null) {
        logger.info("**********No task found for updating reminder error **********");
        return null;
      }
      Reminder reminder = reminderRepository.findByReminderId(updatedReminder.getReminder_Id());
      if (reminder == null) {
        logger.info("**********No reminder to update error **********");
        return null;
      }
      List<Timestamp> totalReminders = reminderRepository.getReminderDateTimeByTaskId(taskId);
      if(totalReminders.contains(updatedReminder.getReminder_DateTime())){
        logger.info("**********Duplicate Reminder update error**********");
        return null;
      }
      reminder.setReminder_DateTime(updatedReminder.getReminder_DateTime());
      reminder.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
      reminderRepository.save(reminder);
      logger.info("**********Updated reminder successfully **********");
      return reminder;
    }catch (Exception exception){
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
  public boolean deleteReminder(Integer reminderId) {
    try{
      Reminder reminder = reminderRepository.findByReminderId(reminderId);
      if(reminder == null){
        logger.info("**********No reminder to delete error **********");
        return false;
      }
      reminderRepository.delete(reminder);
      logger.info("**********Deleted reminder successfully **********");
      return true;
    } catch (Exception exception){
      exception.printStackTrace();
      logger.info("**********Exception while deleting the reminder! **********");
      return false;
    }
  }
}
