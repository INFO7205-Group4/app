package com.todo.service;

import com.todo.Interface.TagInterface;
import com.todo.model.Tag;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repositories.ListRepository;
import com.todo.repositories.TagRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;
import java.sql.Timestamp;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService implements TagInterface {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ListRepository listRepository;
    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    @Override
    public Tag createTag(String loggedInUser, Tag newTag) {
        try {
            User user = userRepository.findByEmailAddress(loggedInUser);
            if (user == null) {
                return null;
            }
            List<String> userTags = tagRepository.findTagNameByUserId(user.getUserId());
            if (userTags.contains(newTag.getTagName())) {
                return null;
            }
            newTag.settUsers(user);
            newTag.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
            newTag.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
            tagRepository.save(newTag);
            logger.info("********** Tag created successfully **********");
            return newTag;

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while creating the tag! **********");
            return null;
        }

    }

    @Override
    public List<Tag> getTags(String loggedInUser) {
        try {
            User user = userRepository.findByEmailAddress(loggedInUser);
            List<Tag> tagList = tagRepository.findTagByUserId(user.getUserId());
            return tagList;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while getting the tag! **********");
            return null;
        }
    }

    @Override
    public Tag updateTag(Tag updatedTag, String loggedInUser) {
        try {
            Tag existingTag = tagRepository.findByTagId(updatedTag.getTag_Id());
            if (existingTag == null) {
                logger.info("********** Tag not found! **********");
                return null;
            }
            if (!existingTag.gettUsers().getEmailAddress().equals(loggedInUser)) {
                logger.info("**********This tag does not belong to this user **********");
                return null;
            }
            if (existingTag.getTagName().equals(updatedTag.getTagName())) {
                logger.info("**********Nothing to update **********");
                return null;
            }
            updatedTag.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
            updatedTag.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
            updatedTag.settUsers(existingTag.gettUsers());
            tagRepository.save(updatedTag);
            logger.info("**********Tag updated successfully **********");
            return updatedTag;
        } catch (Exception exception) {
            logger.info("**********Exception while updating tag **********");
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteTag(Integer tagId, String loggedInUser) {
        try {
            Tag tag = tagRepository.findByTagId(tagId);
            if (tag == null) {
                logger.info("**********Tag you are trying to delete does not exist **********");
                return false;
            }
            if (!tag.gettUsers().getEmailAddress().equals(loggedInUser)) {
                logger.info("**********This tag does not belong to this user **********");
                return false;
            }
            tagRepository.delete(tag);
            logger.info("**********Tag deleted successfully **********");
            return true;
        } catch (Exception exception) {
            logger.info("**********Exception while deleting Tag **********");
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean taskTag(String tagId, String taskId, String loggedInUser) {
        try {
            User user = userRepository.findByEmailAddress(loggedInUser);
            boolean status = getAllListForParticularUser(loggedInUser, Integer.valueOf(taskId));
            if (!status) {
                return false;
            }
            Tag tag = tagRepository.findByTagId(Integer.valueOf(tagId));
            if (tag == null) {
                logger.info("**********Tag does not exist **********");
                return false;
            }
            if (tag.gettUsers().getUserId() != user.getUserId()) {
                logger.info("**********Tag does not belong to this user **********");
                return false;
            }
            // List<Integer> allTagsForTask =
            // tagRepository.findTagByTaskId(Integer.valueOf(taskId));
            // if (allTagsForTask.contains(Integer.valueOf(tagId))) {
            // logger.info("**********Tag already exists for this task **********");
            // return false;
            // }
            // if (allTagsForTask.size() >= 10) {
            // logger.info("**********Maximum 10 tags can be added to a task **********");
            // return false;
            // }

            // Task task = taskRepository.findByTaskId(Integer.valueOf(taskId));
            // if (task == null) {
            // logger.info("**********Task does not exist **********");
            // return false;
            // }
            // tag.setAllTasks(task);
            logger.info("**********Tag added to task successfully **********");
            return true;
        } catch (Exception exception) {
            logger.info("**********Exception while adding Tag to task **********");
            exception.printStackTrace();
            return false;
        }
    }

    private boolean getAllListForParticularUser(String loggedInUser, Integer taskId) {
        User user = userRepository.findByEmailAddress(loggedInUser);
        List<com.todo.model.List> userList = listRepository.findList(user.getUserId());
        if (userList.size() == 0) {
            return false;
        }
        Task existingTask = taskRepository.findByTaskId(taskId);
        for (com.todo.model.List list : userList) {
            if (list.getListId().equals(existingTask.getmList().getListId())) {
                return true;
            }
        }
        return false;
    }

}