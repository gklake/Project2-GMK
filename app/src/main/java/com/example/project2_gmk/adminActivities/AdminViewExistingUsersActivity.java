package com.example.project2_gmk.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.project2_gmk.R;
import com.example.project2_gmk.userDatabase.User;
import com.example.project2_gmk.userDatabase.UserDAO;
import com.example.project2_gmk.userDatabase.UserDatabase;

import java.util.List;

public class AdminViewExistingUsersActivity extends AppCompatActivity {

    TextView textViewExistingUsers;
    UserDAO userDAO;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_existing_users);

        wireUpDisplay();

        implementUserDAO();

        printAllUsers();

    }

    private void printAllUsers() {
        if(users.size() <= 0){
            textViewExistingUsers.setText(R.string.no_users_at_store);
        }
        StringBuilder sb = new StringBuilder();
        for(User user : users){
            sb.append(user);
            sb.append("\n");
            sb.append("*+*-*+*-*+*-*+*-*+*-*+*-*");
            sb.append("\n");
        }
        textViewExistingUsers.setText(sb.toString());
    }

    private void implementUserDAO() {
        userDAO = Room.databaseBuilder(this, UserDatabase.class, UserDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
        users = userDAO.getUsers();
    }

    private void wireUpDisplay() {
        textViewExistingUsers = findViewById(R.id.textViewExistingUsers);
        textViewExistingUsers.setMovementMethod(new ScrollingMovementMethod());
    }
}