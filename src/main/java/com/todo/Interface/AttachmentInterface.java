package com.todo.Interface;

import com.todo.model.Attachment;

public interface AttachmentInterface {

    boolean createAttachment(Attachment newAttachment, String loggedInUser, Integer taskId);
    
}
