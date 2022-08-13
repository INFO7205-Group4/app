package com.todo.model;

import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

public class TagTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagTask_Id;

}
