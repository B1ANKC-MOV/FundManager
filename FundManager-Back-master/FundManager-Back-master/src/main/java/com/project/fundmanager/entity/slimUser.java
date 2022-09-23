package com.project.fundmanager.entity;

import com.project.fundmanager.entity.User;

public class slimUser {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public slimUser(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public slimUser(User toCopy){
        id=toCopy.getId();
        name=toCopy.getName();
    }
}
