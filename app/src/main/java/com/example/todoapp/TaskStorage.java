package com.example.todoapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskStorage {
    private static final TaskStorage taskStorage = new TaskStorage();
    private List<Task> tasks;
    public static TaskStorage getInstance() { return taskStorage; }
    private TaskStorage() {
        tasks = new ArrayList<Task>();
        for (int i = 1; i<=150; i++) {
            Task task = new Task();
            task.setName("Pilne zadanie numer " + i);
            task.setDone(i%3 == 0);
            if(i%3 == 0)
            {
                task.setCategory(Category.STUDIES);
            }
            else
            {
                task.setCategory(Category.HOME);
            }
            tasks.add(task);

        }
    }
    public Task getTask(UUID i) {
        for (Task task : tasks) {
            if (task.getId().equals(i)) {
                return task;
            }
        }
        return null;
    }
    public List<Task> getTasks()
    {
        return tasks;
    }
}
