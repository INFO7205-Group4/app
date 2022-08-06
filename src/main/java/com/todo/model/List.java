package com.todo.model;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="List")
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String listId;
    private String listName;
    private Timestamp created_AtTime;
    private Timestamp updated_AtTime;

    public List() {

    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
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
