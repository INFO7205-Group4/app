package com.todo.service;

import java.sql.Timestamp;

import com.todo.Interface.TagInterface;
import com.todo.model.Tag;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repositories.TagRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    @Override
    public boolean createTag(String loggedInUser, Tag newTag, Integer taskId) {
        try {
            User user = userRepository.findByEmailAddress(loggedInUser);

            Task task = taskRepository.findByTaskId(taskId);
            if (task == null) {
                return false;
            }
            if (String.valueOf(taskId).equals(null) && String.valueOf(taskId).equals("")) {
                return false;
            }

            if (newTag.getTagName() == null || newTag.getTagName().equals("")) {
                return false;
            }

            List<Tag> tags = tagRepository.findTagByTaskId(task.getTask_Id());

            List<String> userTags = tagRepository.findTagNameByUserId(user.getUserId());

            if (userTags.contains(newTag.getTagName())) {
                return false;
            }

            if (tags.size() <= 10) {
                newTag.setmUsers(user);
                newTag.setAllTasks(task);
                newTag.setTagName(newTag.getTagName());
                newTag.setCreatedAtTime(new Timestamp(System.currentTimeMillis()));
                newTag.setUpdatedAtTime(new Timestamp(System.currentTimeMillis()));
                tagRepository.save(newTag);
                logger.info("********** Tag created successfully **********");
                return true;
            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
