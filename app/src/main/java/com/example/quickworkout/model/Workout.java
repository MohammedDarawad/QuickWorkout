package com.example.quickworkout.model;

public class Workout {
    private int id;
    private String name;
    private String Description;
    private int Category;

    public Workout(int id, String name, String description, int category) {
        this.id = id;
        this.name = name;
        Description = description;
        Category = category;
    }

    public int getCategory() {
        return Category;
    }

    public void setCategory(int category) {
        Category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
