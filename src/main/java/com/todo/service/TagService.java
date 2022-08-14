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
            for (com.todo.model.List eachList : listRepository.findList(tag.gettUsers().getUserId())) {
                for (Task eachTask : taskRepository.getTasks(eachList.getListId())) {
                    for (int i = 0; i < eachTask.getTags().size(); i++) {
                        if (eachTask.getTags().contains(String.valueOf(tagId))) {
                            eachTask.getTags().remove(i);
                            taskRepository.save(eachTask);
                        }
                    }
                }
            }
            tagRepository.delete(tag);
            logger.info("**********Tag deleted successfully **********");
            return true;
        } catch (Exception exception) {
            logger.info("**********Exception while deleting tag **********");
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean taskTag(String tagId, String taskId, String loggedInUser) {
        try {
            User user = userRepository.findByEmailAddress(loggedInUser);
            boolean status = getAllListForParticularUser(loggedInUser, Integer.parseInt(taskId));
            if (!status) {
                logger.info("**********Task does not belong to this user **********");
                return false;
            }
            Tag tag = tagRepository.findByTagId(Integer.parseInt(tagId));
            if (tag == null) {
                logger.info("**********Tag does not exist **********");
                return false;
            }
            if (tag.gettUsers().getUserId() != user.getUserId()) {
                logger.info("**********Tag does not belong to this user **********");
                return false;
            }
            Task task = taskRepository.findByTaskId(Integer.parseInt(taskId));
            if (task == null) {
                logger.info("**********Task does not exist **********");
                return false;
            }
            if (task.getTags() != null && task.getTags().size() >= 10) {
                logger.info("**********Tag limit reached **********");
                return false;
            }
            if (task.getTags() != null) {
                for (String t : task.getTags()) {
                    if (t.equals(tag.getTag_Id().toString())) {
                        logger.info("**********Tag already exists **********");
                        return false;
                    }
                }
            }
            task.setTags(String.valueOf(tagId), true, task.getTags());
            taskRepository.save(task);
            logger.info("**********Tag added to task successfully **********");
            return true;
        } catch (

        Exception exception) {
            logger.info("**********Exception while adding Tag to task **********");
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTagFromTask(String tagId, String taskId, String loggedInUser) {
        User user = userRepository.findByEmailAddress(loggedInUser);
        boolean status = getAllListForParticularUser(loggedInUser,
                Integer.parseInt(taskId));
        if (!status) {
            logger.info("**********Task does not belong to this user **********");
            return false;
        }
        Tag tag = tagRepository.findByTagId(Integer.parseInt(tagId));
        if (tag == null) {
            logger.info("**********Tag does not exist **********");
            return false;
        }
        if (tag.gettUsers().getUserId() != user.getUserId()) {
            logger.info("**********Tag does not belong to this user **********");
            return false;
        }
        Task task = taskRepository.findByTaskId(Integer.parseInt(taskId));
        if (task == null) {
            logger.info("**********Task does not exist **********");
            return false;
        }
        if (task.getTags() != null) {
            if (task.getTags().size() == 0) {
                logger.info("**********No tags to delete **********");
                return false;
            }
            for (int i = 0; i < task.getTags().size(); i++) {
                if (task.getTags().contains(String.valueOf(tagId))) {
                    task.getTags().remove(i);
                    taskRepository.save(task);
                    logger.info("**********Tag deleted from task successfully **********");
                }
            }
            return true;
        }
        logger.info("**********Tag does not exist **********");
        return false;

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