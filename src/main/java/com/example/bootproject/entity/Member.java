package com.example.bootproject.entity;

import lombok.Data;

@Data
public class Member {
    String member_id;
    String name;
    String pwd;
    String email;
    boolean manager = false;

    public boolean getAuth() {
        return manager;
    }
}
