package com.boioio.restsincluj.service;

import com.boioio.restsincluj.ApplicationConfiguration;
import com.boioio.restsincluj.dao.RestaurantDAO;
import com.boioio.restsincluj.domain.RestType;
import com.boioio.restsincluj.domain.Restaurant;
import com.boioio.restsincluj.exception.ValidationException;
import com.boioio.restsincluj.service.BaseRestaurantServiceTest;
import com.boioio.restsincluj.service.RestaurantService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class})
public class RestaurantServiceTestWithConfigurationAndMocking extends BaseRestaurantServiceTest {

    @InjectMocks
    private RestaurantService service;

    @Mock
    private RestaurantDAO restaurantDAO;

    @Override
    protected RestaurantService getRestaurantService() {
        return service;
    }

    @Before
    public void setup() {

    }


    @Test
    public void test_add_valid_restaurant() throws ValidationException {

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Marty");
        restaurant.setAddress("Str.Horea,nr.4");
        restaurant.setCapacity(50);
        restaurant.setDescription("Food and Ambiance");
        restaurant.setRestType(RestType.CUISINE);
        restaurant.setId(1);

        when(getRestaurantService().listAll()).thenReturn(Arrays.asList(restaurant));

        getRestaurantService().save(restaurant);

        Assert.assertEquals(1, getRestaurantService().listAll().size());
        Restaurant fromDB = getRestaurantService().listAll().iterator().next();
        Assert.assertNotNull(fromDB);
        Assert.assertTrue(fromDB.getId() > 0);
        restaurant.setId(fromDB.getId());
        Assert.assertEquals(restaurant, fromDB);
    }
//
//    Review review = new Review();
//		review.setRestaurant_id(1);
//		review.setReview("Really nice place");
//		review.setDateOfVisit(new Date());
//		review.setDateOfReview(new Date());
//		review.setRating("GOOD");
//		review.setId(1);
//
//    when(getReviewService().listAll()).thenReturn(Arrays.asList(review));
//
//    getReviewService().save(review);
//
//		Assert.assertEquals(1, getReviewService().listAll().size());
//    Review fromDB = getReviewService().listAll().iterator().next();
//		Assert.assertNotNull(fromDB);
//		Assert.assertTrue(fromDB.getId() > 0);
//		review.setId(fromDB.getId());
//		Assert.assertEquals(review, fromDB);

//    @Test
//    public void test_search_by_name() throws ValidationException {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("Marty");
//        restaurant.setAddress("Str.Horea,nr.4");
//        restaurant.setCapacity(50);
//        restaurant.setDescription("Food and Ambiance");
//        restaurant.setRestType(RestType.CUISINE);
//        getRestaurantService().save(restaurant);
//
//        Assert.assertEquals(1,getRestaurantService().searchByName("Marty").size());
//    }
}
