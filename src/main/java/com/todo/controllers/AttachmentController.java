package com.todo.controllers;

import javax.servlet.http.HttpServletRequest;

import com.todo.Interface.AttachmentInterface;
import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;
import com.todo.model.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/v1/user")
public class AttachmentController {

    @Autowired
    AttachmentInterface Attachment;

    @Autowired
    AuthServiceInterface AuthService;

    private static final Logger logger = LoggerFactory.getLogger(ListController.class);

    @NeedLogin
    @RequestMapping(value = "/task/attachment", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment newAttachment,
            HttpServletRequest request, @RequestParam Integer taskId) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            boolean status = Attachment.createAttachment(newAttachment, loggedInUser, taskId);
            if (status) {
                return new ResponseEntity<Attachment>(newAttachment, HttpStatus.CREATED);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            logger.info("**********Exception while creating New Attachment**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
