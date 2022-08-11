package com.todo.service;

import com.todo.Interface.AttachmentInterface;
import com.todo.model.Attachment;

import org.springframework.stereotype.Service;

@Service
public class AttachmentService implements AttachmentInterface {

    @Override
    public boolean createAttachment(Attachment newAttachment, String loggedInUser) {
        // get the task Id

        return false;
    }

}
