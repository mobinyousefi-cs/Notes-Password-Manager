package com.mobinyousefi.notespasswordmanager.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

public class Note {
    @Exclude
    private String id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Note() {
        // Required empty constructor for Firestore
    }

    public Note(String title, String content, Timestamp createdAt, Timestamp updatedAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title != null ? title : "";
    }

    public String getContent() {
        return content != null ? content : "";
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
