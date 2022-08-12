package com.todo.controllers;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.CommentInterface;
import com.todo.Interface.ListInterface;
import com.todo.Interface.NeedLogin;
import com.todo.model.Comment;
import com.todo.model.Task;
import com.todo.model.User;
import java.util.List;
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
@RequestMapping("/v1/user")
public class CommentController {

  @Autowired
  CommentInterface commentInterface;
  @Autowired
  AuthServiceInterface AuthService;

  private static final Logger logger = LoggerFactory.getLogger(CommentController.class);


  @NeedLogin
  @RequestMapping(value = "/task/comment", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<Object> createComment(@RequestBody Comment comment, @RequestParam Integer taskId) {
    try {
      if(String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty or null value for TaskId");
      }
      if(comment.getComment() == null || comment.getComment().equals("")){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nothing to create");
      }
      boolean status = commentInterface.create(comment);
      if (status) {
        return new ResponseEntity<Object>(comment, HttpStatus.CREATED);
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
  public ResponseEntity<List<Comment>> getComment(@RequestParam("taskId") Integer taskId) {
    try {
      if(String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      List<Comment> commentList = commentInterface.getComment(taskId);
      return ResponseEntity.status(HttpStatus.OK).body(commentList);
    } catch (Exception exception) {
      logger.info("**********Exception while retrieving Comments**********");
      exception.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @NeedLogin
  @RequestMapping(value = "/updateComment", method = RequestMethod.PATCH, produces = "application/json")
  public ResponseEntity<Object> updateComment(@RequestBody Comment updatedComment, @RequestParam Integer taskId) {
    try {
      if(String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty or null value for TaskId");
      }
      boolean status = commentInterface.updateComment(updatedComment);
      if (status) {
        return ResponseEntity.status(HttpStatus.OK).body(updatedComment);
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
  public ResponseEntity<String> deleteComment(@RequestBody Integer commentId) {
    try {
      if(String.valueOf(commentId) == null || String.valueOf(commentId).equals("")){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty or null value for commentId");
      }
      boolean status = commentInterface.deleteComment(commentId);
      if (status) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted successfully");
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
      logger.info("**********Exception while deleting List**********");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

}
