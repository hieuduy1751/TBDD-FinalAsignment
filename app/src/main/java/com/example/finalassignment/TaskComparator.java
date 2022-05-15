package com.example.finalassignment;

import com.example.finalassignment.entity.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task task, Task t1) {
        return task.isStatus() ? 1 : -1;
    }
}
