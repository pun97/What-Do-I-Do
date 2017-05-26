package com.example.pun.whatdoido;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.BoolRes;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Task> tasks = new ArrayList<>();
    TaskAdapter adapter;

    public JSONArray getJSONListFromTasks(){

        JSONArray JSONList = new JSONArray();
        int maxSize = tasks.size();
        for(int i=0;i<maxSize;i++){
            Task currentTask = tasks.get(i);
            try{
                JSONObject currObj = new JSONObject();
                currObj.put("ID",currentTask.getID());
                currObj.put("head",currentTask.getHeader());
                currObj.put("body",currentTask.getBody());
                currObj.put("isCompleted",currentTask.getIsCompleted());
                JSONList.put(currObj);
            }
            catch (Exception e){
                Log.d("PUNEET","JSON EXCEPTION1:"+e);
            }
        }

        return JSONList;
    }
    public List<Task> getTaskFromJSONList(JSONArray JSONList){
        List<Task> storedTasks = new ArrayList<>();
        int maxSize = JSONList.length();
        for(int i=0;i<maxSize;i++){

            try {
                JSONObject currObj = JSONList.getJSONObject(i);
                storedTasks.add(new Task((String) currObj.get("head"),(String )currObj.get("body"),currObj.get("isCompleted")=="true"));
            }
            catch (Exception e){
                Log.d("PUNEET","JSON EXCEPTION LOAD:"+e);
            }
        }

        return storedTasks;
    }
    @Override
    protected void onStop() {
        long time = System.currentTimeMillis();
        JSONObject storageObject = new JSONObject();
        try {
            storageObject.put("version", time);
            storageObject.put("data",getJSONListFromTasks());
        }
        catch (Exception e){
            Log.d("PUNEET","JSON EXCEPTIOn version:"+e);
        }
        File newfile = new File(getFilesDir(),"data.dat");
        try {
            FileOutputStream stream = new FileOutputStream(newfile);
            stream.write(storageObject.toString().getBytes());
            stream.close();
        }
        catch (Exception e){
            Log.d("PUNEET","WRITING FILE onSTOP"+e);
        }


        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        Toast.makeText(getApplicationContext(),"PAUSED",Toast.LENGTH_SHORT).show();
        long time = System.currentTimeMillis();
        JSONObject storageObject = new JSONObject();
        try {
            storageObject.put("version", time);
            storageObject.put("data",getJSONListFromTasks());
        }
        catch (Exception e){
            Log.d("PUNEET","JSON EXCEPTIOn version:"+e);
        }
        File newfile = new File(getFilesDir(),"data.dat");

        try {
            FileOutputStream stream = new FileOutputStream(newfile);
            stream.write(storageObject.toString().getBytes());
            stream.close();
        }
        catch (Exception e){
            Log.d("PUNEET","WRITING FILE on PAUSED"+e);
        }

        super.onPause();
    }

    @Override
    protected void onStart() {

        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton addTaskButton = (ImageButton) findViewById(R.id.b_add);
        final ImageButton markTaskButton = (ImageButton) findViewById(R.id.b_done);
        final ImageButton delTaskButton = (ImageButton) findViewById(R.id.b_del);
        final EditText enterTaskHead = (EditText) findViewById(R.id.et_enter_head);
        final EditText enterTaskBody = (EditText) findViewById(R.id.et_enter_body);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    //---------------------------------------------------------------------------------------------------------------------------
        JSONObject storageObject = null;
        String content=null;
        File newfile = new File(getFilesDir(),"data.dat");

        try{
            FileInputStream stream = new FileInputStream(newfile);
            int length = (int)newfile.length();
            byte[] bytes = new byte[length];
            stream.read(bytes);
            stream.close();
            content = new String(bytes);
        }
        catch (Exception e){
            Log.d("PUNEET","READING FILE"+e);
        }
        try {
            storageObject = new JSONObject(content);
        }
        catch (Exception e){
            Log.d("PUNEET","EXCEPTION JSON LIST1");
        }
        try {
            JSONArray JSONList = storageObject.getJSONArray("data");
            tasks = getTaskFromJSONList(JSONList);
        }
        catch (Exception e)
        {
            Log.d("PUNEET","EXCEPTION JSON LIST2");
        }
        Log.d("PUNEET",tasks.size()+" SIZE onCreate");
     //---------------------------------------------------------------------------------------------------------------------

        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Cavorting.otf");

        adapter = new TaskAdapter(tasks,tf);
        
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        /*
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
        */
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterTaskHead.setVisibility(View.VISIBLE);
                enterTaskBody.setVisibility(View.VISIBLE);
                markTaskButton.setVisibility(View.VISIBLE);
                addTaskButton.setVisibility(View.GONE);
                delTaskButton.setVisibility(View.GONE);

            }
        });
        markTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String h = enterTaskHead.getText().toString();
                String b = enterTaskBody.getText().toString();

                if(h.equals("")==false) {
                    Task newTask = new Task(h, b);
                    tasks.add(newTask);
                    adapter.notifyDataSetChanged();
                    enterTaskBody.setText("");
                    enterTaskHead.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Toast.makeText(getApplicationContext(),"Added new TODO!",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"YOU can't do NOTHING!",Toast.LENGTH_SHORT).show();
                enterTaskHead.setVisibility(View.GONE);
                enterTaskBody.setVisibility(View.GONE);
                markTaskButton.setVisibility(View.GONE);
                addTaskButton.setVisibility(View.VISIBLE);
                delTaskButton.setVisibility(View.VISIBLE);
            }
        });

        delTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(adapter.getLongPressStatus()==true){
                    adapter.setLongPressStatus(false);
                }

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
