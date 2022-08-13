package com.todo.Interface;

import com.todo.model.Tag;

public interface TagInterface {

    boolean createTag(String loggedInUser, Tag newTag, Integer taskId);

}
