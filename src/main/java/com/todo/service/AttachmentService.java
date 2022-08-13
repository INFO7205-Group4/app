package com.todo.service;

import com.todo.Interface.AttachmentInterface;

import com.todo.model.Attachment;
import com.todo.model.Task;
import com.todo.repositories.AttachmentRepository;
import com.todo.repositories.TaskRepository;
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
  @Autowired
  AttachmentRepository attachmentRepository;
  @Autowired
  TaskRepository taskRepository;

  final Logger logger = LoggerFactory.getLogger(AttachmentService.class);

  /**
   * @param file
   * @param taskId
   */
  @Override
  public Integer createAttachment(MultipartFile file, Integer taskId) throws IOException {
    try {
      Task task = taskRepository.findByTaskId(taskId);
      if (task == null) {
        return 1;
      }
      List<Attachment> attachedFiles = attachmentRepository.getAttachmentByTaskId(taskId);
      if(attachedFiles.size() >= 5){
        logger.info("**********Max Attachment Limit reached**********");
        return 2;
      }
      Attachment attachment = new Attachment();
      attachment.setAttachment_Name(file.getOriginalFilename());
      attachment.setContentType(file.getContentType());
      attachment.setAttachment_Size(file.getSize());
      attachment.setAttachment_File(file.getBytes());
      attachment.setAttached_at_time(new Timestamp(System.currentTimeMillis()));
      attachment.setmTasks(task);
      attachmentRepository.save(attachment);
      logger.info("Attached succesfully");
      return 0;
    }catch (Exception exception){
      exception.printStackTrace();
      logger.info("**********Exception while creating the task attachment! **********");
      return 3;
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
  public List<Attachment> getAllAttachmentsForTask(Integer taskId) {
    try {
      Task task = taskRepository.findByTaskId(taskId);
      if (task == null) {
        return null;
      }
      List<Attachment> attachedFiles = attachmentRepository.getAttachmentByTaskId(taskId);
      return attachedFiles;
    } catch (Exception exception){
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
    try{
      Attachment attachment = attachmentRepository.findByAttachmentId(attachmentId);
      if(attachment == null){
        return false;
      }
      attachmentRepository.delete(attachment);
      return true;
    }catch (Exception exception){
      exception.printStackTrace();
      logger.info("**********Exception while deleting the attachment! **********");
      return false;
    }
  }


}
