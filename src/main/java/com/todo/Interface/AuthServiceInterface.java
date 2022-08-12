package com.todo.Interface;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestEntity.BodyBuilder;

public interface AuthServiceInterface {
    ResponseEntity validateBasicAuthentication(String authorization);

    String getUserName(HttpServletRequest request);
}
