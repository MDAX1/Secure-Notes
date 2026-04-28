package com.securenotes.model;

public class Note {

    private int id;
    private int userId;
    private String title;
    private String content;

    public Note(int id, int userId, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "[ID: " + id + "] " + title + "\n    " + content;
    }
}