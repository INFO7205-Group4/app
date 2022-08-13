package com.todo.service;

import com.todo.Interface.CommentInterface;
import com.todo.model.Comment;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repositories.CommentRepository;
import com.todo.repositories.ListRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;

import java.sql.Timestamp;
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
    @Autowired
    ListRepository listRepository;
    @Autowired
    UserRepository userRepository;

    final Logger logger = LoggerFactory.getLogger(CommentService.class);

    /**
     * @param comment
     * @return
     */
    @Override
    public Comment create(Comment comment, Integer taskId, String loggedInUser) {
        try {
            boolean status = validateStatus(taskId, loggedInUser);
            if (status == false) {
                logger.info("Task does not belong to this user");
                return null;
            }
            Task task = taskRepository.findByTaskId(taskId);
            if (task == null) {
                return null;
            }
            comment.setcTask(task);
            comment.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
            comment.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
            commentRepository.save(comment);
            logger.info("**********Created comment successfully **********");
            return comment;
        } catch (Exception exception) {
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
    public List<Comment> getComment(Integer taskId, String loggedInUser) {
        try {
            boolean status = validateStatus(taskId, loggedInUser);
            if (status == false) {
                logger.info("Task does not belong to this user");
                return null;
            }
            Task task = taskRepository.findByTaskId(taskId);
            if (task == null) {
                logger.info("**********No Task Found **********");
                return null;
            }
            List<Comment> comments = commentRepository.getCommentsByTaskId(taskId);
            return comments;
        } catch (Exception exception) {
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
    public Comment updateComment(Comment updatedComment, Integer taskId, String loggedInUser) {
        try {
            boolean status = validateStatus(taskId, loggedInUser);
            if (status == false) {
                logger.info("Task does not belong to this user");
                return null;
            }
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
            comment.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
            commentRepository.save(comment);
            logger.info("**********Updated comment successfully **********");
            return comment;
        } catch (Exception exception) {
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
        try {
            Comment comment = commentRepository.findByCommentId(commentId);
            if (comment == null) {
                logger.info("**********No comment to delete error **********");
                return false;
            }
            commentRepository.delete(comment);
            logger.info("**********Deleted comment successfully **********");
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while deleting the comment! **********");
            return false;
        }
    }

    private boolean validateStatus(Integer taskId, String loggedInUser) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task == null) {
            return false;
        }
        User user = userRepository.findByEmailAddress(loggedInUser);
        java.util.List<com.todo.model.List> userList = listRepository.findList(user.getUserId());
        for (com.todo.model.List list : userList) {
            if (list.getListId().equals(task.getmList().getListId())) {
                return true;
            }
        }
        return false;
    }
}