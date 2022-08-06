package com.todo.model;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String tag_Id;

    private String tag_Name;

    private Timestamp created_AtTime;

    private Timestamp updated_AtTime;

    public Tag(){

    }

    public String getTag_Id() {
        return tag_Id;
    }

    public void setTag_Id(String tag_Id) {
        this.tag_Id = tag_Id;
    }

    public String getTag_Name() {
        return tag_Name;
    }

    public void setTag_Name(String tag_Name) {
        this.tag_Name = tag_Name;
    }

    public Timestamp getCreated_AtTime() {
        return created_AtTime;
    }

    public void setCreated_AtTime(Timestamp created_AtTime) {
        this.created_AtTime = created_AtTime;
    }

    public Timestamp getUpdated_AtTime() {
        return updated_AtTime;
    }

    public void setUpdated_AtTime(Timestamp updated_AtTime) {
        this.updated_AtTime = updated_AtTime;
    }
}
