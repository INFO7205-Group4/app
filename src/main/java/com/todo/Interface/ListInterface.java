package com.todo.Interface;

import com.todo.model.List;

public interface ListInterface {

  boolean createList(List list, String email);

  boolean deleteList(List newList);

  boolean updateList(List updatedList);

  java.util.List<List> getAllListForParticularUser(String loggedInUser);

}
