package com.todo.repositories;

import com.todo.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("Select c from Comment c where c.cTask.task_Id=?1")
    List<Comment> getCommentsByTaskId(Integer taskId);

    @Query("select c from Comment c where c.comment_Id=?1")
    Comment findByCommentId(Integer commentId);
}