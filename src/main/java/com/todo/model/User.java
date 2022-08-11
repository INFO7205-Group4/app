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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String fName;
    private String mName;
    private String lName;
    private String emailAddress;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean emailValidated;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp emailSentTime;
    private Timestamp created_AtTime;
    private Timestamp updated_AtTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean confirmationEmailValidated;

    public Boolean getConfirmationEmailValidated() {
        return confirmationEmailValidated;
    }

    public void setConfirmationEmailValidated(Boolean confirmationEmailValidated) {
        this.confirmationEmailValidated = confirmationEmailValidated;
    }

    @OneToMany(mappedBy = "mUsers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<List> lists;

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getEmailValidated() {
        return emailValidated;
    }

    public void setEmailValidated(Boolean emailValidated) {
        this.emailValidated = emailValidated;
    }

    public Timestamp getEmailSentTime() {
        return emailSentTime;
    }

    public void setEmailSentTime(Timestamp timestamp) {
        this.emailSentTime = timestamp;
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

    public Set<List> getLists() {
        return lists;
    }

    public void setLists(Set<List> lists) {
        this.lists = lists;
    }
}
