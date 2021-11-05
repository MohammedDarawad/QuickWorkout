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
    private final int min = -1;
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

    private int nm = -1;
    private int ns = -1;

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
        flag = true;
        if (nm == -1) {
            tvRemainingTime.setText((spTime.getSelectedItem().toString() + " : 00"));
            nm = (Integer.parseInt(spTime.getSelectedItem().toString()));
            ns = 0;
        }
        thread = new Thread(new Timer((nm * 60) + ns));
        thread.start();
        btStart.setVisibility(View.INVISIBLE);
        btStop.setVisibility(View.VISIBLE);
    }

    public void stop(View view) {
        flag = false;

        btStart.setVisibility(View.VISIBLE);
        btStop.setVisibility(View.INVISIBLE);
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
                        tvRemainingTime.post(new Runnable() {
                            @Override
                            public void run() {
                                nm = i / 60;
                                ns = i % 60;
                                if (nm == 0 && ns == 0) {
                                        tvRemainingTime.setText("Done.. Good Job");
                                        finish();
                                } else
                                    tvRemainingTime.setText(nm + " : " + ns);
                            }
                        });
                        i--;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}