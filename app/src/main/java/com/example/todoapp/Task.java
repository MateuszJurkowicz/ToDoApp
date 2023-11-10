package com.example.todoapp;

import java.util.Date;
import java.util.UUID;

public class Task {
    public Category category;
    public void setCategory(Category c)
    {
        category = c;
    }
    public Category getCategory()
    {
        return category;
    }
    private UUID id;
    private String name;
    private Date date;
    private boolean done;
    public Task()
    {
        id = UUID.randomUUID();
        date = new Date();
    }
    public void setName(String s)
    {
        name = s;
    }
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date d) { date = d; }
    public String getName() { return name; }
    public UUID getId() { return id; }
    public void setDone(boolean d)
    {
        done = d;
    }
    public boolean isDone()
    {
        return done;
    }
}
