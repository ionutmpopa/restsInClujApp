package com.boioio.restsincluj.dao;

import com.boioio.restsincluj.domain.Review;

import java.util.Collection;

public interface ReviewDAO extends BaseDAO<Review>{

    Collection<Review> searchByTitle(String query);

    Collection<Review> searchByRating(String query);

}
