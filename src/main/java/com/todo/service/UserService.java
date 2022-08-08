package com.todo.service;

import java.io.IOException;
import java.sql.Timestamp;
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

import com.todo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.Interface.UserInterface;
import com.todo.model.User;

@Service
public class UserService implements UserInterface {
    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public boolean registerUser(User newUser) {
        boolean validationStatus = validateInput(newUser);
        if (!validationStatus) {
            return false;
        }
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (newUser.getEmailAddress().equals(user.getEmailAddress())) {
                logger.info("**********User account already exists with this email ! **********");
                return false;
            }
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newUser.setUserPassword(bCryptPasswordEncoder.encode(newUser.getUserPassword()));
        newUser.setEmailSentTime(new Timestamp(System.currentTimeMillis()));
        newUser.setEmailValidated(false);
        newUser.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
        newUser.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
        try {
            boolean status = sendEmail(newUser.getEmailAddress());
            if (status) {
                userRepository.save(newUser);
                return true;
            }
            return false;
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validateEmailLink(String email) {
        try {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                if (email.equals(user.getEmailAddress()) && !user.getEmailValidated()) {
                    user.setEmailValidated(true);
                    user.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
                    userRepository.save(user);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.info("**********Exception while validating email **********");
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean resendValidationEmail(String email) {
        try {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                if (email.equals(user.getEmailAddress()) && !user.getEmailValidated()
                        && user.getEmailSentTime().getTime() + 900000 <= System.currentTimeMillis()) {
                    user.setEmailValidated(false);
                    user.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
                    user.setEmailSentTime(new Timestamp(System.currentTimeMillis()));
                    userRepository.save(user);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidEmailAddress(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean validateInput(User newUser) {
        if (!isValidEmailAddress(newUser.getEmailAddress())) {
            return false;
        }
        if (newUser.getfName().isEmpty() || newUser.getfName().isBlank()
                || newUser.getlName().isEmpty() || newUser.getlName().isBlank()
                || newUser.getEmailAddress().isEmpty() || newUser.getEmailAddress().isBlank()) {
            return false;
        }
        return true;
    }

    private boolean sendEmail(String email) throws AddressException, MessagingException, IOException {
        try {
            String url = "http://localhost:8081/v1/validateEmail?email=" + email;
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("huskydevportal@gmail.com", "wbppdzxlwdnhlaxa");
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
            logger.info("**********Error Sending email !! **********");
            return false;
        }
    }

}
