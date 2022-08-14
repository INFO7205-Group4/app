package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer task_Id;
    private String taskSummary;
    private String taskName;
    private Timestamp dueDate;
    private short taskPriority;
    private short taskState;
    private Timestamp createdAtTime;
    private Timestamp updatedAtTime;
    private String[] tags;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String tagId) {
        if (this.tags == null) {
            this.tags = new String[1];
            this.tags[0] = tagId;
        } else {
            String[] temp = new String[this.tags.length + 1];
            for (int i = 0; i < this.tags.length; i++) {
                temp[i] = this.tags[i];
            }
            temp[this.tags.length] = tagId;
            this.tags = temp;
        }
    }

    @OneToMany(mappedBy = "mTasks", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Attachment> attachments;

    @OneToMany(mappedBy = "cTask", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "rTask", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Reminder> reminders;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List mList;

    public Task() {

    }

    public short getTaskState() {
        return taskState;
    }

    public void setTaskState(short taskState) {
        this.taskState = taskState;
    }

    public List getmList() {
        return mList;
    }

    public void setmList(List mlist) {
        this.mList = mlist;
    }

    public Integer getTask_Id() {
        return task_Id;
    }

    public void setTask_Id(Integer task_Id) {
        this.task_Id = task_Id;
    }

    public String getTaskSummary() {
        return taskSummary;
    }

    public void setTaskSummary(String taskSummary) {
        this.taskSummary = taskSummary;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public short getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(short taskPriority) {
        this.taskPriority = taskPriority;
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

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachment(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "Tag", joinColumns = @JoinColumn(name = "task_Id"),
    // inverseJoinColumns = @JoinColumn(name = "tag_Id"))
    // public Set<Tag> tags;
}
