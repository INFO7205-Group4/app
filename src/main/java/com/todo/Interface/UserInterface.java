package com.todo.Interface;

import com.todo.model.User;

public interface UserInterface {
    boolean registerUser(User user);

    boolean validateEmailLink(String email);

    boolean resendValidationEmail(String email);

    boolean deleteUser(String email);

    User getUserDetails(String email);

    boolean updateUser(String loggedInUser, User user);
}
