package com.todo.controllers;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.ListInterface;
import com.todo.Interface.NeedLogin;
import com.todo.model.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class ListController {

  @Autowired
  ListInterface List;
  @Autowired
  AuthServiceInterface AuthService;

  private static final Logger logger = LoggerFactory.getLogger(ListController.class);

  @NeedLogin
  @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List> Search(HttpServletRequest request){
    String loggedInUser = AuthService.getUserName(request);
      return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }
  @NeedLogin
  @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<com.todo.model.List> createList(@RequestBody List newList, HttpServletRequest request) {
    try {
      String loggedInUser = AuthService.getUserName(request);
        boolean status = List.createList(newList, loggedInUser);
        if (status) {
          return new ResponseEntity<List>(newList, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    } catch (Exception e) {
      logger.info("**********Exception while creating New List**********");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }
  @NeedLogin
  @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<java.util.List<com.todo.model.List>> getList(HttpServletRequest request) {
    try {
      String loggedInUser = AuthService.getUserName(request);
      java.util.List<com.todo.model.List> userList = List.getList(loggedInUser);
      return ResponseEntity.status(HttpStatus.OK).body(userList);
    } catch (Exception e) {
      logger.info("**********Exception while retrieving New List**********");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @NeedLogin
  @RequestMapping(value = "/updateList", method = RequestMethod.PATCH, produces = "application/json")
  public ResponseEntity<List> updateUser(@RequestBody List updatedList) {
    try {
      boolean status = List.updateList(updatedList);
      if (status) {
        return ResponseEntity.status(HttpStatus.OK).body(updatedList);
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
      logger.info("**********Exception while updating list details **********");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

  }

  @NeedLogin
  @RequestMapping(value = "/deleteList", method = RequestMethod.DELETE, produces = "application/json")
  public ResponseEntity<List> deleteList(@RequestBody List newList){
    try {
      boolean status = List.deleteList(newList);
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

}
