package com.rockinghorse.keepit.models;

/**
 * Created by root on 18/10/16.
 */

public interface TaskStatus {

    public String getActionText();

    public TaskStatus gotoDoing() throws NoSuchMethodException;

    public TaskStatus gotoToDo() throws NoSuchMethodException;

    public TaskStatus gotoDone() throws NoSuchMethodException;

    public TaskStatus gotoDefaultNextStatus() throws NoSuchMethodException;

    public TaskStatus getInstance();

    public String getLabel();
}
