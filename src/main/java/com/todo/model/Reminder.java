package com.todo.model;

import javax.persistence.*;


public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String Reminder_Id;
}
