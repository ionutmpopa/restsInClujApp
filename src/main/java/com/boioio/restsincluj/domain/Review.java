package com.boioio.restsincluj.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

public class Review extends AbstractModel {

    private long restaurant_id;

    private String review;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfVisit;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfReview;

    private String rating;


    public void setRestaurant_id(long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public long getRestaurant_id() {
        return restaurant_id;
    }

//    public Rating getRating() {
//        return rating;
//    }
//
//    public void setRating(Rating rating) {
//        this.rating = rating;
//    }

    public String getRating() {
//        return rating != null ? rating.toString() : "POOR";
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    @Override
    public String toString() {
        return "Review{" +
                "restaurant_id=" + restaurant_id +
                ", review='" + review + '\'' +
                ", dateOfVisit=" + dateOfVisit +
                ", dateOfReview=" + dateOfReview +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return restaurant_id == review1.restaurant_id &&
                Objects.equals(review, review1.review) &&
                Objects.equals(dateOfVisit, review1.dateOfVisit) &&
                Objects.equals(dateOfReview, review1.dateOfReview) &&
                rating == review1.rating;
    }

    @Override
    public int hashCode() {

        return Objects.hash(restaurant_id, review, dateOfVisit, dateOfReview, rating);
    }
}
