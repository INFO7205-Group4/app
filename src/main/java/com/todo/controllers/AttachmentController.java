package com.todo.controllers;

import com.todo.model.AttachmentResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.todo.Interface.AttachmentInterface;
import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;
import com.todo.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/v1/user")
public class AttachmentController {

    @Autowired
    AttachmentInterface Attachment;

    @Autowired
    AuthServiceInterface AuthService;

    @NeedLogin
    @RequestMapping(value = "/task/attachment", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> createAttachment(HttpServletRequest request, @RequestParam("file") MultipartFile file,
            @RequestParam("taskId") Integer taskId) {
        try {
            String email = AuthService.getUserName(request);
            if (file != null && String.valueOf(taskId) != null) {
                boolean status = Attachment.createAttachment(email, file, taskId);
                if (status) {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(null);
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @NeedLogin
    @RequestMapping(value = "/task/attachment", method = RequestMethod.GET, produces = "application/json")
    public List<AttachmentResponse> getAttachmentList(HttpServletRequest request,
            @RequestParam("taskId") Integer taskId) {
        try {
            String email = AuthService.getUserName(request);
            if (!String.valueOf(taskId).equals(null)) {

                return Attachment.getAllAttachmentsForTask(email, taskId)
                        .stream()
                        .map(this::mapToAttachmentResponse)
                        .collect(Collectors.toList());
            }
            return null;
        } catch (Exception exception) {
            return null;
        }
    }

    @NeedLogin
    @RequestMapping(value = "/task/attachment/{attachmentId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer attachmentId) {
        try {
            Optional<Attachment> attachmentOptional = Attachment.getFile(attachmentId);
            if (!attachmentOptional.isPresent()) {
                return ResponseEntity.notFound()
                        .build();
            }
            Attachment attachment = attachmentOptional.get();
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + attachment.getAttachmentName() + "\"")
                    .contentType(MediaType.valueOf(attachment.getContentType()))
                    .body(attachment.getAttachmentFile());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

    }

    @NeedLogin
    @RequestMapping(value = "/task/attachment/{attachmentId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteAttachment(@PathVariable Integer attachmentId) {
        try {
            boolean status = Attachment.deleteAttachment(attachmentId);
            if (status) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    private AttachmentResponse mapToAttachmentResponse(Attachment attachment) {
        String attachmentDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/user/task/attachment/")
                .path(String.valueOf(attachment.getAttachment_Id()))
                .toUriString();
        AttachmentResponse attachmentResponse = new AttachmentResponse();
        attachmentResponse.setId(attachment.getAttachment_Id());
        attachmentResponse.setName(attachment.getAttachmentName());
        attachmentResponse.setContentType(attachment.getContentType());
        attachmentResponse.setSize(attachment.getAttachmentSize());
        attachmentResponse.setUrl(attachmentDownloadURL);
        return attachmentResponse;
    }

}
