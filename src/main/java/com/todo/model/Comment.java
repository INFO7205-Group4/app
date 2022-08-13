package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name="Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comment_Id;

    private String comment;

    private Timestamp created_AtTime;

    private Timestamp updated_AtTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="task_Id",nullable=false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Task cTask;


    public Integer getComment_Id() {
        return comment_Id;
    }

    public void setComment_Id(Integer comment_Id) {
        this.comment_Id = comment_Id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
