package com.todo.controllers;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.CommentInterface;
import com.todo.Interface.NeedLogin;
import com.todo.model.Comment;
import java.util.List;
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
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/user")
public class CommentController {

    @Autowired
    CommentInterface commentInterface;
    @Autowired
    AuthServiceInterface AuthService;

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @NeedLogin
    @RequestMapping(value = "/task/comment", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Comment> createComment(HttpServletRequest request, @RequestBody Comment comment,
            @RequestParam("taskId") Integer taskId) {
        try {

            String email = AuthService.getUserName(request);
            if (String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")) {
                logger.info("**********No Task ID value **********");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            if (comment.getComment() == null || comment.getComment().equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Comment status = commentInterface.create(comment, taskId, email);
            if (status != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(status);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception exception) {
            logger.info("**********Exception while creating New Comment**********");
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/task/comment", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Comment>> getComment(HttpServletRequest request,
            @RequestParam("taskId") Integer taskId) {
        try {

            String email = AuthService.getUserName(request);
            if (String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            List<Comment> commentList = commentInterface.getComment(taskId, email);
            return ResponseEntity.status(HttpStatus.OK).body(commentList);
        } catch (Exception exception) {
            logger.info("**********Exception while retrieving Comments**********");
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/updateComment", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Object> updateComment(HttpServletRequest request, @RequestBody Comment updatedComment,
            @RequestParam("taskId") Integer taskId) {
        try {
            String email = AuthService.getUserName(request);
            if (String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")) {
                logger.info("**********No Task ID value **********");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            if (updatedComment.getComment() == null) {
                logger.info("**********No Comment  value **********");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            if (updatedComment.getComment_Id() == null) {
                logger.info("**********No Comment ID value**********");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Comment status = commentInterface.updateComment(updatedComment, taskId, email);
            if (status != null) {
                return ResponseEntity.status(HttpStatus.OK).body(status);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while updating Comment details **********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @NeedLogin
    @RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteComment(@RequestParam("commentId") Integer commentId) {
        try {
            if (String.valueOf(commentId) == null || String.valueOf(commentId).equals("")) {
                logger.info("**********Empty or null value for commentId **********");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            boolean status = commentInterface.deleteComment(commentId);
            if (status) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted successfully!");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.info("**********Exception while deleting List**********");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}