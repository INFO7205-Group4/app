package com.todo.repositories;

import java.util.List;
import com.todo.model.Attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    @Query("select a from Attachment a where a.mTasks.task_Id=?1")
    List<Attachment> getAttachmentByTaskId(Integer taskId);

    @Query("select a from Attachment a where a.attachment_Id=?1")
    Attachment findByAttachmentId(Integer attachmentId);
}
