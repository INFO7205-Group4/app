package com.todo.Interface;

import com.todo.model.Comment;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;

public interface CommentInterface {

  boolean create(Comment comment);

  List<Comment> getComment(Integer taskId);

  boolean updateComment(Comment updatedComment, Integer taskId);

  boolean deleteComment(Integer commentId);
}
