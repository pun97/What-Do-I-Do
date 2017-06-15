package com.example.pun.whatdoido;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pun on 6/15/2017.
 */

public class ParcelableTask implements Parcelable {
    Task mtask;
    public Task getTask(){
        return mtask;
    }
    public  ParcelableTask(Task task){
        mtask = task;
    }
    protected ParcelableTask(Parcel in) {

        String head = in.readString();
        String body = in.readString();
        mtask = new Task(head,body);
    }

    public static final Creator<ParcelableTask> CREATOR = new Creator<ParcelableTask>() {
        @Override
        public ParcelableTask createFromParcel(Parcel in) {
            return new ParcelableTask(in);
        }

        @Override
        public ParcelableTask[] newArray(int size) {
            return new ParcelableTask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mtask.getHeader());
        parcel.writeString(mtask.getBody());
    }
}
