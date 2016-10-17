package com.rockinghorse.keepit.models;

/**
 * Created by root on 17/10/16.
 */
public class Project {
    private String title;

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
