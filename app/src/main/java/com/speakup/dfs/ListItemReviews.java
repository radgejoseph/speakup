package com.speakup.dfs;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItemReviews implements Parcelable {

    private String username;
    private String plate;
    private int ratecount;
    private String review;

    public ListItemReviews(String username, String plate, int ratecount, String review) {
        this.username = username;
        this.plate = plate;
        this.ratecount = ratecount;
        this.review = review;
    }

    protected ListItemReviews(Parcel in) {
        username = in.readString();
        plate = in.readString();
        ratecount = in.readInt();
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

    public String getPlateL() {
        return plate;
    }

    public int getRatcountL() {
        return ratecount;
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
        dest.writeString(plate);
        dest.writeInt(ratecount);
        dest.writeString(review);
    }

}
