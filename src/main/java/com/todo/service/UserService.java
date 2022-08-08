package com.todo.service;

import java.sql.Timestamp;
import java.util.List;

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
        userRepository.save(newUser);
        return true;
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

    private boolean sendEmail(String email) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
