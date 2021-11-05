package com.example.quickworkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WorkoutDetail extends AppCompatActivity {
    private final Handler handler = new Handler();
    private int min = -1;
    private ImageView ivGif;
    private TextView tvName;
    private TextView tvDescription;
    private Spinner spTime;
    private TextView tvRemainingTime;
    private Button btStart;
    private Button btStop;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private Intent intent;
    private Workout workout;
    private Runnable runnable;
    private Thread thread;
    private boolean flag = false;

    private int nm = 0;
    private int ns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        intent = getIntent();
        gson = new Gson();

        ivGif = findViewById(R.id.ivGif);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        spTime = findViewById(R.id.spTime);
        tvRemainingTime = findViewById(R.id.tvRemainingTime);
        btStart = findViewById(R.id.btStart);
        btStop = findViewById(R.id.btStop);


        workout = gson.fromJson(intent.getStringExtra("workout"), Workout.class);

        tvName.setText(workout.getName());
        tvDescription.setText(workout.getDescription());

        int categoryId = workout.getCategory();
        int workoutId = workout.getId();
        System.out.println("R.drawable.w" + categoryId + "" + workoutId);
        String imageId = "w" + categoryId + "" + workoutId;
        String imageId2 = "R.drawable.w00";

        int drawableResourceId = this.getResources().getIdentifier(imageId, "drawable", this.getPackageName());

        Glide.with(this).load(drawableResourceId).into(ivGif);

        ArrayList<Integer> time = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int j = i;
            time.add(++j);
        }

        ArrayAdapter adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, time);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTime.setAdapter(adapter);


    }

    public void start(View view) {
        tvRemainingTime.setText((spTime.getSelectedItem().toString() + " : 00"));
        flag = true;
        if (min == -1)
            min = (Integer.parseInt(spTime.getSelectedItem().toString()));
        else{

        }

        thread = new Thread(new Timer(min * 60));
        thread.start();
        btStart.setVisibility(View.INVISIBLE);
        btStop.setVisibility(View.VISIBLE);
    }

    public void stop(View view) {
        flag = false;
        btStart.setVisibility(View.VISIBLE);
        btStop.setVisibility(View.INVISIBLE);
    }

    public void restart(View view) {

    }

    class Timer implements Runnable {
        int seconds = 0;
        int i = 0;

        public Timer(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            i = seconds;
            while (flag) {
                if (i >= 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tvRemainingTime.post(new Runnable() {
                        @Override
                        public void run() {
                            int min = i / 60;
                            int sec = i % 60;
                            tvRemainingTime.setText(min + " : " + sec);
                        }
                    });
                    i--;
                }
            }
        }
    }
}