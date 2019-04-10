package com.boioio.restsincluj.service;

import com.boioio.restsincluj.domain.Review;
import com.boioio.restsincluj.exception.ValidationException;
import com.boioio.restsincluj.service.ReviewService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

@TestPropertySource(locations="classpath:test.properties")
public abstract class BaseReviewServiceTest {

	protected abstract ReviewService getReviewService();



	@After
	public void tearDown() {
		Collection<Review> reviews = new LinkedList<Review>(getReviewService().listAll());

		for (Review review : reviews) {
			getReviewService().delete(review.getId());
		}
	}

//	@Test
//	public void test_empty_get_all() {
//		Collection<Review> reviews = getReviewService().listAll();
//		Assert.assertTrue(reviews.isEmpty());
//	}

	@Test(expected = ValidationException.class)
	public void test_add_no_restaurant_id() throws ValidationException {
		Review review = new Review();
		review.setReview("Really nice place");
		review.setDateOfVisit(new Date());
		review.setDateOfReview(new Date());
		review.setRating("GOOD");
		getReviewService().save(review);
	}

	@Test(expected = ValidationException.class)
	public void test_add_no_review() throws ValidationException {
		Review review = new Review();
		review.setRestaurant_id(1);
		review.setDateOfVisit(new Date());
		review.setDateOfReview(new Date());
		review.setRating("GOOD");
		getReviewService().save(review);

	}



	@Test(expected = ValidationException.class)
	public void test_add_no_date_of_visit() throws ValidationException {
		Review review = new Review();
		review.setRestaurant_id(1);
		review.setReview("Really nice place");
		review.setDateOfVisit(null) ;
		review.setDateOfReview(new Date());
		review.setRating("GOOD");
		getReviewService().save(review);

	}

	@Test(expected = ValidationException.class)
	public void test_add_no_date_of_review() throws ValidationException {
		Review review = new Review();
		review.setRestaurant_id(1);
		review.setReview("Really nice place");
		review.setDateOfVisit(new Date());
		review.setRating("GOOD");
		getReviewService().save(review);

	}

	@Test(expected = ValidationException.class)
	public void test_add_in_future_review() throws ValidationException {
		Review review = new Review();
		Calendar c = GregorianCalendar.getInstance();
		c.set(2019,4,11);
		review.setRestaurant_id(1);
		review.setReview("Really nice place");
		review.setDateOfVisit(new Date());
		review.setDateOfReview(c.getTime());
		review.setRating("GOOD");
		getReviewService().save(review);

	}

	@Test(expected = ValidationException.class)
	public void test_add_date_of_review_before_date_of_visit() throws ValidationException {
		Review review = new Review();
		Calendar c = GregorianCalendar.getInstance();
		review.setRestaurant_id(1);
		review.setReview("Really nice place");
		c.set(2019,3,2);
		review.setDateOfVisit(c.getTime());
		c.set(2019,3,1);
		review.setDateOfReview(c.getTime());
		review.setRating("GOOD");
		getReviewService().save(review);

	}

	@Test(expected = ValidationException.class)
	public void test_add_no_rating() throws ValidationException {
		Review review = new Review();
		review.setRestaurant_id(1);
		review.setReview("Really nice place");
		review.setDateOfVisit(new Date());
		review.setDateOfReview(new Date());
		getReviewService().save(review);
	}

	@Test(expected = ValidationException.class)
	public void test_add_empty() throws ValidationException {
		Review review = new Review();

		getReviewService().save(review);

	}

	@Test
	public void test_add_valid_review() throws ValidationException {
		Review review = new Review();
		review.setRestaurant_id(1);
		review.setReview("Really nice place");
		review.setDateOfVisit(new Date());
		review.setDateOfReview(new Date());
		review.setRating("GOOD");
		getReviewService().save(review);
		Assert.assertEquals(1, getReviewService().listAll().size());
		Review fromDB = getReviewService().listAll().iterator().next();
		Assert.assertNotNull(fromDB);
		Assert.assertTrue(fromDB.getId() > 0);
		review.setId(fromDB.getId());
		Assert.assertEquals(review, fromDB);
	}

	@Test
	public void test_delete_nonexistent() throws ValidationException {

		Assert.assertFalse(getReviewService().delete(-12l));

	}
}