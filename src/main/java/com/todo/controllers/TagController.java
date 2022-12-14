package com.todo.controllers;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;
import com.todo.Interface.TagInterface;
import com.todo.model.Tag;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")

public class TagController {
    @Autowired
    TagInterface tagInterface;
    @Autowired
    AuthServiceInterface AuthService;

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @NeedLogin
    @RequestMapping(value = "/tag", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Tag> createTag(HttpServletRequest request, @RequestBody Tag newTag) {
        try {
            if (newTag.getTagName().equals(null) || newTag.getTagName().equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            String loggedInUser = AuthService.getUserName(request);
            Tag status = tagInterface.createTag(loggedInUser, newTag);
            if (status != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(status);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            logger.info("**********Exception while creating New Tag**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/tag", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Tag>> getTags(HttpServletRequest request) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            List<Tag> tagList = tagInterface.getTags(loggedInUser);
            return ResponseEntity.status(HttpStatus.OK).body(tagList);
        } catch (Exception exception) {
            logger.info("**********Exception while retrieving User tags**********");
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/updateTag", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Tag> updateTag(@RequestBody Tag updatedTag, HttpServletRequest request) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            Tag status = tagInterface.updateTag(updatedTag, loggedInUser);
            if (status != null) {
                return ResponseEntity.status(HttpStatus.OK).body(status);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while updating list details **********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @NeedLogin
    @RequestMapping(value = "/deleteTag", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Tag> deleteTag(HttpServletRequest request, @RequestParam("tag_Id") Integer tagId) {
        try {
            String loggedInUser = AuthService.getUserName(request);
            boolean status = tagInterface.deleteTag(tagId, loggedInUser);
            if (status) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while deleting List**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/tagTask", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Tag> attachTagToTask(HttpServletRequest request, @RequestBody Map<String, String> taskData) {
        try {
            if (taskData.get("taskId") == null || taskData.get("taskId").isEmpty()
                    || taskData.get("taskId").isBlank() || taskData.get("tagId") == null
                    || taskData.get("tagId").isEmpty()) {
                logger.info("**********Task is Required**********");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            String loggedInUser = AuthService.getUserName(request);
            boolean status = tagInterface.taskTag(taskData.get("tagId"), taskData.get("taskId"), loggedInUser);
            if (status) {
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            logger.info("**********Exception while creating New Tag**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/removeTagFromTask", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Tag> removeTagFromTask(HttpServletRequest request,
            @RequestBody Map<String, String> taskData) {
        try {
            if (taskData.get("taskId") == null || taskData.get("taskId").isEmpty()
                    || taskData.get("taskId").isBlank() || taskData.get("tagId") == null
                    || taskData.get("tagId").isEmpty()) {
                logger.info("**********Task is Required**********");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            String loggedInUser = AuthService.getUserName(request);
            boolean status = tagInterface.deleteTagFromTask(taskData.get("tagId"), taskData.get("taskId"),
                    loggedInUser);
            if (status) {
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            logger.info("**********Exception while deleting  Tag**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}