package com.boioio.mvc;

import com.boioio.restsincluj.domain.Restaurant;
import com.boioio.restsincluj.domain.Review;
import com.boioio.restsincluj.exception.ValidationException;
import com.boioio.restsincluj.service.RestaurantService;
import com.boioio.restsincluj.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    private static Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReviewService reviewService;

    @RequestMapping("")
    public ModelAndView listAll() {
        ModelAndView result = new ModelAndView("restaurant/list");

        Collection<Restaurant> restaurants = restaurantService.listAll();
        Collection<Review> reviews = reviewService.listAll();

        result.addObject("restaurants", restaurants);

        Map<String, String> allReviews = new HashMap<>();
        for (Review review : reviews) {
            Restaurant restaurant = restaurantService.get(review.getRestaurant_id());
            allReviews.put(review.getRestaurant_id() + "", review.getReview());

        }
        result.addObject("allReviews", allReviews);

        return result;
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("restaurant/add");
        modelAndView.addObject("restaurant", new Restaurant());
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") long id) {
        Restaurant restaurant = restaurantService.findById(id);
        ModelAndView modelAndView = new ModelAndView("restaurant/add");
        modelAndView.addObject("restaurant", restaurant);
        return modelAndView;
    }

    @RequestMapping(value= "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") long id) {
        restaurantService.delete(id);
        return "redirect:/restaurant";
    }

    @RequestMapping("/save")
    public ModelAndView save(@Valid Restaurant restaurant,
                             BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasErrors()) {
            try {
                restaurantService.save(restaurant);
                RedirectView redirectView = new RedirectView("/restaurant");
                modelAndView.setView(redirectView);
            } catch (ValidationException ex) {

                LOGGER.error("validation error", ex);

                List<String> errors = new LinkedList<>();
                errors.add(ex.getMessage());
                modelAndView = new ModelAndView("restaurant/add");
                modelAndView.addObject("errors", errors);
                modelAndView.addObject("restaurant", restaurant);
            }

        } else {
            List<String> errors = new LinkedList<>();

            for (FieldError error :
                    bindingResult.getFieldErrors()) {
                errors.add(error.getField() + ":" + error.getCode());
            }

            modelAndView = new ModelAndView("restaurant/add");
            modelAndView.addObject("errors", errors);
            modelAndView.addObject("restaurant", restaurant);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/reviewList{id}", method = RequestMethod.GET)
    public ModelAndView reviewList(@PathVariable("id") long id) {
        ModelAndView result = new ModelAndView("restaurant/reviewList");

        Collection<Restaurant> restaurants = restaurantService.listAll();
        Collection<Review> reviews = reviewService.listAll();

        result.addObject("restaurants", restaurants);

        Map<String, String> allListReviews = new HashMap<>();
        for (Review review : reviews) {

            if (review.getRestaurant_id() == id) {

                Restaurant restaurant = restaurantService.get(review.getRestaurant_id());
                allListReviews.put(review.getRestaurant_id() + "", review.getReview());
            }
        }
        result.addObject("allListReviews", allListReviews);

        return result;
    }
}
