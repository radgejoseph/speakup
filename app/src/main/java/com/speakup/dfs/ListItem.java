package com.speakup.dfs;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {

    private int id;
    private String plate;

    public ListItem(int id, String plate) {
        this.id = id;
        this.plate = plate;
    }

    protected ListItem(Parcel in) {
        id = in.readInt();
        plate = in.readString();
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    public int getIdL() {
        return id;
    }

    public String getPlateL() {
        return plate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(plate);
    }

}
