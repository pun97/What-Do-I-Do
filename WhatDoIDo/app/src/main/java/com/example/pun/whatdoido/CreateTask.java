package com.example.pun.whatdoido;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;

public class CreateTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ImageButton done = (ImageButton) findViewById(R.id.b_done);
        ImageButton back = (ImageButton) findViewById(R.id.b_del);

        final EditText body = (EditText) findViewById(R.id.et_enter_body);
        final EditText head = (EditText) findViewById(R.id.et_enter_head);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task newTask = new Task(head.getText().toString(),body.getText().toString());
                Intent intent = new Intent(CreateTask.this,MainActivity.class);
                intent.putExtra("task",new ParcelableTask(newTask));
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTask.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    }

