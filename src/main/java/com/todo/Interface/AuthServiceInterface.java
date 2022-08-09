package com.todo.Interface;

import javax.servlet.http.HttpServletRequest;

public interface AuthServiceInterface {
    Boolean validateBasicAuthentication(String authorization);

    String getUserName(HttpServletRequest request);
}
