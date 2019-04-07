package com.boioio.mvc;

import com.boioio.restsincluj.domain.Rating;
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
@RequestMapping("/review")
public class ReviewController {

    private static Logger LOGGER = LoggerFactory.getLogger("ReviewController");

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping("")
    public ModelAndView list() {
        ModelAndView result = new ModelAndView("review/list");


        Collection<Review> reviews = reviewService.listAll();
        Collection<Restaurant> restaurants = restaurantService.listAll();


        result.addObject("reviews", reviews);

        Map<String, String> allReviews = new HashMap<>();
        for (Review review : reviews) {
            Restaurant restaurant = restaurantService.get(review.getRestaurant_id());
            allReviews.put(restaurant.getId() + "", restaurant.getName());

        }
        result.addObject("allReviews", allReviews);

        Map<String, Date> reviewsDate = new HashMap<>();
        for (Review review : reviews) {
            Restaurant restaurant = restaurantService.get(review.getRestaurant_id());
            reviewsDate.put(review.getRestaurant_id() + "", review.getDateOfReview());

        }
        result.addObject("reviewsDate", reviewsDate);

        return result;
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("review/add");

        Collection<Restaurant> restaurants = restaurantService.listAll();
        modelAndView.addObject("restaurants", restaurants);

        List<Rating> ratings = new LinkedList<>();
        ratings.add(Rating.valueOf("TERRIBLE"));
        ratings.add(Rating.valueOf("POOR"));
        ratings.add(Rating.valueOf("AVERAGE"));
        ratings.add(Rating.valueOf("GOOD"));
        ratings.add(Rating.valueOf("EXCELLENT"));

        modelAndView.addObject("ratings", ratings);

        modelAndView.addObject("review", new Review());
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") long id) {
        Review review = reviewService.findById(id);
        ModelAndView modelAndView = new ModelAndView("review/add");
        modelAndView.addObject("review", review);
        return modelAndView;
    }

    @RequestMapping(value= "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") long id) {
        reviewService.delete(id);
        return "redirect:/review";
    }

    @RequestMapping("/save")
    public ModelAndView save(@Valid Review review,
                             BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasErrors()) {
            try {
                reviewService.save(review);
                RedirectView redirectView = new RedirectView("/review");
                modelAndView.setView(redirectView);
            } catch (ValidationException ex) {

                LOGGER.error("validation error", ex);

                List<String> errors = new LinkedList<>();
                errors.add(ex.getMessage());
                modelAndView = new ModelAndView("review/add");
                modelAndView.addObject("errors", errors);
                modelAndView.addObject("review", review);
            }

        } else {
            List<String> errors = new LinkedList<>();

            for (FieldError error :
                    bindingResult.getFieldErrors()) {
                errors.add(error.getField() + ":" + error.getDefaultMessage());
            }

            modelAndView = new ModelAndView("review/add");
            modelAndView.addObject("errors", errors);
            modelAndView.addObject("review", review);
        }

        return modelAndView;
    }

}
