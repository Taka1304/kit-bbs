package com.example.kit_bbs;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Thread {
    private String id;
    private String subtitle;
    private ArrayList<String> tags;
    private String title;
    private String userId;
    private Timestamp createdAt;

    public Thread() {
        // デフォルトコンストラクタが必要な場合は追加します
    }

    public Thread(String id, String subtitle, ArrayList<String> tags, String title, String userId, Timestamp createdAt) {
        this.id = id;
        this.subtitle = subtitle;
        this.tags = tags;
        this.title = title;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

