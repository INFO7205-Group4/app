package com.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "user_id")
    private Integer userId;
    //@Column(name = "f_name")
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

    public Users() {
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

    public void setEmailSentTime(Timestamp emailSentTime) {
        this.emailSentTime = emailSentTime;
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

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((fName == null) ? 0 : fName.hashCode());
//        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
//        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
//       // long temp;
//       // temp = Double.doubleToLongBits(salary);
//       // result = prime * result + (int) (temp ^ (temp >>> 32));
//        result = result = prime * result;
//        return result;
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Users other = (Users) obj;
//        if (emailAddress == null) {
//            if (other.emailAddress != null)
//                return false;
//        } else if (!emailAddress.equals(other.emailAddress))
//            return false;
////        if (user_Id == null) {
////            if (other.user_Id != null)
////                return false;
////        } else if (!user_Id.equals(other.user_Id))
////            return false;
//        if (fName == null) {
//            if (other.fName != null)
//                return false;
//        } else if (!fName.equals(other.fName))
//            return false;
////        if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
////            return false;
//        return true;
//    }

//    public Users orElseThrow(Object o) {
//        return
//    }
}
