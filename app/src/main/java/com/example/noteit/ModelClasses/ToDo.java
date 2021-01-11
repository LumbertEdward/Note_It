package com.example.noteit.ModelClasses;

public class ToDo {
    private String title;
    private String category;
    private String status;
    private String id;

    public ToDo(String title, String category, String status, String id) {
        this.title = title;
        this.category = category;
        this.status = status;
        this.id = id;
    }

    public ToDo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
