package com.todo.Interface;

import com.todo.model.Comment;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;

public interface CommentInterface {

  Comment create(Comment comment, Integer taskId);

  List<Comment> getComment(Integer taskId);

  Comment updateComment(Comment updatedComment, Integer taskId);

  boolean deleteComment(Integer commentId);
}
