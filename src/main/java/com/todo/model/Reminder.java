package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "Reminder")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reminder_Id;

    private Timestamp reminderDateTime;

    private Timestamp createdAtTime;

    private Timestamp updatedAtTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_Id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Task rTask;

    public Integer getReminder_Id() {
        return reminder_Id;
    }

    public void setReminder_Id(Integer reminder_Id) {
        this.reminder_Id = reminder_Id;
    }

    public Timestamp getReminderDateTime() {
        return reminderDateTime;
    }

    public void setReminderDateTime(Timestamp reminderDateTime) {
        this.reminderDateTime = reminderDateTime;
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

    public Task getrTask() {
        return rTask;
    }

    public void setrTask(Task rTask) {
        this.rTask = rTask;
    }
}