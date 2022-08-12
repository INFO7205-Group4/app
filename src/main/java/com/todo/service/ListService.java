package com.todo.service;

import com.todo.Interface.ListInterface;
import com.todo.model.List;
import com.todo.model.User;
import com.todo.repositories.ListRepository;
import com.todo.repositories.UserRepository;
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
  UserRepository userRepository;
  private static final Logger logger = LoggerFactory.getLogger(ListService.class);

  @Override
  public boolean createList(List newList, String email) {
    try {
      User user = userRepository.findByEmailAddress(email);
      if (newList.getListName() == null) {
        newList.setListName("New List");
      } else {
        newList.setListName(newList.getListName());
      }
      newList.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
      newList.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
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

  @Override
  public boolean deleteList(List list) {
    try {
      List existingList = listRepository.findByListId(list.getListId());
      if (existingList != null) {
        java.util.List<List> totalList = getAllListForParticularUser(existingList.getmUsers().getEmailAddress());
        if (totalList.size() > 1) {
          listRepository.delete(existingList);
        } else if (totalList.size() == 1) {
          existingList.setListName("New List");
          existingList.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
          existingList.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
          listRepository.save(existingList);
        }
        logger.info("**********List deleted successfully **********");
        return true;
      }
      logger.info("**********List you are trying to delete does not exist **********");
      return false;
    } catch (Exception exception) {
      logger.info("**********Exception while deleting list **********");
      exception.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean updateList(List updatedList) {
    try {
      List existingList = listRepository.findByListId(updatedList.getListId());
      if (existingList != null) {
        updatedList.setCreatedAtTime(existingList.getCreatedAtTime());
        if (existingList.getListName().equals(updatedList.getListName())) {
          return false;
        }
        updatedList.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
        updatedList.setmUsers(existingList.getmUsers());
        listRepository.save(updatedList);
        logger.info("**********List updated successfully **********");
        return true;
      }
      logger.info("**********List you are trying to update does not exist **********");
      return false;
    } catch (Exception exception) {
      logger.info("**********Exception while updating list **********");
      exception.printStackTrace();
      return false;
    }
  }

  @Override
  public java.util.List<List> getAllListForParticularUser(String loggedInUser) {
    User user = userRepository.findByEmailAddress(loggedInUser);
    java.util.List<List> userList = listRepository.findList(user.getUserId());
    if (userList.isEmpty()) {
      userList.add(createEmptyList(user));
    }
    return userList;
  }

  private List createEmptyList(User user) {
    try {
      List newList = new List();
      newList.setListName("");
      newList.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
      newList.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
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
}
