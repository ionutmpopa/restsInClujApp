package com.boioio.restsincluj.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Review extends AbstractModel {

    private long restaurant_id;

    private String title;

    private String review;

    @DateTimeFormat(pattern = "dd/mm/year")
    private Date dateOfVisit;

    @DateTimeFormat(pattern = "dd/mm/year")
    private Date dateOfReview;

    private Rating rating;

//    @Override
    public void setRestaurant_id(long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

//    @Override
    public long getRestaurant_id() {
        return restaurant_id;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(Date dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public Date getDateOfReview() {
        return dateOfReview;
    }

    public void setDateOfReview(Date dateOfReview) {
        this.dateOfReview = dateOfReview;
    }
}
