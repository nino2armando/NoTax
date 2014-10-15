package com.example.ninokhodabandeh.notax.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ninokhodabandeh.notax.MainActivity;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class UserInputModel implements Parcelable {

    private int mDistance;
    private String mBusinessType;

    public UserInputModel(int distance, String businessType){
        mDistance = distance;
        mBusinessType = businessType;
    }

    public UserInputModel(Parcel in){
        mDistance = in.readInt();
        mBusinessType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mDistance);
        dest.writeString(mBusinessType);
    }

    public static final Creator<UserInputModel> CREATOR = new Creator<UserInputModel>() {
        @Override
        public UserInputModel createFromParcel(Parcel source) {
            return new UserInputModel(source);
        }

        @Override
        public UserInputModel[] newArray(int size) {
            return new UserInputModel[size];
        }
    };
}
