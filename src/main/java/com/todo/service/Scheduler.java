package com.todo.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

import java.util.List;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todo.model.Reminder;
import com.todo.model.Task;
import com.todo.repositories.ListRepository;
import com.todo.repositories.ReminderRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Annotation
@Component
// Class
public class Scheduler {
        @Autowired
        ReminderRepository reminderRepository;

        @Autowired
        TaskRepository taskRepository;

        @Autowired
        ListRepository listRepository;

        @Autowired
        UserRepository userRepository;
        private static final Logger logger = LoggerFactory.getLogger(UserService.class);

        // Method
        // Cron Job runs every 2 hours
        // @Scheduled(cron = "0 0 */2 * * ?")
        @Scheduled(fixedRate = 120000)
        public void scheduleTask() {
                try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate now = LocalDate.now();
                        java.util.List<Reminder> allReminders = allReminders();
                        List<Reminder> filteredReminders = new ArrayList<>();
                        List<Task> allTasks = new ArrayList<>();
                        List<Task> filteredTasks = new ArrayList<>();
                        List<com.todo.model.List> allLists = new ArrayList<>();
                        List<com.todo.model.List> filteredList = new ArrayList<>();
                        List<com.todo.model.User> allUsers = new ArrayList<>();
                        List<com.todo.model.User> filteredUser = new ArrayList<>();

                        for (Reminder reminder : allReminders) {
                                LocalDateTime localDateTime = reminder.getReminderDateTime().toLocalDateTime();
                                logger.info("Reminder date" + dtf.format(localDateTime));

                                logger.info("Today date" + now.toString());
                                if (dtf.format(now).equals(dtf.format(localDateTime))) {
                                        filteredReminders.add(reminder);
                                }
                        }
                        if (filteredReminders.size() > 0) {
                                allTasks = taskRepository.findAll();
                        }
                        for (Reminder reminder : filteredReminders) {
                                if (allTasks.contains(reminder.getrTask())) {
                                        filteredTasks.add(reminder.getrTask());
                                }
                        }
                        if (filteredTasks.size() > 0) {
                                allLists = listRepository.findAll();
                        }
                        for (Task task : filteredTasks) {
                                if (allLists.contains(task.getmList())) {
                                        filteredList.add(task.getmList());
                                }
                        }
                        if (filteredList.size() > 0) {
                                allUsers = userRepository.findAll();
                        }
                        for (com.todo.model.List list : filteredList) {
                                if (allUsers.contains(list.getmUsers())) {
                                        filteredUser.add(list.getmUsers());
                                }
                        }
                        if (filteredTasks.size() == filteredUser.size()) {
                                for (int i = 0; i < filteredReminders.size(); i++) {
                                        try {
                                                String content = filteredTasks.get(i).getTaskName();
                                                String email = filteredUser.get(i).getEmailAddress();
                                                sendEmail(content, email);
                                                logger.info("Email sent to " + filteredUser.get(i).getEmailAddress());
                                        } catch (MessagingException | IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }
                } catch (Exception e) {
                        logger.error("Error in Scheduler", e);
                }
        }

        private List<Reminder> allReminders() {
                return reminderRepository.findAll();
        }

        private boolean sendEmail(String url, String email) throws AddressException, MessagingException, IOException {
                try {
                        Properties props = new Properties();
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.port", "587");

                        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication("huskydevportal@gmail.com",
                                                        "wbppdzxlwdnhlaxa");
                                }
                        });
                        Message msg = new MimeMessage(session);
                        msg.setFrom(new InternetAddress("huskydevportal@gmail.com", false));

                        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                        msg.setSubject("Verification Email Link Todo App");
                        msg.setContent(url, "text/html");

                        MimeBodyPart messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setContent(url, "text/html");

                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart);
                        msg.setContent(multipart);
                        Transport.send(msg);
                        return true;
                } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                }
        }
}
