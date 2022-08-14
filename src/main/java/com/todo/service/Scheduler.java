package com.todo.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

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

import com.todo.model.Task;
import com.todo.model.User;
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

        @Scheduled(cron = "0 0 * * *")
        public void scheduleTask() {
                try {
                        allUsers();
                } catch (Exception e) {
                        logger.error("Error in Scheduler", e);
                }
        }

        private void allUsers() {
                try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate now = LocalDate.now();
                        List<User> allUsers = userRepository.findAll();
                        for (User user : allUsers) {
                                List<com.todo.model.List> allListForAUser = listRepository.findList(user.getUserId());
                                for (com.todo.model.List list : allListForAUser) {
                                        List<Task> allTaskInaList = taskRepository.getTasks(list.getListId());
                                        for (Task task : allTaskInaList) {
                                                List<Timestamp> allReminders = reminderRepository
                                                                .getReminderDateTimeByTaskId(task.getTask_Id());
                                                for (Timestamp reminder : allReminders) {
                                                        LocalDateTime localDateTime = reminder.toLocalDateTime();
                                                        logger.info("Reminder date" + dtf.format(localDateTime));
                                                        logger.info("Today date" + now.toString());
                                                        if (dtf.format(now).equals(dtf.format(localDateTime))) {
                                                                String content = task.getTaskName();
                                                                String email = user.getEmailAddress();
                                                                try {
                                                                        sendEmail(content, email);
                                                                } catch (MessagingException | IOException e) {
                                                                        e.printStackTrace();
                                                                }
                                                        }
                                                }

                                        }
                                }

                        }
                } catch (Exception e) {
                        logger.error("Error in Scheduler", e);
                }

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
                        msg.setSubject("Reminder Email Todo App");
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
