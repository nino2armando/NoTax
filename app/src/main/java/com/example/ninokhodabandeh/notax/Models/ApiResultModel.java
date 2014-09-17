package com.example.ninokhodabandeh.notax.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class ApiResultModel implements Parcelable {
    public int _id;
    public String _content;
    public String _distance;

    public ApiResultModel(int id, String content, String distance){
        super();
        _id = id;
        _content = content;
        _distance = distance;
    }

    public ApiResultModel(Parcel in){
        _id = in.readInt();
        _content = in.readString();
        _distance = in.readString();
    }

    public int getId(){return this._id;}

    public String getContent(){return this._content;}

    public String getDistance(){
        return String.format("%1$s km", this._distance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(_content);
        dest.writeString(_distance);
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
}
