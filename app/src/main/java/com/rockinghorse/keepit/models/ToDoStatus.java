package com.rockinghorse.keepit.models;

/**
 * Created by root on 18/10/16.
 */

public class ToDoStatus implements TaskStatus {
    @Override
    public String getActionText() {
        return "Send me to DOING";
    }

    @Override
    public TaskStatus gotoDoing() throws NoSuchMethodException {
        return new DoingStatus();
    }

    @Override
    public TaskStatus gotoToDo() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    @Override
    public TaskStatus gotoDone() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    @Override
    public TaskStatus gotoDefaultNextStatus() throws NoSuchMethodException {
        return gotoDoing();
    }


    @Override
    public TaskStatus getInstance() {
        return new ToDoStatus();
    }

    @Override
    public String getLabel() {
        return "TO DO";
    }
}
