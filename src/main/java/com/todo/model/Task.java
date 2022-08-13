package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

import java.sql.Timestamp;
import com.todo.model.List;
import java.util.Set;

@Entity
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer task_Id;
    private String task_Summary;
    private String task_Name;
    private Timestamp dueDate;
    private short Task_Priority;
    private short Task_State;
    private Timestamp created_AtTime;
    private Timestamp updated_AtTime;

    @OneToMany(mappedBy = "mTasks", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Attachment> attachments;

    @OneToMany(mappedBy = "cTask", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "rTask", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Reminder> reminders;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List mList;

    public Task() {

    }

    public short getTask_State() {
        return Task_State;
    }

    public void setTask_State(short task_State) {
        Task_State = task_State;
    }

    public List getmList() {
        return mList;
    }

    public void setmList(List mList) {
        this.mList = mList;
    }

    public Integer getTask_Id() {
        return task_Id;
    }

    public void setTask_Id(Integer task_Id) {
        this.task_Id = task_Id;
    }

    public String getTask_Summary() {
        return task_Summary;
    }

    public void setTask_Summary(String task_Summary) {
        this.task_Summary = task_Summary;
    }

    public String getTask_Name() {
        return task_Name;
    }

    public void setTask_Name(String task_Name) {
        this.task_Name = task_Name;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public short getTask_Priority() {
        return Task_Priority;
    }

    public void setTask_Priority(short task_Priority) {
        Task_Priority = task_Priority;
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

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachment(Set<Attachment> attachments) {
        this.attachments = attachments;
    }
}
