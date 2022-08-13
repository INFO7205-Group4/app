package com.todo.Interface;

import com.todo.model.Comment;
import java.util.List;

public interface CommentInterface {

    Comment create(Comment comment, Integer taskId, String loggedInUser);

    List<Comment> getComment(Integer taskId, String loggedInUser);

    Comment updateComment(Comment updatedComment, Integer taskId, String loggedInUser);

    boolean deleteComment(Integer commentId);
}