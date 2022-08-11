package com.todo.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

//@Entity
//@Table(name="Attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer attachment_Id;

    private String attachment_Name;

    @Column(name = "attached_at_time", nullable = true, unique = false)
    private Timestamp attached_at_time;

    private short Attachment_Size;

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

    public Timestamp getAttached_AtTime() {
        return attached_at_time;
    }

    public void setAttached_AtTime(Timestamp attached_AtTime) {
        this.attached_at_time = attached_AtTime;
    }

    public short getAttachment_Size() {
        return Attachment_Size;
    }

    public void setAttachment_Size(short attachment_Size) {
        Attachment_Size = attachment_Size;
    }

    public byte[] getAttachment_File() {
        return attachment_File;
    }

    public void setAttachment_File(byte[] attachment_File) {
        this.attachment_File = attachment_File;
    }

    public Task getmTasks() {
        return mTasks;
    }

    public void setmUsers(Task mTasks) {
        this.mTasks = mTasks;
    }

}
