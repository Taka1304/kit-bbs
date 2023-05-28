package com.example.kit_bbs;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private ArrayList<UsersEvent> event;
    private String name;
    private ArrayList<String> tags;

    public User () {}

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<UsersEvent> getEvent() {
        return event;
    }

    public String getName() {
        return name;
    }
}
