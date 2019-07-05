package com.sqisland.android.todo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {


    private ArrayList<Task> tasks;
    private TasksRecyclerViewAdapter adapter;
    private EditText editText;
    private DatabaseReference mDatabase;
    RecyclerViewClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();

        mListener = this;

        RecyclerView recyclerView = findViewById(R.id.user_list);
        editText = findViewById(R.id.editText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksRecyclerViewAdapter(tasks, mListener, this);
        recyclerView.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FloatingActionButton myFab = findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addItem();
                hideSoftKeyboard(MainActivity.this, v);
            }
        });

        fetchTasks();
    }

    void fetchTasks()
    {

        mDatabase.child("tasks").orderByChild("timestamp").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Task task = dataSnapshot.getValue(Task.class);
                task.setTaskId(dataSnapshot.getKey());
                tasks.add(0, task);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setTaskId(dataSnapshot.getKey());
                int index = tasks.indexOf(task);

                if(index >= 0)
                {
                    tasks.set(index, task);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setTaskId(dataSnapshot.getKey());
                int index = tasks.indexOf(task);

                if(index >= 0)
                {
                    tasks.remove(index);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        Long timestamp = System.currentTimeMillis() / 1000;
        Task task = new Task(null, text, timestamp);
        editText.setText("");

        DatabaseReference taskRef = mDatabase.child("tasks").push();
        taskRef.setValue(task);
        Log.d("addItem", "added");
    }


    @Override
    public void onClick(View view, int position) {
        Task task = tasks.get(position);
        DatabaseReference taskRef = mDatabase.child("tasks").child(task.getTaskId());
        taskRef.setValue(null);
    }

    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
