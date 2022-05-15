package com.example.finalassignment.entity;

import java.util.Date;
import java.util.List;

public class Task {
    private String docId;
    private String name;
    private String detail;
    private String tag;
    private String folderId;
    private boolean status;
    private long createdAt;
    private long updatedAt;
    private String ownerId;

    public Task(String name, String detail, String tag, String folderId, String ownerId) {
        this.name = name;
        this.detail = detail;
        this.tag = tag;
        this.folderId = folderId;
        this.ownerId = ownerId;
        this.createdAt = new Date().getTime();
        this.updatedAt = new Date().getTime();
    }

    public Task(String name, String detail, String folderId, String ownerId) {
        this.name = name;
        this.detail = detail;
        this.status = false;
        this.folderId = folderId;
        this.ownerId = ownerId;
        this.createdAt = new Date().getTime();
        this.updatedAt = new Date().getTime();
    }

    public Task(String name, String detail, String tag, boolean status, String folderId, String ownerId) {
        this.name = name;
        this.detail = detail;
        this.status = status;
        this.tag = tag;
        this.folderId = folderId;
        this.ownerId = ownerId;
        this.createdAt = new Date().getTime();
        this.updatedAt = new Date().getTime();
    }

    public Task(String docId, String name, String detail, String tag, boolean status, long createdAt, long updatedAt, String folderId, String ownerId) {
        this.docId = docId;
        this.name = name;
        this.detail = detail;
        this.tag = tag;
        this.status = status;
        this.folderId = folderId;
        this.ownerId = ownerId;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
