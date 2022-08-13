package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tag_Id;

    private String tag_Name;

    private Timestamp created_AtTime;

    private Timestamp updated_AtTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="userId",nullable=false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User tUsers;

    public Tag(){

    }

    public Integer getTag_Id() {
        return tag_Id;
    }

    public void setTag_Id(Integer tag_Id) {
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

    public User gettUsers() {
        return tUsers;
    }

    public void settUsers(User tUsers) {
        this.tUsers = tUsers;
    }
}
