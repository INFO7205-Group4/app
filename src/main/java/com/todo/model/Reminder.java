package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name="Reminder")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reminder_Id;

    private Timestamp reminder_DateTime;

    private Timestamp created_AtTime;

    private Timestamp updated_AtTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="task_Id",nullable=false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Task rTask;

    public Integer getReminder_Id() {
        return reminder_Id;
    }

    public void setReminder_Id(Integer reminder_Id) {
        this.reminder_Id = reminder_Id;
    }

    public Timestamp getReminder_DateTime() {
        return reminder_DateTime;
    }

    public void setReminder_DateTime(Timestamp reminder_DateTime) {
        this.reminder_DateTime = reminder_DateTime;
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

    public Task getrTask() {
        return rTask;
    }

    public void setrTask(Task rTask) {
        this.rTask = rTask;
    }
}
