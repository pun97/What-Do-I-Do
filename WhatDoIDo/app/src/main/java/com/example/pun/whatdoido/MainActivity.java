package com.example.pun.whatdoido;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Task> tasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton addTaskButton = (ImageButton) findViewById(R.id.b_add);
        ImageButton markTaskButton = (ImageButton) findViewById(R.id.b_done);
        ImageButton delTaskButton = (ImageButton) findViewById(R.id.b_del);
        final EditText enterTaskHead = (EditText) findViewById(R.id.et_enter_head);
        final EditText enterTaskBody = (EditText) findViewById(R.id.et_enter_body);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);




        final TaskAdapter adapter = new TaskAdapter(tasks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        tasks.add(new Task("Android Development 0", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 1", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 2", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 3", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 4", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 5", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 6", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 7", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 8", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development 9", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development10", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development11", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development12", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development13", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development14", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));
        tasks.add(new Task("Android Development15", "This is the description of the huge document man, it totally rocks. I just love the way it looks. Hopefully people will like what I'm creating. This is what i wish."));


        adapter.notifyDataSetChanged();

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterTaskHead.setVisibility(View.VISIBLE);
                enterTaskBody.setVisibility(View.VISIBLE);

            }
        });
        markTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String h = enterTaskHead.getText().toString();
                String b = enterTaskBody.getText().toString();
                if (enterTaskBody.getVisibility()!=View.GONE) {
                    Task newTask = new Task(h, b);
                    tasks.add(newTask);
                    adapter.notifyDataSetChanged();
                    enterTaskBody.setText("");
                    enterTaskHead.setText("");
                    enterTaskHead.setVisibility(View.GONE);
                    enterTaskBody.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                else{
                    Toast.makeText(getApplicationContext(),Integer.toString(adapter.getItemCount()),Toast.LENGTH_LONG).show();
                }
            }
        });

        delTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ArrayList<Integer> selectedItemsID = new ArrayList<Integer>(adapter.getSelectedItemsID());
                int s_size = selectedItemsID.size();
                int t_size = tasks.size();
                for(int i=0;i<s_size;i++){
                    Integer toDeleteID = selectedItemsID.get(i);

                    for(int j=0;j<t_size;j++){
                        Task currTask = tasks.get(j);
                        if(toDeleteID.equals(currTask.getID())){
                            tasks.remove(j);
                            adapter.notifyItemRemoved(j);
                            adapter.notifyItemRangeChanged(j,tasks.size());
                            break;
                        }
                    }


                }
                for(int i=0;i<tasks.size();i++){
                    Task ctask = tasks.get(i);
                    if(ctask.getIsSelected()==true) {
                        ctask.setSelected(false);
                        adapter.notifyItemChanged(i);
                    }
                }
                Log.d("PUNEET", Integer.toString(adapter.getItemCount())+" Adapter Count");
                Log.d("PUNEET", Integer.toString(tasks.size())+" List Count");

            }
        });
    }
}
