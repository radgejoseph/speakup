package com.speakup.dfs;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {

    private String vehicle;
    private String plate;

    public ListItem(String vehicle, String plate) {
        this.vehicle = vehicle;
        this.plate = plate;
    }

    protected ListItem(Parcel in) {
        vehicle = in.readString();
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

    public String getVehicleL() {
        return vehicle;
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
        dest.writeString(vehicle);
        dest.writeString(plate);
    }

}
