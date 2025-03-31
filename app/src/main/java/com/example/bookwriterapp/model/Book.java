package com.example.bookwriterapp.model;

public class Book {
    private int id;
    private String title;
    private String lastEdited; // Добавлено поле

    public Book(int id, String title, String lastEdited) {
        this.id = id;
        this.title = title;
        this.lastEdited = lastEdited;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLastEdited() { // Добавленный метод
        return lastEdited;
    }

    public void setLastEdited(String lastEdited) { // Добавленный метод
        this.lastEdited = lastEdited;
    }
}
