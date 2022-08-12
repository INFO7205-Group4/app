package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name="Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String Comment_Id;

    private String Comment;

    private Timestamp created_AtTime;

    private Timestamp updated_AtTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="taskId",nullable=false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Task cTask;

    public String getComment_Id() {
        return Comment_Id;
    }

    public void setComment_Id(String comment_Id) {
        Comment_Id = comment_Id;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
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

    public Task getcTask() {
        return cTask;
    }

    public void setcTask(Task cTask) {
        this.cTask = cTask;
    }
}
