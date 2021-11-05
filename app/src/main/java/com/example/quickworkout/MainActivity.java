package com.example.quickworkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickworkout.model.User;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox cbRememberMe;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.edtUsername_Login);
        edtPassword = findViewById(R.id.edtPassword_Login);
        cbRememberMe = findViewById(R.id.cbRememberMe);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        gson = new Gson();

        if (prefs.contains("logedinUser")) {
            User user = gson.fromJson(prefs.getString("logedinUser", ""), User.class);
            edtUsername.setText(user.getUsername());
            edtPassword.setText(user.getPassword());
            cbRememberMe.setChecked(true);
        }
    }

    public void login(View view) {
        boolean userExist = false;
        if (!dataValidation()) {
            if (prefs.contains("users")) {
                String usersJSON = prefs.getString("users", "");
                User[] users = gson.fromJson(usersJSON, User[].class);
                for (int i = 0; i < users.length; i++) {
                    if (users[i].getUsername().equals(edtUsername.getText().toString()) && users[i].getPassword().equals(edtPassword.getText().toString())) {
                        userExist = true;

                        if (cbRememberMe.isChecked()) {
                            String newUsersJSON = gson.toJson(users[i]);
                            editor.putString("logedinUser", newUsersJSON);
                            editor.commit();
                        } else {
                            if (prefs.contains("logedinUser")) {
                                editor.remove("logedinUser");
                                editor.commit();
                            }
                        }

                        Intent intent = new Intent(this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            if (!userExist) {
                Toast.makeText(this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signup(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    private boolean dataValidation() {
        boolean isError = false;

        if (edtUsername.getText().toString().isEmpty()) {
            isError = true;
            edtUsername.setError("Field Cannot Be Empty");
        } else if (edtPassword.getText().toString().isEmpty()) {
            isError = true;
            edtPassword.setError("Field Cannot Be Empty");
        }
        return isError;
    }
}