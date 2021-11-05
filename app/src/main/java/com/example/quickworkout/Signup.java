package com.example.quickworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.quickworkout.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtPasswordConf;

    private RadioGroup rgGender;
    private RadioButton rbSelected;
    private int selectedId;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtUsername = findViewById(R.id.edtUsername_Login);
        edtPassword = findViewById(R.id.edtPassword_Login);
        edtPasswordConf = findViewById(R.id.edtPasswordConf);

        rgGender = findViewById(R.id.rgGender);
        selectedId = rgGender.getCheckedRadioButtonId();
        rbSelected = findViewById(selectedId);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        gson = new Gson();
    }

    public void signup(View view) {
        if (!dataValidation()) {
            int id;
            if (prefs.contains("lastIdNumber")) {
                id = Integer.parseInt(prefs.getString("lastIdNumber", "")) + 1;
            } else {
                id = 0;
                editor.putString("lastIdNumber", String.valueOf(0));
                editor.commit();
            }

            User newUser = new User(id, edtUsername.getText().toString(), edtPassword.getText().toString(), rbSelected.getText().toString());

            String booksString = gson.toJson(newUser);

            if (prefs.contains("users")) {
                String usersJSON = prefs.getString("users", "");
                List<User> users = gson.fromJson(usersJSON, ArrayList.class);
                users.add(newUser);

                String newUsersJSON = gson.toJson(users);
                editor.putString("users", newUsersJSON);
                editor.commit();
            } else {
                List<User> users = new ArrayList<User>();
                users.add(newUser);
                String usersJSON = gson.toJson(users);
                editor.putString("users", usersJSON);
                editor.commit();
            }

            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean dataValidation() {
        boolean isError = false;

        if (edtUsername.getText().toString().isEmpty()) {
            isError = true;
            edtUsername.setError("Field Cannot Be Empty");
        } else if (edtPassword.getText().toString().isEmpty()) {
            isError = true;
            edtPassword.setError("Field Cannot Be Empty");
        } else if (edtPasswordConf.getText().toString().isEmpty()) {
            isError = true;
            edtPasswordConf.setError("Field Cannot Be Empty");
        } else if (!edtPasswordConf.getText().toString().equals(edtPassword.getText().toString())) {
            isError = true;
            edtPasswordConf.setError("Passwords Don't Match");
        }
        return isError;
    }
}