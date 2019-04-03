package com.boioio.mvc;

import com.boioio.restsincluj.domain.Review;
import com.boioio.restsincluj.exception.ValidationException;
import com.boioio.restsincluj.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

        private static Logger LOGGER = LoggerFactory.getLogger("ReviewController");

        @Autowired
        private ReviewService reviewService;

        @RequestMapping("")
        public ModelAndView list() {
            ModelAndView result = new ModelAndView("review/list");


            Collection<Review> reviews = reviewService.listAll();
            result.addObject("reviews", reviews);

            return result;
        }

        @RequestMapping("/add")
        public ModelAndView add() {
            ModelAndView modelAndView = new ModelAndView("review/add");
            modelAndView.addObject("review", new Review());
            return modelAndView;
        }

        @RequestMapping("/view")
        public ModelAndView view() {
            ModelAndView modelAndView = new ModelAndView("review/view");
            modelAndView.addObject("review", new Review());
            return modelAndView;
        }

        @RequestMapping("/edit")
        public ModelAndView edit(long id) {
            Review review = reviewService.get(id);
            ModelAndView modelAndView = new ModelAndView("review/add");
            modelAndView.addObject("review", review);
            return modelAndView;
        }

        @RequestMapping("/delete")
        public String delete(long id) {
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
