package com.example.pun.whatdoido;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pun on 5/24/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private List<Task> mTaskList;
    private boolean isLongPressed=false;
    Typeface tf;


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            TextView body = (TextView) view.findViewById(R.id.tv_body);
            TextView id = (TextView) view.findViewById(R.id.ID);

            int currID = Integer.parseInt(id.getText().toString());
            Task current = null;// = mTaskList.get(Integer.parseInt(id.getText().toString()));
            int i=0;

            for ( i = 0; i < mTaskList.size(); i++) {
                current = mTaskList.get(i);
                if (current.getID() == currID)
                    break;
            }
            if (current == null) {
                Log.d("PUNEET", "ID NOT FOUND");
                return;
            }
            Log.d("PUNEET", current.getID() + " touched.");


            if(isLongPressed==false) {

                if (current.isOnFocus()==false) {
                    /*
                    for(int j=0;j<mTaskList.size();j++){
                        if(mTaskList.get(j)!=current&&mTaskList.get(j).isOnFocus()) {
                            mTaskList.get(j).setOnFocus(false);
                            notifyItemChanged(j);
                        }
                    }*/
                    current.setOnFocus(true);
                    notifyItemChanged(i);
                } else {
                    current.setOnFocus(false);
                    notifyItemChanged(i);
                }
            }
            else{
                current.setSelected(!current.getIsSelected());
                notifyItemChanged(i);
            }
            boolean flag=false;
            for(i=0;i<mTaskList.size();i++)
                flag = flag|mTaskList.get(i).getIsSelected();
            if(flag==false)
                isLongPressed=false;


        }
    };

    private View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {

            if(isLongPressed==false) {
                TextView id = (TextView) view.findViewById(R.id.ID);

                int currID = Integer.parseInt(id.getText().toString());
                Task current = null;
                int i=0;
                for(i=0;i<mTaskList.size();i++){
                    mTaskList.get(i).setOnFocus(false);
                }
                for ( i = 0; i < mTaskList.size(); i++) {
                    current = mTaskList.get(i);
                    if (current.getID() == currID)
                        break;
                }
                if (current == null) {
                    Log.d("PUNEET", "ID NOT FOUND");
                    return false;
                }
                current.setSelected(true);
                current.setOnFocus(false);
                notifyItemChanged(i);

                isLongPressed = true;

            }
            Log.d("PUNEET","LONG PRESSED"+isLongPressed);
            return true;
        }
    };

    private View.OnClickListener statusChange = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isLongPressed == false) {
                View Pview = (View) view.getParent();
                TextView id = (TextView) Pview.findViewById(R.id.ID);

                int currID = Integer.parseInt(id.getText().toString());

                Task current = null;
                int i = 0;
                for (i = 0; i < mTaskList.size(); i++) {
                    current = mTaskList.get(i);
                    if (current.getID() == currID)
                        break;
                }
                if (current == null)
                    Log.d("PUNEET", "ID NOT FOUND");
                if (current.isOnFocus() == false) {
                    current.setCompleted(!current.getIsCompleted());
                    Log.d("PUNEET", "STATUS" + current.getIsCompleted());
                    notifyItemChanged(i);
                }

            }
        }
    };

    public TaskAdapter(List<Task> taskList, Typeface tf) {
        mTaskList = taskList; this.tf = tf;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        view.setOnClickListener(mOnClickListener);
        view.setOnLongClickListener(mLongClickListener);
        ImageButton status = (ImageButton)view.findViewById(R.id.status);
        status.setOnClickListener(statusChange);
        return new TaskHolder(view,tf);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.bindTask(task);
    }


    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public ArrayList<Integer> getSelectedItemsID() {
        ArrayList<Integer> selectedItemsID = new ArrayList<>();
        int size = mTaskList.size();
        for (int i = 0; i < size; i++) {
            Task currTask = mTaskList.get(i);
            if (currTask.getIsSelected() == true) {
                selectedItemsID.add(currTask.getID());
            }
        }

        return selectedItemsID;
    }
    public boolean getLongPressStatus(){
        return isLongPressed;
    }

    public void setLongPressStatus(boolean b) {
        isLongPressed = b;
    }
}
