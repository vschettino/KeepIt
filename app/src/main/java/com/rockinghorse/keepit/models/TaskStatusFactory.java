package com.rockinghorse.keepit.models;

/**
 * Created by root on 07/12/16.
 */
public class TaskStatusFactory {
    private static TaskStatusFactory ourInstance = new TaskStatusFactory();

    public static TaskStatusFactory getInstance() {
        return ourInstance;
    }

    private TaskStatusFactory() {
    }

    public TaskStatus newStatus(String name) {
        switch (name) {
            case "TO DO":
                return new ToDoStatus();
            case "DOING":
                return new DoingStatus();
            case "DONE":
                return new DoneStatus();
            default:
                throw new IllegalArgumentException("Não existe o estado especificado");
        }

    }
}
