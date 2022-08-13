package com.todo.service;

import com.todo.Interface.AttachmentInterface;

import com.todo.model.Attachment;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.repositories.AttachmentRepository;
import com.todo.repositories.ListRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentService implements AttachmentInterface {

    // @Autowired
    // TaskRepository taskRepository;

    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ListRepository listRepository;
    @Autowired
    UserRepository userRepository;

    final Logger logger = LoggerFactory.getLogger(AttachmentService.class);

    /**
     * @param file
     * @param taskId
     */
    @Override
    public boolean createAttachment(String loggedInUser, MultipartFile file, Integer taskId) throws IOException {
        try {
            Task task = taskRepository.findByTaskId(taskId);
            if (task == null) {
                logger.info("Task not found");
                return false;
            }
            boolean status = validateStatus(taskId, loggedInUser);
            if (status == false) {
                logger.info("Task does not belong to this user");
                return false;
            }
            List<Attachment> attachedFiles = attachmentRepository.getAttachmentByTaskId(taskId);
            if (attachedFiles.size() >= 5) {
                logger.info("Cannot add more than 5 attachments");
                return false;
            }
            Attachment attachment = new Attachment();
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setAttachmentSize(file.getSize());

            attachment.setAttachment_File(file.getBytes());
            attachment.setAttachedAtTime(new Timestamp(System.currentTimeMillis()));
            attachment.setmTasks(task);
            attachmentRepository.save(attachment);
            logger.info("Attached successfully");
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while creating the task attachment! **********");
            return false;
        }
    }

    @Override
    public Optional<Attachment> getFile(Integer id) {
        return attachmentRepository.findById(id);
    }

    @Override
    public List<Attachment> getAllFiles() {
        return attachmentRepository.findAll();
    }

    /**
     * @param taskId
     * @return
     */
    @Override
    @Transactional
    public List<Attachment> getAllAttachmentsForTask(String loggedInUser, Integer taskId) {
        try {
            boolean status = validateStatus(taskId, loggedInUser);
            if (status == false) {
                logger.info("Task does not belong to this user");
                return null;
            }
            Task task = taskRepository.findByTaskId(taskId);
            if (task == null) {
                logger.info("Task not found");
                return null;
            }
            List<Attachment> attachedFiles = attachmentRepository.getAttachmentByTaskId(taskId);
            logger.info("Tasks returned successfully");
            return attachedFiles;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while retrieving the task attachments! **********");
            return null;
        }
    }

    /**
     * @param attachmentId
     * @return
     */
    @Override
    @Transactional
    public boolean deleteAttachment(Integer attachmentId) {
        try {
            Attachment attachment = attachmentRepository.findByAttachmentId(attachmentId);
            if (attachment == null) {
                logger.info("Attachment not found");
                return false;
            }
            attachmentRepository.delete(attachment);
            logger.info("Attachment deleted successfully");
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("**********Exception while deleting the attachment! **********");
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
