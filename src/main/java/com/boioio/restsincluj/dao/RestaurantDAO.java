package com.boioio.restsincluj.dao;

import com.boioio.restsincluj.domain.Restaurant;

import java.util.Collection;

public interface RestaurantDAO extends BaseDAO<Restaurant> {

    Collection<Restaurant> searchByName(String query);
}
