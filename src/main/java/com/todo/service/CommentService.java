package com.todo.service;

import com.todo.Interface.CommentInterface;
import com.todo.model.Attachment;
import com.todo.model.Comment;
import com.todo.model.Task;
import com.todo.repositories.CommentRepository;
import com.todo.repositories.TaskRepository;
import java.util.List;
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
  public boolean create(Comment comment) {
    try{
      commentRepository.save(comment);
      logger.info("**********Created comment successfully **********");
      return true;
    }catch (Exception exception){
      exception.printStackTrace();
      logger.info("**********Exception while retrieving the task attachments! **********");
      return false;
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
  public boolean updateComment(Comment updatedComment) {
    try{
      commentRepository.save(updatedComment);
      logger.info("**********Updated comment successfully **********");
      return true;
    }catch (Exception exception){
      exception.printStackTrace();
      logger.info("**********Exception while updating the task comment! **********");
      return false;
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
      return false;
    }
    commentRepository.delete(comment);
    return true;
  } catch (Exception exception){
    exception.printStackTrace();
    logger.info("**********Exception while deleting the comment! **********");
    return false;
  }
  }
}
