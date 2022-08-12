package com.todo.controllers;

import com.todo.model.AttachmentResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;

import com.todo.Interface.AttachmentInterface;
import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;
import com.todo.model.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/v1/user")
public class AttachmentController {

    @Autowired
    AttachmentInterface Attachment;

    @Autowired
    AuthServiceInterface AuthService;

    private static final Logger logger = LoggerFactory.getLogger(ListController.class);

    @NeedLogin
    @RequestMapping(value = "/task/attachment", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> createAttachment(@RequestParam("file")MultipartFile file, @RequestParam("taskId") Integer taskId) {
        try {
            if(file != null && String.valueOf(taskId) != null){
                Integer status = Attachment.createAttachment(file, taskId);
            if(status == 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.format("File uploaded successfully: %s",
                        file.getOriginalFilename()));
            }else if(status == 1){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("Invalid task ID"));
            } else if (status == 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("Maximum Attachment limit 5 exceeded!"));
            }else if(status == 3){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Server Error"));
            }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Request missing parameters"));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }

    }

    @NeedLogin
    @RequestMapping(value = "/task/attachment", method = RequestMethod.GET, produces = "application/json")
    public List<AttachmentResponse> getAttachmentList(@RequestParam("taskId") Integer taskId) {
        if(!String.valueOf(taskId).equals(null)) {
            return Attachment.getAllAttachmentsForTask(taskId)
                .stream()
                .map(this::mapToAttachmentResponse)
                .collect(Collectors.toList());
        }
        return null;
    }

    private AttachmentResponse mapToAttachmentResponse(Attachment attachment) {
        String attachmentDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/v1/user/task/attachment/")
            .path(String.valueOf(attachment.getAttachment_Id()))
            .toUriString();
        AttachmentResponse attachmentResponse = new AttachmentResponse();
        attachmentResponse.setId(attachment.getAttachment_Id());
        attachmentResponse.setName(attachment.getAttachment_Name());
        attachmentResponse.setContentType(attachment.getContentType());
        attachmentResponse.setSize(attachment.getAttachment_Size());
        attachmentResponse.setUrl(attachmentDownloadURL);
        return attachmentResponse;
    }

    @NeedLogin
    @RequestMapping(value = "/task/attachment/{attachmentId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer attachmentId) {
        Optional<Attachment> attachmentOptional = Attachment.getFile(attachmentId);

        if (!attachmentOptional.isPresent()) {
            return ResponseEntity.notFound()
                .build();
        }

        Attachment attachment = attachmentOptional.get();
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getAttachment_Name() + "\"")
            .contentType(MediaType.valueOf(attachment.getContentType()))
            .body(attachment.getAttachment_File());
    }

    @NeedLogin
    @RequestMapping(value = "/task/attachment/{attachmentId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteAttachment(@PathVariable Integer attachmentId){
        boolean status = Attachment.deleteAttachment(attachmentId);
        if(status){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Attachment deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing Attachment");
    }

}
