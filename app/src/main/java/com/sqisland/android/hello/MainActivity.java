package com.sqisland.android.hello;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Task> tasks;
    private AllUsersCallRecyclerViewAdapter adapter;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();

        tasks.add(new Task("Run"));
        tasks.add(new Task("Walk"));
        tasks.add(new Task("Code"));
        tasks.add(new Task("Eat"));
        tasks.add(new Task("Sleep"));

        RecyclerView recyclerView = findViewById(R.id.user_list);
        editText = findViewById(R.id.editText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllUsersCallRecyclerViewAdapter(tasks, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton myFab = findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addItem();
            }
        });
    }

    public void addItem()
    {
        String text = editText.getText().toString();
        if(TextUtils.isEmpty(text))
        {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.main_layout_id), "Please enter task name", Snackbar.LENGTH_LONG);


            snackbar.show();
            return;
        }
        tasks.add(0, new Task(text));
        adapter.notifyDataSetChanged();
        editText.setText("");
    }
}
