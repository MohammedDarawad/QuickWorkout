package com.example.quickworkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    private ListView lvCategories;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private Workout[] workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        lvCategories = findViewById(R.id.lvCategories);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPrefs.edit();

        gson = new Gson();

        Category[] categories = new Category[5];
        categories[0] = new Category(0, "Legs", 4);
        categories[1] = new Category(1, "Arms", 4);
        categories[2] = new Category(2, "Chest", 4);
        categories[3] = new Category(3, "Shoulders", 4);
        categories[4] = new Category(4, "Back", 4);

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
                android.R.layout.simple_list_item_1,
                categories);
        lvCategories.setAdapter(adapter);

        workouts = new Workout[5];
        workouts[0] = new Workout(0, "Back squat", "Target your posterior chain — or the back of your body, including the glutes and hamstrings — with a back squat.", 0);
        workouts[1] = new Workout(1, "Front squat", "Target the front of your body — especially your quads — with a front squat.", 0);
        workouts[2] = new Workout(2, "Lateral lunge", "As humans, we move mostly in the front-to-back planes of movement. Doing side-to-side movements like lateral lunges helps increase stability and strength.", 0);
        workouts[3] = new Workout(0, "Shoulders", "4", 1);
        workouts[4] = new Workout(0, "Back", "5", 2);

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(HomeScreen.this, WorkoutList.class);
                intent.putExtra("id", id);
                intent.putExtra("name", categories[position].getName());

                List<Workout> chosenCategoryWorkouts = new ArrayList<Workout>();

                for (int i = 0; i < workouts.length; i++) {
                    if (workouts[i].getCategory() == id) {
                        chosenCategoryWorkouts.add(workouts[i]);
                    }
                }
                String usersJSON = gson.toJson(chosenCategoryWorkouts);
                intent.putExtra("workouts", usersJSON);

                startActivity(intent);
            }
        });
    }

    public void viewBookmarks(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}