package com.todo.Interface;

import com.todo.model.Comment;
import java.util.List;

public interface CommentInterface {

  boolean create(Comment comment);

  List<Comment> getComment(Integer taskId);

  boolean updateComment(Comment updatedComment);

  boolean deleteComment(Integer commentId);
}
