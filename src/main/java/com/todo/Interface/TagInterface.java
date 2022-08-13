package com.todo.Interface;

import com.todo.model.Tag;
import java.util.List;

public interface TagInterface {

    Tag createTag(String loggedInUser, Tag newTag);

    List<Tag> getTags(String loggedInUser);

    Tag updateTag(Tag updatedTag, String loggedInUser);

    boolean deleteTag(Integer tagId);
}