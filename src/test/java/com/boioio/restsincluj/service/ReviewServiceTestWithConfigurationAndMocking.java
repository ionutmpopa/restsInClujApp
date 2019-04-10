package com.boioio.restsincluj.service;

import com.boioio.restsincluj.ApplicationConfiguration;
import com.boioio.restsincluj.dao.ReviewDAO;
import com.boioio.restsincluj.domain.Review;
import com.boioio.restsincluj.exception.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class})
public class ReviewServiceTestWithConfigurationAndMocking extends BaseReviewServiceTest {

	@InjectMocks
	private ReviewService service;

	@Mock
	private ReviewDAO reviewDAO;

	@Override
	protected ReviewService getReviewService() {
		return service;
	}

	@Before
	public void setup() {

	}


	@Test
	public void test_add_valid_review() throws ValidationException {

		Review review = new Review();
		review.setRestaurant_id(1);
		review.setReview("Really nice place");
		review.setDateOfVisit(new Date());
		review.setDateOfReview(new Date());
		review.setRating("GOOD");
		review.setId(1);

		when(getReviewService().listAll()).thenReturn(Arrays.asList(review));

		getReviewService().save(review);

		Assert.assertEquals(1, getReviewService().listAll().size());
		Review fromDB = getReviewService().listAll().iterator().next();
		Assert.assertNotNull(fromDB);
		Assert.assertTrue(fromDB.getId() > 0);
		review.setId(fromDB.getId());
		Assert.assertEquals(review, fromDB);
	}

	@Test
	public void test_search_by_rating_no_result() throws ValidationException {
		Review review = new Review();
		review.setRestaurant_id(1);
		review.setReview("Really nice place");
		review.setDateOfVisit(new Date());
		review.setDateOfReview(new Date());
		review.setRating("GOOD");
		getReviewService().save(review);
		Assert.assertEquals(0, getReviewService().search("EXCELENT").size());

	}

	@Test
    public void test_empty_get_all() throws ValidationException{
        Collection<Review> reviews = getReviewService().listAll();
        Assert.assertTrue(reviews.isEmpty());
    }

//	@Test
//	public void test_search_by_rating_multiple_results() throws ValidationException {
//		Review review = new Review();
//		review.setRestaurant_id(1);
//		review.setReview("Really nice place");
//		review.setDateOfVisit(new Date());
//		review.setDateOfReview(new Date());
//		review.setRating("GOOD");
//		getReviewService().save(review);
//
//		review = new Review();
//		review.setRestaurant_id(1);
//		review.setReview("Really nice place");
//		review.setDateOfVisit(new Date());
//		review.setDateOfReview(new Date());
//		review.setRating("GOOD");
//		getReviewService().save(review);
//
//		Assert.assertEquals(2, getReviewService().search("GOOD").size());
//
//	}

}
