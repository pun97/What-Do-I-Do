package com.example.pun.whatdoido;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Pun on 5/24/2017.
 */

public class TaskHolder extends RecyclerView.ViewHolder {
    private TextView header;
    private TextView body;
    private TextView ID;
    private LinearLayout selector;
    private LinearLayout wrapper;
    private ImageButton status;

    public TaskHolder(View itemView, Typeface tf) {
        super(itemView);
        header = (TextView) itemView.findViewById(R.id.tv_head);
        body = (TextView) itemView.findViewById(R.id.tv_body);
        ID = (TextView) itemView.findViewById(R.id.ID);
        selector = (LinearLayout) itemView.findViewById(R.id.selector);
        wrapper = (LinearLayout) itemView.findViewById(R.id.wrapper);
        status = (ImageButton) itemView.findViewById(R.id.status);
        body.setTypeface(tf);

    }

    public void bindTask(Task mTask) {

        header.setText(mTask.getHeader());
        ID.setText(Integer.toString(mTask.getID()));
        status.setVisibility(View.VISIBLE);
        if (mTask.getIsCompleted() == false && mTask.getIsSelected() == false && mTask.isOnFocus() == false) {
            status.setImageResource(R.drawable.complete);
            header.setBackgroundResource(R.color.incomplete);
            wrapper.setBackgroundResource(R.color.incomplete);
            header.setPaintFlags(1281);

        } else if (mTask.getIsSelected() == false && mTask.isOnFocus() == false) {
            status.setImageResource(R.drawable.incomplete);
            header.setBackgroundResource(R.color.complete);
            wrapper.setBackgroundResource(R.color.complete);
            Log.d("PUNEET","PAINT FLAG:"+ (header.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG ));
            header.setPaintFlags(1297);
        }

        if (mTask.isOnFocus() == true) {
            header.setPaintFlags(1281);
            status.setVisibility(View.GONE);
            body.setVisibility(View.VISIBLE);
            body.setText(mTask.getBody());
            selector.setVisibility(View.INVISIBLE);
            wrapper.setBackgroundColor(0xFFFFFF);
            body.setBackgroundResource(R.drawable.complete);
            if (mTask.getIsCompleted() == true) {
                header.setBackgroundResource(R.color.complete);
                body.setBackgroundResource(R.drawable.complete_rec);

            } else {
                header.setBackgroundResource(R.color.incomplete);
                body.setBackgroundResource(R.drawable.incomplete_rec);
            }
        } else if (mTask.isOnFocus() == false && mTask.getIsSelected() == false) {
            body.setVisibility(View.GONE);
            selector.setVisibility(View.INVISIBLE);
        } else if (mTask.getIsSelected()) {
            header.setPaintFlags(1281);
            body.setVisibility(View.GONE);
            Log.d("PUNEET", "SELECTED" + mTask.getID());
            selector.setVisibility(View.VISIBLE);
            header.setBackgroundResource(R.color.selected);
            wrapper.setBackgroundResource(R.color.selected);
            status.setImageResource(R.drawable.selected);
        } else {
            body.setVisibility(View.GONE);
            selector.setVisibility(View.INVISIBLE);
        }
    }

}
