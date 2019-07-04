package com.sqisland.android.hello;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<User> users;
    private AllUsersCallRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<>();

        users.add(new User("Umair"));
        users.add(new User("Ahmed"));
        users.add(new User("Talha"));
        users.add(new User("Noman"));
        users.add(new User("Ali"));
        users.add(new User("Mujahid"));
        users.add(new User("Daniyal"));
        users.add(new User("Qasim"));

        RecyclerView recyclerView = findViewById(R.id.user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllUsersCallRecyclerViewAdapter(users, this);
        recyclerView.setAdapter(adapter);
    }
}
