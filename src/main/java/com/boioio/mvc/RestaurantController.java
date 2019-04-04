package com.boioio.mvc;

import com.boioio.restsincluj.domain.Restaurant;
import com.boioio.restsincluj.exception.ValidationException;
import com.boioio.restsincluj.service.RestaurantService;
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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    private static Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping("")
    public ModelAndView listAll() {
        ModelAndView result = new ModelAndView("restaurant/list");

        Collection<Restaurant> restaurants = restaurantService.listAll();

        result.addObject("restaurants", restaurants);

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

    @RequestMapping(value = "/listReview{id}", method = RequestMethod.GET)
    public ModelAndView listReview(@PathVariable("id") long id) {
        ModelAndView result = new ModelAndView("restaurant/list");

        Restaurant restaurant = restaurantService.findById(id);
        //ProjectCost projectCost = new ProjectCost();
        String review = restaurantService.listRestaurantAndReview(id);
//        projectCost.setCost(cost);
//        projectCost.setName(project.getName());

        result.addObject("review", review);

        return result;
    }
}
