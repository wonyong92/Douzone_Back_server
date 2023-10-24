package com.example.bootproject.entity;

import lombok.Data;

@Data
public class Employee {
    String employee_id;
    String name;
    String pwd;
    String email;
    boolean manager = false;

    public boolean getAuth() {
        return manager;
    }
}
