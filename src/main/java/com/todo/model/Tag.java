package com.todo.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String tag_Id;

    private String tagName;

    private Timestamp createdAtTime;

    private Timestamp updatedAtTime;

    @ManyToMany(mappedBy = "tags")
    private List<Task> allTasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User mUsers;

    // private List<Task> taskSet = new ArrayList<>();;

    public Tag() {

    }

    public String getTag_Id() {
        return tag_Id;
    }

    public void setTag_Id(String tag_Id) {
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

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public void setAllTasks(Task task) {
        allTasks.add(task);
    }

    public User getmUsers() {
        return mUsers;
    }

    public void setmUsers(User mUsers) {
        this.mUsers = mUsers;
    }

}
