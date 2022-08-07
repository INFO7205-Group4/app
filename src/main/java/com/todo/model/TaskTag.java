package com.todo.model;


import javax.persistence.*;

//@Entity
//@Table(name="task_tag")
public class TaskTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String tag_Id;

}
