package com.todo.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@Entity
@Table(name="Attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attachment_Id;

    private String attachment_Name;

    private String contentType;


    @Column(name = "attached_at_time", nullable = true, unique = false)
    private Timestamp attached_at_time;

    private long Attachment_Size;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_Id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Task mTasks;

    @Lob
    @Column(name = "Attachment_File")
    private byte[] attachment_File;

    public Attachment() {

    }

    public Integer getAttachment_Id() {
        return attachment_Id;
    }

    public void setAttachment_Id(Integer attachment_Id) {
        this.attachment_Id = attachment_Id;
    }

    public String getAttachment_Name() {
        return attachment_Name;
    }

    public void setAttachment_Name(String attachment_Name) {
        this.attachment_Name = attachment_Name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Timestamp getAttached_at_time() {
        return attached_at_time;
    }

    public void setAttached_at_time(Timestamp attached_at_time) {
        this.attached_at_time = attached_at_time;
    }

    public long getAttachment_Size() {
        return Attachment_Size;
    }

    public void setAttachment_Size(long attachment_Size) {
        Attachment_Size = attachment_Size;
    }

    public Task getmTasks() {
        return mTasks;
    }

    public void setmTasks(Task mTasks) {
        this.mTasks = mTasks;
    }

    public byte[] getAttachment_File() {
        return attachment_File;
    }

    public void setAttachment_File(byte[] attachment_File) {
        this.attachment_File = attachment_File;
    }
}
