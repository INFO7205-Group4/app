package com.todo.service;

import com.todo.Interface.CommentInterface;
import com.todo.model.Attachment;
import com.todo.model.Comment;
import com.todo.model.Task;
import com.todo.repositories.CommentRepository;
import com.todo.repositories.TaskRepository;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements CommentInterface {
  
  @Autowired
  CommentRepository commentRepository;
  @Autowired
  TaskRepository taskRepository;

  final Logger logger = LoggerFactory.getLogger(CommentService.class);


  /**
   * @param comment 
   * @return
   */
  @Override
  public Comment create(Comment comment, Integer taskId) {
    try{
      Task task = taskRepository.findByTaskId(taskId);
      if (task == null) {
        return null;
      }
      comment.setcTask(task);
      comment.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
      comment.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
      commentRepository.save(comment);
      logger.info("**********Created comment successfully **********");
      return comment;
    }catch (Exception exception){
      exception.printStackTrace();
      logger.info("**********Exception while retrieving the task comments! **********");
      return null;
    }
    
  }

  /**
   * @param taskId 
   * @return
   */
  @Override
  public List<Comment> getComment(Integer taskId) {
    try {
      Task task = taskRepository.findByTaskId(taskId);
      if (task == null) {
        return null;
      }
      List<Comment> comments = commentRepository.getCommentsByTaskId(taskId);
      return comments;
    } catch (Exception exception){
      exception.printStackTrace();
      logger.info("**********Exception while retrieving the task comments! **********");
      return null;
    }
  }

  /**
   * @param updatedComment 
   * @return
   */
  @Override
  public Comment updateComment(Comment updatedComment, Integer taskId) {
    try{
      Task task = taskRepository.findByTaskId(taskId);
      if (task == null) {
        logger.info("**********No task found for updating comment error **********");
        return null;
      }
      Comment comment = commentRepository.findByCommentId(updatedComment.getComment_Id());
      if (comment == null) {
        logger.info("**********No comment to update error **********");
        return null;
      }
      comment.setComment(updatedComment.getComment());
      comment.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
      commentRepository.save(comment);
      logger.info("**********Updated comment successfully **********");
      return comment;
    }catch (Exception exception){
      exception.printStackTrace();
      logger.info("**********Exception while updating the task comment! **********");
      return null;
    }
  }

  /**
   * @param commentId 
   * @return
   */
  @Override
  public boolean deleteComment(Integer commentId) {
    try{
    Comment comment = commentRepository.findByCommentId(commentId);
    if(comment == null){
      logger.info("**********No comment to delete error **********");
      return false;
    }
    commentRepository.delete(comment);
    logger.info("**********Deleted comment successfully **********");
    return true;
  } catch (Exception exception){
    exception.printStackTrace();
    logger.info("**********Exception while deleting the comment! **********");
    return false;
  }
  }
}
