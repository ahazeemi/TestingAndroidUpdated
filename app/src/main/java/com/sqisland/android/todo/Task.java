package com.sqisland.android.todo;

import com.google.firebase.database.Exclude;

/**
 * Created by abdul on 4/26/2018.
 */

public class Task {

    @Exclude
    private String taskId;

    private String name;
    private Long timestamp;

    public Task() {
    }

    public Task(String taskId, String name, Long timestamp) {
        this.taskId = taskId;
        this.name = name;
        this.timestamp = timestamp;
    }

    @Exclude
    public String getTaskId() {
        return taskId;
    }

    @Exclude
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Task) {
            Task s = (Task) object;
            return this.taskId.equals(s.taskId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return taskId.hashCode();
    }
}