package com.example.kit_bbs;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {
    private String id;
    private String content;
    private String location;
    private String title;
    private String userId;
    private String thumbnailId;
    private int currentParticipants;
    private int maxParticipants;
    private Timestamp endDateTime;
    private Timestamp startDateTime;
    private ArrayList<String> tags;

    public Event() {
        // デフォルトのコンストラクタ
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() { return id; }
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

    public String getThumbnailId() {return thumbnailId;}

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
