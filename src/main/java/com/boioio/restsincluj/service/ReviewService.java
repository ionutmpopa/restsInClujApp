package com.boioio.restsincluj.service;

import com.boioio.restsincluj.dao.ReviewDAO;
import com.boioio.restsincluj.domain.Review;
import com.boioio.restsincluj.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Service
public class ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewDAO reviewDAO;

    public Collection<Review> listAll() {
        return reviewDAO.getAll();
    }

    public Collection<Review> search( String query) {
        LOGGER.debug("Searching for " + query);
        return reviewDAO.searchByTitle(query);
    }

    public boolean delete(Long id) {
        LOGGER.debug("Deleting review for id: " + id);
        Review review = null;
        try {
            review = reviewDAO.findById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("trying to find a non-existent review");
            return false;
        }
        if (review != null) {
            reviewDAO.delete(review);
            return true;
        }

        return false;
    }

    public Review get(Long id) {
        LOGGER.debug("Getting review for id: " + id);
        return reviewDAO.findById(id);
    }

    public void save(Review review) throws ValidationException {
        LOGGER.debug("Saving: " + review);
        validate(review);

        reviewDAO.update(review);
    }

    private void validate(Review review) throws ValidationException {
        Date currentDate = new Date();
        List<String> errors = new LinkedList<String>();
        if (StringUtils.isEmpty(review.getTitle())) {
            errors.add("Title is Empty");
        }

        if (review.getDateOfReview() == null) {
            errors.add("Date of review is Empty");
        }else {
            if(currentDate.before((review.getDateOfReview()))){
                errors.add("Date of review in future");
            }
        }

        if(review.getDateOfVisit() == null){
            errors.add("Date of visit is Empty") ;
        }


        if (StringUtils.isEmpty(review.getReview())) {
            errors.add("Review is Empty");
        }else {
            if (currentDate.before((review.getDateOfVisit()))) {
                errors.add("Date of visit in future");
            }
        }

    }

    public ReviewDAO getReviewDAO() {
        return reviewDAO;
    }

    public void setReviewDAO(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }





}
