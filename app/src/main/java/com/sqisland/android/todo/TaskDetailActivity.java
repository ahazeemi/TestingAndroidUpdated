package com.sqisland.android.todo;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String taskName = getIntent().getStringExtra("taskName");
        long now = System.currentTimeMillis();
        String timeAgo = DateUtils.getRelativeTimeSpanString(getIntent().getLongExtra("taskTimestamp", 0)*1000, now,
                0L, DateUtils.FORMAT_ABBREV_ALL).toString();

        setTitle(taskName);
        ((TextView)findViewById(R.id.timestamp)).setText("Task created ".concat(timeAgo));
    }
}
