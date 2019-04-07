package com.boioio.mvc;

import com.boioio.restsincluj.domain.User;
import com.boioio.restsincluj.exception.ValidationException;
import com.boioio.restsincluj.service.UserService;
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
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger("UserController");

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("register/add");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping("/save")
    public ModelAndView save(@Valid User user,
                             BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasErrors()) {
            try {
                userService.save(user);
                RedirectView redirectView = new RedirectView("/register");
                modelAndView.setView(redirectView);
            } catch (ValidationException ex) {

                LOGGER.error("validation error", ex);

                List<String> errors = new LinkedList<>();
                errors.add(ex.getMessage());
                modelAndView = new ModelAndView("register/add");
                modelAndView.addObject("errors", errors);
                modelAndView.addObject("user", user);
            }

        } else {
            List<String> errors = new LinkedList<>();

            for (FieldError error :
                    bindingResult.getFieldErrors()) {
                errors.add(error.getField() + ":" + error.getCode());
            }

            modelAndView = new ModelAndView("register/add");
            modelAndView.addObject("errors", errors);
            modelAndView.addObject("user", user);
        }

        return modelAndView;
    }
}
