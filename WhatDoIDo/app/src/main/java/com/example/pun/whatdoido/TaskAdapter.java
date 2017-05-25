package com.example.pun.whatdoido;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pun on 5/24/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private List<Task> mTaskList;
    private boolean isLongPress=false;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            LinearLayout selector = (LinearLayout) view.findViewById(R.id.selector);
            TextView body = (TextView) view.findViewById(R.id.tv_body);
            TextView id = (TextView) view.findViewById(R.id.ID);
            LinearLayout wrapper = (LinearLayout) view.findViewById(R.id.wrapper);
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
            if (current.getIsSelected() == false) {
                selector.setVisibility(View.VISIBLE);
                body.setVisibility(View.VISIBLE);
                current.setSelected(true);
                notifyItemChanged(i);
                wrapper.setBackgroundResource(R.color.selected);
            } else {

                selector.setVisibility(View.INVISIBLE);
                body.setVisibility(View.GONE);
                current.setSelected(false);
                notifyItemChanged(i);
                wrapper.setBackgroundResource(R.color.unSelected);
            }
        }
    };

    public TaskAdapter(List<Task> taskList) {
        mTaskList = taskList;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new TaskHolder(view);
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
}
