package com.rockinghorse.keepit.models;


import java.util.HashMap;
import java.util.Map;

public class Task {
    public String id;
    public String title;
    public String date;
    public String info;

    private TaskStatus status;
    private String statusString;
    public int color;
    public String project_id;
    public int defaultColor = Integer.valueOf(0x2196f3);

    public int getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TaskStatus getStatus() {
        if (status == null)
            this.status = TaskStatusFactory.getInstance().newStatus(statusString);
        return status;
    }


    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Task(String title) {

        this.title = title;
        this.status = new ToDoStatus();
        this.color = defaultColor;
    }

    public Task() {
    }

    public Task(String title, TaskStatus ts) {

        this.title = title;
        this.status = ts;
        this.color = defaultColor;

    }


    public Task(String id, String title, String date, int color, String info, String project_id) {
        this.project_id = project_id;
        this.title = title;
        this.id = id;
        this.date = date;
        this.info = info;
        this.color =  color;
        ToDoStatus status = new ToDoStatus();
        this.statusString = status.getLabel();
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

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.status = TaskStatusFactory.getInstance().newStatus(statusString);
        this.statusString = statusString;
    }

    public String getId() {
        return this.id;
    }

    public String getActionText() {
        return this.getStatus().getActionText();
    }

    public void gotoDoing() throws NoSuchMethodException {
        this.status = this.getStatus().gotoDoing();
        this.statusString = this.getStatus().getLabel();
    }

    public void gotoToDo() throws NoSuchMethodException {
        this.status = this.getStatus().gotoToDo();
        this.statusString = this.getStatus().getLabel();

    }

    public void gotoDone() throws NoSuchMethodException {
        this.status = this.getStatus().gotoDone();
        this.statusString = this.getStatus().getLabel();

    }

    public void gotoDefaultNextStatus() throws NoSuchMethodException {
        this.status = this.getStatus().gotoDefaultNextStatus();
        this.statusString = this.getStatus().getLabel();
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", getId());
        result.put("title", getTitle());
        result.put("statusString", getStatusString());
        result.put("date", getDate());
        result.put("info", getInfo());
        result.put("color", getColor());
        result.put("project_id", getProject_id());
        return result;
    }
}
