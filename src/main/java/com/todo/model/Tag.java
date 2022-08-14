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
@Table(name = "Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tag_Id;

    private String tagName;

    private Timestamp createdAtTime;

    private Timestamp updatedAtTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User tUsers;

    public Tag() {

    }

    public Integer getTag_Id() {
        return tag_Id;
    }

    public void setTag_Id(Integer tag_Id) {
        this.tag_Id = tag_Id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Timestamp getCreatedAtTime() {
        return createdAtTime;
    }

    public void setCreatedAtTime(Timestamp createdAtTime) {
        this.createdAtTime = createdAtTime;
    }

    public Timestamp getUpdatedAtTime() {
        return updatedAtTime;
    }

    public void setUpdatedAtTime(Timestamp updatedAtTime) {
        this.updatedAtTime = updatedAtTime;
    }

    public User gettUsers() {
        return tUsers;
    }

    public void settUsers(User tUsers) {
        this.tUsers = tUsers;
    }

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "Task", joinColumns = @JoinColumn(name = "tag_Id"),
    // inverseJoinColumns = @JoinColumn(name = "task_Id"))

    // public Set<Task> tasks;

}
