package com.boioio.restsincluj.domain;

import java.util.HashMap;
import java.util.Map;

public class ListReview {

    private Map<String, String> myReviews;

    public ListReview(){
        this.myReviews = new HashMap<>();
    }


    public Map<String, String> getMyReviews() {
        return myReviews;
    }

    public void setMyReviews(Map<String, String> myReviews) {
        this.myReviews = myReviews;
    }
}
