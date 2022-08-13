package com.todo.controllers;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;
import com.todo.Interface.ReminderInterface;
import com.todo.model.Reminder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class ReminderController {

  @Autowired
  ReminderInterface reminderInterface;
  @Autowired
  AuthServiceInterface AuthService;

  private static final Logger logger = LoggerFactory.getLogger(ReminderController.class);


  @NeedLogin
  @RequestMapping(value = "/task/reminder", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder, @RequestParam("taskId")  Integer taskId) {
    try {
      if(String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")){
        logger.info("**********No Task ID value **********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if(reminder.getReminder_DateTime() == null || reminder.getReminder_DateTime().equals("")){
        logger.info("**********No Reminder date time value **********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if(!isValidTimeStamp(reminder.getReminder_DateTime().toString())){
        logger.info("**********Invalid/Incorrect Reminder Date Time value **********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if(reminder.getReminder_DateTime().before(new Timestamp(System.currentTimeMillis()))){
        logger.info("********** Reminder date time past value error**********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      Reminder status = reminderInterface.create(reminder, taskId);
      if (status != null) {
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    } catch (Exception exception) {
      logger.info("**********Exception while creating New Reminder**********");
      exception.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @NeedLogin
  @RequestMapping(value = "/task/reminder", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List<Reminder>> getReminder(@RequestParam("taskId") Integer taskId) {
    try {
      if(String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      List<Reminder> reminderList = reminderInterface.getReminder(taskId);
      return ResponseEntity.status(HttpStatus.OK).body(reminderList);
    } catch (Exception exception) {
      logger.info("**********Exception while retrieving Reminder**********");
      exception.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @NeedLogin
  @RequestMapping(value = "/updateReminder", method = RequestMethod.PATCH, produces = "application/json")
  public ResponseEntity<Object> updateReminder(@RequestBody Reminder updatedReminder, @RequestParam("taskId") Integer taskId) {
    try {
      if(String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")){
        logger.info("**********No Task ID value **********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if(updatedReminder.getReminder_Id() == null || String.valueOf(updatedReminder.getReminder_Id()).equals("")){
        logger.info("**********No Reminder ID value **********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if(updatedReminder.getReminder_DateTime() == null  || updatedReminder.getReminder_DateTime().equals("")){
        logger.info("**********No Reminder ---> null or empty value**********");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nothing to update");
      }
      if(!isValidTimeStamp(updatedReminder.getReminder_DateTime().toString())){
        logger.info("**********Invalid/Incorrect Reminder Date Time value **********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      if(updatedReminder.getReminder_DateTime().before(new Timestamp(System.currentTimeMillis()))){
        logger.info("********** Reminder date time past value error**********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      Reminder status = reminderInterface.updateReminder(updatedReminder, taskId);
      if (status != null) {
        return ResponseEntity.status(HttpStatus.OK).body(status);
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
      logger.info("**********Exception while updating Reminder details **********");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

  }

  @NeedLogin
  @RequestMapping(value = "/deleteReminder", method = RequestMethod.DELETE, produces = "application/json")
  public ResponseEntity<String> deleteReminder(@RequestParam("reminderId") Integer reminderId) {
    try {
      if(String.valueOf(reminderId) == null || String.valueOf(reminderId).equals("")){
        logger.info("**********Empty or null value for reminderId **********");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      boolean status = reminderInterface.deleteReminder(reminderId);
      if (status) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted successfully!");
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
      logger.info("**********Exception while deleting Reminder**********");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  private boolean isValidTimeStamp(String timeStamp) {
    SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      dateFormat.parse(timeStamp);
      return true;
    } catch (ParseException e) {
      logger.info("**********Exception --> Invalid Reminder timestamp **********");
      return false;
    }
  }

}
