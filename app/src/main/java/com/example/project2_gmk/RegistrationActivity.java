package com.example.project2_gmk;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2_gmk.userDatabase.User;
import com.example.project2_gmk.userDatabase.UserDAO;
import com.example.project2_gmk.userDatabase.UserDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextRegisterName;
    EditText editTextRegisterPassword;
    Button buttonRegister;
    TextView textViewAlreadyRegistered;
    UserDAO userDAO;
    int attemptCounter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.hide();

        wireUpDisplay();

        implementUserDAO();

        buttonRegister.setOnClickListener(v -> {
            String userName = editTextRegisterName.getText().toString().trim();
            String password = editTextRegisterPassword.getText().toString().trim();
            boolean admin = isAdmin();
            User newUser = new User(userName, password, admin);
            if(validAccount(newUser)){
                //Adding user to database and going to login activity
                userDAO.insert(newUser);
                StyleableToast.makeText(RegistrationActivity.this, "Registration Successful!", R.style.specialToast).show();
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            } else {
                attemptCounter--;
                if(attemptCounter > 0) {
                    StyleableToast.makeText(RegistrationActivity.this, attemptCounter + " Attempt(s) Left", R.style.specialToast).show();
                }
                if(attemptCounter == 0){
                    StyleableToast.makeText(RegistrationActivity.this, "Attempts Expired: Returning to Log In Page", R.style.specialToast).show();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                }
            }
        });

        textViewAlreadyRegistered.setOnClickListener(v -> startActivity(new Intent(RegistrationActivity.this, MainActivity.class)));


    }

    private void implementUserDAO() {
        userDAO = Room.databaseBuilder(this, UserDatabase.class, UserDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    private void wireUpDisplay() {
        editTextRegisterName = findViewById(R.id.editTextRegisterName);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewAlreadyRegistered = findViewById(R.id.textViewAlreadyRegistered);
    }


    private boolean validAccount(User user){
        if(userDAO.getUserFromUsername(user.getUsername()) != null){
            StyleableToast.makeText(RegistrationActivity.this, "Username already taken, please choose a different one.", R.style.specialToast).show();
            return false;
        }
        if(user.getUsername().isEmpty() || user.getPassword().length() < 6){
            StyleableToast.makeText(this, "Please fill all fields.", R.style.specialToast).show();
            return false;
        }
        return true;
    }

    private boolean isAdmin(){
        return editTextRegisterPassword.toString().equals("admin2") && editTextRegisterName.toString().equals("admin2");
    }

}