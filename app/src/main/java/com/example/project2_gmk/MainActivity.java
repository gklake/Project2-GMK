package com.example.project2_gmk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2_gmk.userDatabase.User;
import com.example.project2_gmk.userDatabase.UserDAO;
import com.example.project2_gmk.userDatabase.UserDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewAttemptsInfo;
    TextView textViewRegister;
    UserDAO userDAO;
    List<User> users;
    SharedPreferences sharedPreferences;
    int attemptCounter = 3;

    private static final String SHARED_PREF_NAME = "pref";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_DayNight_DarkActionBar);
        setContentView(R.layout.activity_main);


        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.hide();

        implementUserDAO();

        wireUpDisplay();

        setUpSharedPrefs();

        buttonLogin.setOnClickListener(v -> {

            String username = editTextUsername.getText().toString().trim();
            String password1 = editTextPassword.getText().toString().trim();
            User user = new User(username, password1, isAdmin(username, password1));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME, editTextUsername.getText().toString());
            editor.putString(KEY_PASSWORD, editTextPassword.getText().toString());
            editor.apply();

            if(userDAO.getUser(user.getUsername(), user.getPassword()) != null){
                StyleableToast.makeText(MainActivity.this, "Login Successful! Hi " + user.getUsername(), R.style.specialToast).show();
                //Going to HomePageActivity.java
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            } else {
                attemptCounter--;
                StyleableToast.makeText(MainActivity.this, "Unregistered User or Invalid Credentials", R.style.specialToast).show();
                textViewAttemptsInfo.setText("Number of Attempts Remaining: " + attemptCounter);
                if(attemptCounter == 0){
                    buttonLogin.setEnabled(false);
                }
                editor.clear();
                editor.apply();
            }
        });

        //Going to RegistrationActivity.java
        textViewRegister.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegistrationActivity.class)));

    }

    private void implementUserDAO() {
        userDAO = Room.databaseBuilder(this, UserDatabase.class, UserDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
        users = userDAO.getUsers();
        if(users.size() <= 0){
            User testUser = new User("testuser1", "testuser1", false);
            users.add(testUser);
            User admin = new User("admin2", "admin2", true);
            users.add(admin);
            userDAO.insert(testUser, admin);
        }
    }

    private void setUpSharedPrefs() {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        if(name != null){
            //Going to HomePageActivity.java
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
        }
    }

    private void wireUpDisplay() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewAttemptsInfo = findViewById(R.id.textViewAttemptsInfo);
        textViewRegister = findViewById(R.id.textViewRegister);
    }

    private boolean isAdmin(String username, String password){
        return username.equals("admin2") && password.equals("admin2");
    }

}