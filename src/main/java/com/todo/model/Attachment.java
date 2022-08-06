package com.todo.model;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="Attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String attachment_Id;

    private String attachment_Name;

    private Timestamp attached_AtTime;

    private short Attachment_Size;

    @Lob
    @Column(name="Attachment_File")
    private byte[] attachment_File;

    public Attachment(){

    }

    public String getAttachment_Id() {
        return attachment_Id;
    }

    public void setAttachment_Id(String attachment_Id) {
        this.attachment_Id = attachment_Id;
    }

    public String getAttachment_Name() {
        return attachment_Name;
    }

    public void setAttachment_Name(String attachment_Name) {
        this.attachment_Name = attachment_Name;
    }

    public Timestamp getAttached_AtTime() {
        return attached_AtTime;
    }

    public void setAttached_AtTime(Timestamp attached_AtTime) {
        this.attached_AtTime = attached_AtTime;
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
}
