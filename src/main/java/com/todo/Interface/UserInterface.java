package com.todo.Interface;

import com.todo.model.User;

public interface UserInterface {
    boolean registerUser(User user);

    boolean validateEmailLink(String email);

    boolean resendValidationEmail(User email);

    boolean deleteUser(String email);

    User getUserDetails(String email);

    boolean updateUser(String loggedInUser, User user);

    boolean sendUpdatedEmailVerificationLink(String oldLink, String newLink);

    boolean updateVerificationEmailAddress(String oldLink, String newLink);
}
