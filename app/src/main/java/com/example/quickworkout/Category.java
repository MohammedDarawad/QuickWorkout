package com.example.quickworkout;

public class Category {
    private int id;
    private String name;
    private int numOfWorkouts;

    public Category(int id, String name, int numOfWorkouts) {
        this.id = id;
        this.name = name;
        this.numOfWorkouts = numOfWorkouts;
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

    public int getNumOfWorkouts() {
        return numOfWorkouts;
    }

    public void setNumOfWorkouts(int numOfWorkouts) {
        this.numOfWorkouts = numOfWorkouts;
    }

    @Override
    public String toString() {
        return name;
    }
}
