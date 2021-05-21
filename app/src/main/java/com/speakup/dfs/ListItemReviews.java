package com.speakup.dfs;

public class ListItemReviews {

    private String vehicle;
    private String body_plate;
    private int ratings;
    private String narrative;

    public ListItemReviews(String vehicle, String body_plate, int ratings, String narrative) {
        this.vehicle = vehicle;
        this.body_plate = body_plate;
        this.ratings = ratings;
        this.narrative = narrative;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getBody_plate() {
        return body_plate;
    }

    public int getRatings() {
        return ratings;
    }

    public String getNarrative() {
        return narrative;
    }

    //    public ListItemReviews(String vehicle, String body_plate, int ratings, String narrative) {
//        this.vehicle = vehicle;
//        this.body_plate = body_plate;
//        this.ratings = ratings;
//        this.narrative = narrative;
//    }
//
//    protected ListItemReviews(Parcel in) {
//        vehicle = in.readString();
//        body_plate = in.readString();
//        ratings = in.readInt();
//        narrative = in.readString();
//    }
//
//    public static final Creator<ListItemReviews> CREATOR = new Creator<ListItemReviews>() {
//        @Override
//        public ListItemReviews createFromParcel(Parcel in) {
//            return new ListItemReviews(in);
//        }
//
//        @Override
//        public ListItemReviews[] newArray(int size) {
//            return new ListItemReviews[size];
//        }
//    };
//
////    public ListItemReviews(List<ListItemReviews> listItemReviews, RatingsFragment ratingsFragment) {
////
////    }
//
//    public String getVehicleL() {
//        return vehicle;
//    }
//
//    public String getPlateL() {
//        return body_plate;
//    }
//
//    public int getRatcountL() {
//        return ratings;
//    }
//
//    public String getReviewL() {
//        return narrative;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(vehicle);
//        dest.writeString(body_plate);
//        dest.writeInt(ratings);
//        dest.writeString(narrative);
//    }

}
