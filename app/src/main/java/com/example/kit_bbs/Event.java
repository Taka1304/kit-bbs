package com.example.kit_bbs;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Event {
    private String content;
    private String location;
    private String title;
    private String userId;
    private int currentParticipants;
    private int maxParticipants;
    private Timestamp endDateTime;
    private Timestamp startDateTime;
    private ArrayList<String> tags;

    public Event() {
        // デフォルトのコンストラクタ
    }

    public String getContent() {
        return content;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
