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
    private String list_name;
    private Timestamp created_AtTime;
    private Timestamp updated_AtTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User mUsers;

    @OneToMany(mappedBy = "mList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Task> tasks;

    public List(int id, String listname, Timestamp createdTimestamp, Timestamp updatedTimestamp, User user) {
        this.list_name = listname;
        this.list_Id = id;
        this.created_AtTime = createdTimestamp;
        this.updated_AtTime = updatedTimestamp;
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

    public Integer getList_Id() {
        return list_Id;
    }

    public void setList_Id(Integer list_Id) {
        this.list_Id = list_Id;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
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
