package com.todo.Interface;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface AuthServiceInterface {
    ResponseEntity validateBasicAuthentication(String authorization);

    String getUserName(HttpServletRequest request);
}
