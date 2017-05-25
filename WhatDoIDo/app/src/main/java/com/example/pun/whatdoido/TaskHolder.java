package com.example.pun.whatdoido;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Pun on 5/24/2017.
 */

public class TaskHolder extends RecyclerView.ViewHolder{
    private TextView header;
    private TextView body;
    private TextView ID;
    private LinearLayout selector;
    private LinearLayout wrapper;

    public TaskHolder(View itemView) {
        super(itemView);
       header = (TextView) itemView.findViewById(R.id.tv_head);
       body = (TextView) itemView.findViewById(R.id.tv_body);
        ID = (TextView)itemView.findViewById(R.id.ID);
        selector = (LinearLayout)itemView.findViewById(R.id.selector);
        wrapper = (LinearLayout)itemView.findViewById(R.id.wrapper);
    }
    public void bindTask(Task mTask){
        header.setText(mTask.getHeader());
        body.setText(mTask.getBody());
        ID.setText(Integer.toString(mTask.getID()));
        if(mTask.getIsSelected()){
            body.setVisibility(View.VISIBLE);
            selector.setVisibility(View.VISIBLE);
            wrapper.setBackgroundResource(R.color.selected);
        }
        else {
            wrapper.setBackgroundResource(R.color.unSelected);
            body.setVisibility(View.GONE);
            selector.setVisibility(View.INVISIBLE);
        }
    }

}
