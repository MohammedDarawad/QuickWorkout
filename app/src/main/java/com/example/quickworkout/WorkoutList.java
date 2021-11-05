package com.example.quickworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WorkoutList extends AppCompatActivity {
    private TextView tvHeader;
    private ListView lvWorkouts;

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private Gson gson;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);
        intent = getIntent();

        lvWorkouts = findViewById(R.id.lvWorkouts);

        tvHeader = findViewById(R.id.tvHeader);
        tvHeader.setText(intent.getStringExtra("name") + " Workouts");
        gson = new Gson();

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPrefs.edit();

        String workoutsJSON = intent.getStringExtra("workouts");
        Workout[] workouts = gson.fromJson(workoutsJSON, Workout[].class);

        ArrayAdapter<Workout> adapter = new ArrayAdapter<Workout>(this,
                android.R.layout.simple_list_item_1,
                workouts);
        lvWorkouts.setAdapter(adapter);

        lvWorkouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String workoutJSON = gson.toJson(workouts[position]);

                Intent newintent = new Intent(WorkoutList.this, WorkoutDetail.class);
                newintent.putExtra("workout", workoutJSON);
                startActivity(newintent);
            }
        });
    }
}