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
                currObj.put("isCompleted","="+currentTask.getIsCompleted());
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
                storedTasks.add(new Task((String) currObj.get("head"),(String )currObj.get("body"),currObj.get("isCompleted").equals("=true")));
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
        final ImageButton delTaskButton = (ImageButton) findViewById(R.id.b_del);


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

        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/montserrat_light.ttf");

        ParcelableTask ptask = getIntent().getParcelableExtra("task");
        if(ptask!=null){

            Task newTask = ptask.getTask();
            if(newTask==null){
                Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_SHORT).show();
            }
            tasks.add(newTask);
        }

        adapter = new TaskAdapter(tasks,tf);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,CreateTask.class);
                startActivity(intent);

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
