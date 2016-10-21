package com.rockinghorse.keepit.models;


public class Task {
    private String title;
    private TaskStatus status;
    private int color;
    private int defaultColor = new Integer(0x2196f3);

    public Task(String title) {

        this.title = title;
        this.status = new ToDoStatus();
        this.color = defaultColor;
    }

    public Task(String title, TaskStatus ts) {

        this.title = title;
        this.status = ts;
        this.color = defaultColor;

    }

    public Task(String title, TaskStatus ts, int color) {

        this.title = title;
        this.status = ts;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getColorLabel() {
        return "#" + Integer.toHexString(color);
    }
}
