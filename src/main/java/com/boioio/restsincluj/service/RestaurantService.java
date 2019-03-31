package com.boioio.restsincluj.service;

import com.boioio.restsincluj.dao.RestaurantDAO;
import com.boioio.restsincluj.domain.Restaurant;
import com.boioio.restsincluj.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class RestaurantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private RestaurantDAO restaurantDAO;


    public RestaurantDAO getRestaurantDAO() {
        return restaurantDAO;
    }

    public void setRestaurantDAO(RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    public Collection<Restaurant> listAll() {
        return restaurantDAO.getAll();
    }


    public boolean delete(Long id) {
        LOGGER.debug("Deleting restaurant with id: " + id);
        Restaurant restaurant = restaurantDAO.findById(id);
        if (restaurant != null) {
            restaurantDAO.delete(restaurant);
            return true;
        }

        return false;
    }

    public Restaurant findById(long id) {
        return restaurantDAO.findById(id);
    }


    public void save(Restaurant restaurant) throws ValidationException {
        LOGGER.debug("Saving: " + restaurant);
        validate(restaurant);
        restaurantDAO.update(restaurant);
    }

    private void validate(Restaurant restaurant) throws ValidationException {

        List<String> errors = new LinkedList<String>();
        if (StringUtils.isEmpty(restaurant.getName())) {
            errors.add("Restaurant name is Empty");
        }

        if (StringUtils.isEmpty(restaurant.getAddress())) {
            errors.add("Restaurant address is Empty");
        }

        if (restaurant.getRestType() == null) {
            errors.add("Type is Empty");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors.toArray(new String[] {}));
        }
    }

}


