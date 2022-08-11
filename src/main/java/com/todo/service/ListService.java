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
  private static final Logger logger = LoggerFactory.getLogger(ListService.class);

  public boolean createList(List newList, String email) {
    try {
    User user = userInterface.getUserDetails(email);
    if(newList.getList_name() == null){
      newList.setList_name("");
    } else {
      newList.setList_name(newList.getList_name());
    }
    newList.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
    newList.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
    newList.setmUsers(user);
        listRepository.save(newList);
        logger.info("**********List created successfully **********");
        return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      logger.info("**********Exception while creating the list! **********");
      return false;
    }
  }

  public boolean createDefaultList(User user) {
    try {
      List newList = new List();
      newList.setList_name("");
      newList.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
      newList.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
      newList.setmUsers(user);
      listRepository.save(newList);
      logger.info("**********Default List created successfully **********");
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      logger.info("**********Exception while creating the default list! **********");
      return false;
    }
  }

  public List createEmptyList(User user) {
    try {
      List newList = new List();
      newList.setList_name("");
      newList.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
      newList.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
      newList.setmUsers(user);
      listRepository.save(newList);
      logger.info("**********Default List created successfully **********");
      return newList;
    } catch (Exception exception) {
      exception.printStackTrace();
      logger.info("**********Exception while creating the default list! **********");
      return null;
    }
  }

  /**
   * @param newList 
   * @return boolean
   */
  @Override
  public boolean deleteList(List newList) {
    try {
      List list = listRepository.findByListId(newList.getList_Id());
      if (list != null) {
        java.util.List<List> totalList = getList(list.getmUsers().getEmailAddress());
        if(totalList.size() > 1) {
          listRepository.delete(list);
        } else if (totalList.size() == 1) {
          list.setList_name("");
          list.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
          list.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
          listRepository.save(list);
        }
        logger.info("**********List deleted successfully **********");
        return true;
      }
      logger.info("**********List you are trying to delete does not exist **********");
      return false;
    } catch(Exception exception){
      logger.info("**********Exception while deleting list **********");
      exception.printStackTrace();
      return false;
    }
  }

  /**
   * @param updatedList 
   * @return
   */
  @Override
  public boolean updateList(List updatedList) {
    try {
      List existingList = listRepository.findByListId(updatedList.getList_Id());
      if (existingList != null){
        updatedList.setCreated_AtTime(existingList.getCreated_AtTime());
        if(existingList.getList_name().equals(updatedList.getList_name())) {
          logger.info("**********Nothing to update **********");
          return false;
        }
        updatedList.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
        updatedList.setmUsers(existingList.getmUsers());
        listRepository.save(updatedList);
        logger.info("**********List updated successfully **********");
        return true;
      }
      logger.info("**********List you are trying to update does not exist **********");
      return false;
      }catch(Exception exception){
      logger.info("**********Exception while updating list **********");
      exception.printStackTrace();
      return false;
    }
  }

  /**
   * @param loggedInUser 
   * @return
   */
  @Override
  public java.util.List<List> getList(String loggedInUser) {
    User user = userInterface.getUserDetails(loggedInUser);
    java.util.List<List> userList = listRepository.findList(user.getUserId());
    if(userList.isEmpty()){
      userList.add(createEmptyList(user));
    }
    return userList;
  }

}
