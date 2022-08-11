package com.todo.Interface;

import com.todo.model.List;
import com.todo.model.User;

public interface ListInterface {

  boolean createList(List list, String email);

  boolean deleteList(List newList);

  boolean updateList(List updatedList);

  java.util.List<List> getList(String loggedInUser);

  boolean createDefaultList(User user);
}
