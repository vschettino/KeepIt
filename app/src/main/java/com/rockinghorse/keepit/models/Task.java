package com.rockinghorse.keepit.models;

/**
 * Created by root on 17/10/16.
 */
public class Task {
    private String title;
    private TaskStatus status;

    public Task(String title) {

        this.title = title;
        this.status = new ToDoStatus();
    }

    public Task(String title, TaskStatus ts) {

        this.title = title;
        this.status = ts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return this.title;
    }

    public String getActionLabel() {
        return this.status.getActionText();
    }
}
