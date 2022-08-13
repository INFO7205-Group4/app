package com.todo.service;

import com.todo.Interface.TagInterface;
import com.todo.model.Tag;
import com.todo.model.User;
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
    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    @Override
    public Tag createTag(String loggedInUser, Tag newTag) {
        try {
            User user = userRepository.findByEmailAddress(loggedInUser);
            if(user == null){
                return null;
            }
            List<String> userTags = tagRepository.findTagNameByUserId(user.getUserId());
            if(userTags.contains(newTag.getTag_Name())){
                return null;
            }
                newTag.settUsers(user);
                newTag.setCreated_AtTime(new Timestamp(System.currentTimeMillis()));
                newTag.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
                tagRepository.save(newTag);
                logger.info("********** Tag created successfully **********");
                return newTag;

        } catch(Exception exception) {
        exception.printStackTrace();
        logger.info("**********Exception while creating the tag! **********");
        return null;
        }

    }

    /**
     * @param loggedInUser 
     * @return
     */
    @Override
    public List<Tag> getTags(String loggedInUser) {
        try{
            User user = userRepository.findByEmailAddress(loggedInUser);
            List<Tag> tagList = tagRepository.findTagByUserId(user.getUserId());
            return tagList;
        }catch (Exception exception){
            exception.printStackTrace();
            logger.info("**********Exception while getting the tag! **********");
            return null;
        }
    }

    /**
     * @param updatedTag 
     * @param loggedInUser
     * @return
     */
    @Override
    public Tag updateTag(Tag updatedTag, String loggedInUser) {
        try {
            Tag existingTag = tagRepository.findByTagId(updatedTag.getTag_Id());
            if (existingTag != null){
                if(existingTag.getTag_Name().equals(updatedTag.getTag_Name())) {
                    logger.info("**********Nothing to update **********");
                    return null;
                }
                updatedTag.setUpdated_AtTime(new Timestamp(System.currentTimeMillis()));
                updatedTag.settUsers(existingTag.gettUsers());
                tagRepository.save(updatedTag);
                logger.info("**********Tag updated successfully **********");
                return updatedTag;
            }
            logger.info("**********Tag you are trying to update does not exist **********");
            return null;
        }catch(Exception exception){
            logger.info("**********Exception while updating tag **********");
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * @param tagId 
     * @return
     */
    @Override
    public boolean deleteTag(Integer tagId) {
        try {
            Tag tag = tagRepository.findByTagId(tagId);
            if (tag != null) {
                tagRepository.delete(tag);
                logger.info("**********Tag deleted successfully **********");
                return true;
            }
            logger.info("**********Tag you are trying to delete does not exist **********");
            return false;
        } catch(Exception exception){
            logger.info("**********Exception while deleting Tag **********");
            exception.printStackTrace();
            return false;
        }
    }

}