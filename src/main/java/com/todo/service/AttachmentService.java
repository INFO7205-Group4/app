package com.todo.service;

import com.todo.Interface.AttachmentInterface;
import com.todo.model.Attachment;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService implements AttachmentInterface {

    // @Autowired
    // TaskRepository taskRepository;

    @Override
    public boolean createAttachment(Attachment newAttachment, String loggedInUser, Integer taskId) {
        // get the task Id
        // Task task = taskRepository.findByTaskId(taskId);

        return false;
    }

}
