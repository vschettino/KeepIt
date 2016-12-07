package com.rockinghorse.keepit.models;

/**
 * Created by root on 18/10/16.
 */

public class DoneStatus implements TaskStatus {
    @Override
    public String getActionText() {
        return "Send me back do DOING";
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
        return new DoneStatus();
    }

    @Override
    public String getLabel() {
        return "DONE";
    }
}
