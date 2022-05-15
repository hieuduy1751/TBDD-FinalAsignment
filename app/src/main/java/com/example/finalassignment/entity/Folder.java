package com.example.finalassignment.entity;

import java.util.Date;

public class Folder {
    private String docId;
    private String name;
    private long createdAt;
    private long updatedAt;

    public Folder(String name) {
        this.name = name;
        this.createdAt = new Date().getTime();
        this.updatedAt = new Date().getTime();
    }

    public Folder(String docId, String name, long createdAt, long updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
