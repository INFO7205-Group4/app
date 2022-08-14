package com.todo.controllers;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;
import com.todo.Interface.ReminderInterface;
import com.todo.model.Reminder;
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
import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<Reminder> createReminder(HttpServletRequest request, @RequestBody Reminder reminder,
            @RequestParam("taskId") Integer taskId) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            Reminder status = reminderInterface.create(loggedInUser, reminder, taskId);
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
    public ResponseEntity<List<Reminder>> getReminder(HttpServletRequest request,
            @RequestParam("taskId") Integer taskId) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            List<Reminder> reminderList = reminderInterface.getReminder(loggedInUser, taskId);
            if (reminderList != null) {
                return ResponseEntity.status(HttpStatus.OK).body(reminderList);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception exception) {
            logger.info("**********Exception while retrieving Reminder**********");
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/updateReminder", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Object> updateReminder(HttpServletRequest request, @RequestBody Reminder updatedReminder,
            @RequestParam("taskId") Integer taskId) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            Reminder status = reminderInterface.updateReminder(loggedInUser, updatedReminder, taskId);
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
    public ResponseEntity<String> deleteReminder(HttpServletRequest request,
            @RequestParam("reminderId") Integer reminderId) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            boolean status = reminderInterface.deleteReminder(loggedInUser, reminderId);
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

}