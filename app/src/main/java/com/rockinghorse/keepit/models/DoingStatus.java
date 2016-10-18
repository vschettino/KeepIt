package com.rockinghorse.keepit.models;

/**
 * Created by root on 18/10/16.
 */

public class DoingStatus implements TaskStatus {
    @Override
    public String getActionText() {
        return "Send me to DONE";
    }

    @Override
    public TaskStatus gotoDoing() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    @Override
    public TaskStatus gotoToDo() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    @Override
    public TaskStatus gotoDone() throws NoSuchMethodException {
        return new DoneStatus();
    }

    @Override
    public TaskStatus getInstance() {
        return new DoingStatus();
    }

    @Override
    public String getLabel() {
        return "DOING";
    }
}
