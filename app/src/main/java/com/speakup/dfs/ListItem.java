package com.speakup.dfs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class ListItem implements Parcelable {

    private String vehicle;
    private String plate;
    private int ratings;

    public ListItem(String vehicle, String plate , int ratings) {
        this.vehicle = vehicle;
        this.plate = plate;
        this.ratings = ratings;
    }

    protected ListItem(Parcel in) {
        vehicle = in.readString();
        plate = in.readString();
        ratings = in.readInt();
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

    public int getRatingsL() {
        return ratings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vehicle);
        dest.writeString(plate);
        dest.writeInt(ratings);
    }

    public static Comparator<ListItem> listItemComparatorHtoL = new Comparator<ListItem>() {
        @Override
        public int compare(ListItem o1, ListItem o2) {
            return o1.getRatingsL() - (o2.getRatingsL());
        }
    };

    public static Comparator<ListItem> listItemComparatorLtoH = new Comparator<ListItem>() {
        @Override
        public int compare(ListItem o1, ListItem o2) {
            return o2.getRatingsL() - (o1.getRatingsL());
        }
    };

}
