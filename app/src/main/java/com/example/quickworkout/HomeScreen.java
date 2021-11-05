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

        Category[] categories = new Category[3];
        categories[0] = new Category(0, "Legs", 4);
        categories[1] = new Category(1, "Arms", 4);
        categories[2] = new Category(2, "Chest", 4);

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
                android.R.layout.simple_list_item_1,
                categories);
        lvCategories.setAdapter(adapter);

        workouts = new Workout[9];
        workouts[0] = new Workout(0, "Back squat", "Target your posterior chain — or the back of your body, including the glutes and hamstrings — with a back squat.", 0);
        workouts[1] = new Workout(1, "Front squat", "Target the front of your body — especially your quads — with a front squat.", 0);
        workouts[2] = new Workout(2, "Lateral lunge", "As humans, we move mostly in the front-to-back planes of movement. Doing side-to-side movements like lateral lunges helps increase stability and strength.", 0);
        workouts[3] = new Workout(0, "Incline Bicep Curl", "Sit on an incline bench and hold a dumbbell in each hand at arm's length. Use your biceps to curl the dumbbell until it reaches your shoulder, then lower them back down to your side and repeat.", 1);
        workouts[4] = new Workout(1, "Concentration Curl", "Sit down on bench and rest your right arm against your right leg, letting the weight hang down. Curl the weight up, pause, then lower. Repeat with the other arm.", 1);
        workouts[5] = new Workout(2, "Twisting Dumbbell Curl", " Hold a dumbbell in each hand at your side with palms facing each other. Use your bicep to curl the dumbbells up to your shoulders alternately, twisting your palms to face your chest as you lift them. Slowly lower the dumbbells back down to your side and repeat.", 1);
        workouts[6] = new Workout(0, "Dumbbell Squeeze Press", " Lie on a flat bench and hold a dumbbell in each hand. Maintain a neutral grip and begin with your arms straight, directly above you. Bend your arms and lower them to the side of your body so the dumbbells lie just above your chest. Pause and then lift your arms to repeat.", 2);
        workouts[7] = new Workout(1, "Incline barbell bench press", " Lie back on a bench set to an incline angle and lift a barbell to shoulder height, palms facing away from you. Breathe out as you press up with both arms. Lock out your arms and squeeze your chest before returning slowly to the start position.", 2);
        workouts[8] = new Workout(2, "Incline dumbbell bench press", "Lie back on a bench set to a 45-degree angle and lift the weights over your chest, palms facing away from you. Slowly lower one weight, then drive it back up and squeeze your chest at the top.Repeat with the other side.", 2);

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