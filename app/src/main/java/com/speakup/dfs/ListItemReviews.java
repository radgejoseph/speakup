package com.speakup.dfs;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItemReviews implements Parcelable {

    private String username;
    private String vehicle;
    private String plate;
    private int rateCount;
    private String review;

    public ListItemReviews(String username,String vehicle, String plate, int rateCount, String review) {
        this.username = username;
        this.vehicle = vehicle;
        this.plate = plate;
        this.rateCount = rateCount;
        this.review = review;
    }

    protected ListItemReviews(Parcel in) {
        username = in.readString();
        vehicle = in.readString();
        plate = in.readString();
        rateCount = in.readInt();
        review = in.readString();
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

    public String getUsernameL() {
        return username;
    }

    public String getVehicle() {
        return vehicle; }

    public String getPlateL() {
        return plate;
    }

    public int getRatCountL() {
        return rateCount;
    }

    public String getReviewL() {
        return review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(vehicle);
        dest.writeString(plate);
        dest.writeInt(rateCount);
        dest.writeString(review);
    }

}
