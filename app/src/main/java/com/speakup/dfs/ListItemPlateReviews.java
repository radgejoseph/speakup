package com.speakup.dfs;

import java.util.Comparator;

public class ListItemPlateReviews {

    private int ratings;
    private String narrative;
    private String username;
    private String created_at;

public ListItemPlateReviews(String username, int ratings, String narrative, String created_at) {

        this.ratings = ratings;
        this.narrative = narrative;
        this.username = username;
        this.created_at = created_at;
    }

    public int getRatings() {
        return ratings;
    }

    public String getNarrative() {
        return narrative;
    }

    public String getUsername() {
        return username;
    }

    public String getTimestampL() {
        return created_at;
    }

//    public static Comparator<ListItemPlateReviews> listItemComparatorDT = new Comparator<ListItemPlateReviews>() {
//        @Override
//        public int compare(ListItemPlateReviews o1, ListItemPlateReviews o2) {
//            return o1.getTimestampL().compareTo(o2.getTimestampL());
//        }
//    };
}
