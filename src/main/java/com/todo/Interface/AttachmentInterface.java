package com.todo.Interface;

import com.todo.model.Attachment;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentInterface {

    Integer createAttachment(MultipartFile file, Integer taskId) throws IOException;

    Optional<Attachment> getFile(Integer id);

    List<Attachment> getAllFiles();

    List<Attachment> getAllAttachmentsForTask(Integer taskId);

    boolean deleteAttachment(Integer attachmentId);
}
