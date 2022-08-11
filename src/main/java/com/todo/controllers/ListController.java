package com.todo.controllers;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.ListInterface;
import com.todo.model.List;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class ListController {

  @Autowired
  ListInterface List;
  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthServiceInterface AuthService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List> Search(HttpServletRequest request){
    String loggedInUser = AuthService.getUserName(request);
    User user = userRepository.findByEmailAddress(loggedInUser);
      return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }


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
      logger.info("**********Exception while creating New User**********");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  public static Map<String, String> getUrlQueryMap(String query) {
    String[] params = query.split("&");
    Map<String, String> map = new HashMap<String, String>();

    for (String param : params) {
      String name = param.split("=")[0];
      String value = param.split("=")[1];
      map.put(name, value);
    }
    return map;
  }

}
