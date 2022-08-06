package com.todo.model;

import javax.persistence.*;


public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String Comment_Id;
}
