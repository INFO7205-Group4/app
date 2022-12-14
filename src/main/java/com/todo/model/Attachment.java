package com.todo.model;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@Entity
@Table(name = "Attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attachment_Id;

    private String attachmentName;

    private String contentType;

    @Column(name = "attachedAtTime", nullable = true, unique = false)
    private Timestamp attachedAtTime;

    private long attachmentSize;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_Id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Task mTasks;

    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "attachmentFile")
    private byte[] attachmentFile;

    public Attachment() {

    }

    public Integer getAttachment_Id() {
        return attachment_Id;
    }

    public void setAttachment_Id(Integer attachment_Id) {
        this.attachment_Id = attachment_Id;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Timestamp getAttachedAtTime() {
        return attachedAtTime;
    }

    public void setAttachedAtTime(Timestamp attachedAtTime) {
        this.attachedAtTime = attachedAtTime;
    }

    public long getAttachmentSize() {
        return attachmentSize;
    }

    public void setAttachmentSize(long attachmentSize) {
        this.attachmentSize = attachmentSize;
    }

    public Task getmTasks() {
        return mTasks;
    }

    public void setmTasks(Task mTasks) {
        this.mTasks = mTasks;
    }

    public byte[] getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachment_File(byte[] attachmentFile) {
        this.attachmentFile = attachmentFile;
    }
}