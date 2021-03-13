package com.speakup.dfs;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {

    private String vehicle;
    private String plate;
    //private String ratings;

    public ListItem(String vehicle, String plate /*, String ratings*/) {
        this.vehicle = vehicle;
        this.plate = plate;
        //this.ratings = ratings;
    }

    protected ListItem(Parcel in) {
        vehicle = in.readString();
        plate = in.readString();
        //ratings = in.readString();
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

//    public String getRatingsL() {
//        return ratings;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vehicle);
        dest.writeString(plate);
        //dest.writeString(ratings);
    }

}
