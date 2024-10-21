package com.example.todo;

public class Task {
    private String name;
    private boolean completed;
    private int id; // Thêm ID

    // Constructor
    public Task(String name) {
        this.name = name;
        this.completed = false; // Mặc định completed là false
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Constructor với completed
    public Task(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(
            boolean completed)
    {
        this.completed = completed;
    }
}