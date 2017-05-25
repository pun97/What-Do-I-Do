package com.example.pun.whatdoido;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Pun on 5/24/2017.
 */

public class Task {
    private String header;
    private String body;
    private boolean isCompleted;
    private boolean isSelected;

    public boolean isOnFocus() {
        return onFocus;
    }

    public void setOnFocus(boolean onFocus) {
        this.onFocus = onFocus;
    }

    private boolean onFocus=false;
    private int ID;
    static int COUNT=0;


    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Task(String header, String body){
        this.header = header;
        this.body = body;
        this.isCompleted = false;
        this.isSelected = false;
        this.ID = COUNT;
        COUNT++;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        COUNT--;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public boolean getIsSelected(){ return isSelected; }

    public int getID(){ return ID; }

    public void setSelected(boolean selected){this.isSelected = selected;}
}
