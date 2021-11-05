package com.example.quickworkout;

public class Timer implements java.lang.Runnable{
    private int min;

    public Timer(int min) {
        this.min = min * 60;
    }

    @Override
    public void run() {
        this.runTimer();
    }

    public void runTimer(){
        int i = min;
        while (i>0){
            System.out.println("Remaining: "+i+" seconds");
            try {
                i--;
                Thread.sleep(1000);    // 1000L = 1000ms = 1 second
            }
            catch (InterruptedException e) {
                //I don't think you need to do anything for your particular problem
            }
        }
    }

}