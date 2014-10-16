package com.example.ninokhodabandeh.notax.Models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class ApiResultModel implements Parcelable {
    private int mId;
    private String mTitle;
    private String mAddress;
    private String mContent;
    private String mDistance;
    private String mPhone;
    private Location mLocation;


    public ApiResultModel(int id, String content, String distance){
        super();
        this.mId = id;
        this.mContent = content;
        this.mDistance = distance;
    }

    public ApiResultModel(Parcel in){
        mId = in.readInt();
        mContent = in.readString();
        mDistance = in.readString();
    }

    public int getId(){return mId;}

    public String getContent(){return mContent;}

    public String getDistance(){
        return String.format("%1$s km", mDistance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mContent);
        dest.writeString(mDistance);
    }

    public static final Creator<ApiResultModel> CREATOR = new Creator<ApiResultModel>() {
        @Override
        public ApiResultModel createFromParcel(Parcel source) {
            return new ApiResultModel(source);
        }

        @Override
        public ApiResultModel[] newArray(int size) {
            return new ApiResultModel[size];
        }
    };

    public class Location{
        private String mLat;
        private String mLng;
    }
}
