package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "List")
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer list_Id;
    private String listName;
    private Timestamp createdAtTime;
    private Timestamp updatedAtTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User mUsers;

    @OneToMany(mappedBy = "mList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Task> tasks;

    public List(int id, String listName, Timestamp createdTimestamp, Timestamp updatedTimestamp, User user) {
        this.listName = listName;
        this.list_Id = id;
        this.createdAtTime = createdTimestamp;
        this.updatedAtTime = updatedTimestamp;
        this.mUsers = user;
    }

    public List() {

    }

    public User getmUsers() {
        return mUsers;
    }

    public void setmUsers(User mUsers) {
        this.mUsers = mUsers;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Integer getListId() {
        return list_Id;
    }

    public void setListId(Integer listId) {
        this.list_Id = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
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

}
