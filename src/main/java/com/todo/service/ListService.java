package com.todo.service;

import com.todo.Interface.ListInterface;
import com.todo.Interface.UserInterface;
import com.todo.model.List;
import com.todo.model.User;
import com.todo.repositories.ListRepository;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService implements ListInterface {

  @Autowired
  ListRepository listRepository;
  @Autowired
  UserInterface userInterface;
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  public boolean createList(List newList, String email) {

    User user = userInterface.getUserDetails(email);

    // List list = new List(1,"listname",new
    // Timestamp(System.currentTimeMillis()),new
    // Timestamp(System.currentTimeMillis()),user);
    // java.util.List<List> lists= listRepository.findList(user.getUserId());
    newList.setList_name(newList.getList_name());
    newList.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
    newList.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
    newList.setmUsers(user);
    // newList.setList_name("listname");
    // newList.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
    // newList.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
    // newList.setmUsers(user);
    try {
      listRepository.save(newList);
      logger.info("**********User registered successfully **********");
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      logger.info("**********Exception while registering the user ! **********");
      return false;
    }
  }

}
