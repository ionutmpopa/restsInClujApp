package com.boioio.restsincluj.service;

import com.boioio.restsincluj.domain.RestType;
import com.boioio.restsincluj.domain.Restaurant;
import com.boioio.restsincluj.exception.ValidationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;
import com.boioio.restsincluj.service.RestaurantService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.*;

@TestPropertySource(locations = "classpath:test.properties")
public abstract class BaseRestaurantServiceTest {

    protected abstract RestaurantService getRestaurantService();

    @After
    public void tearDown() {
        Collection<Restaurant> restaurants = new LinkedList<Restaurant>(getRestaurantService().listAll());

        for (Restaurant restaurant : restaurants) {
            getRestaurantService().delete(restaurant.getId());
        }
    }

    @Test
    public void test_empty_get_all() {
        Collection<Restaurant> restaurants = getRestaurantService().listAll();
        Assert.assertTrue(restaurants.isEmpty());
    }

    @Test
    public void test_add_valid_restaurant() throws ValidationException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Marty");
        restaurant.setAddress("Str.Horea,nr.4");
        restaurant.setCapacity(50);
        restaurant.setDescription("Food and Ambiance");
        restaurant.setRestType(RestType.CUISINE);
        getRestaurantService().save(restaurant);
        Assert.assertEquals(1, getRestaurantService().listAll().size());
        Restaurant fromDB = getRestaurantService().listAll().iterator().next();
        Assert.assertNotNull(fromDB);
        Assert.assertTrue(fromDB.getId() > 0);
        restaurant.setId(fromDB.getId());
        Assert.assertEquals(restaurant, fromDB);
    }

    @Test(expected = ValidationException.class)
    public void test_add_no_restaurant_name() throws ValidationException {
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress("Str.Horea,nr.4");
        restaurant.setCapacity(50);
        restaurant.setDescription("Food and Ambiance");
        restaurant.setRestType(RestType.CUISINE);
        getRestaurantService().save(restaurant);
    }

    @Test(expected = ValidationException.class)
    public void test_add_no_restaurant_adders() throws ValidationException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Marty");
        restaurant.setCapacity(50);
        restaurant.setDescription("Food and Ambiance");
        restaurant.setRestType(RestType.CUISINE);
        getRestaurantService().save(restaurant);

    }


    @Test(expected = ValidationException.class)
    public void test_add_no_restaurant_capacity() throws ValidationException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Marty");
        restaurant.setAddress("Str.Horea,nr.4");
        restaurant.setDescription("Food and Ambiance");
        restaurant.setRestType(RestType.CUISINE);
        getRestaurantService().save(restaurant);

    }

    @Test(expected = ValidationException.class)
    public void test_add_no_restaurant_description() throws ValidationException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Marty");
        restaurant.setAddress("Str.Horea,nr.4");
        restaurant.setCapacity(50);
        restaurant.setRestType(RestType.CUISINE);
        getRestaurantService().save(restaurant);

    }

    @Test(expected = ValidationException.class)
    public void test_add_no_restaurant_type() throws ValidationException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Marty");
        restaurant.setAddress("Str.Horea,nr.4");
        restaurant.setCapacity(50);
        restaurant.setDescription("Food and Ambiance");
        getRestaurantService().save(restaurant);

    }


    @Test(expected = ValidationException.class)
    public void test_add_empty() throws ValidationException {
        Restaurant restaurant = new Restaurant();

        getRestaurantService().save(restaurant);

    }


    @Test
    public void test_delete_nonexistent() throws ValidationException {

        Assert.assertFalse(getRestaurantService().delete(-13l));

    }
}