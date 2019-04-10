package com.boioio.restsincluj.service;

import com.boioio.restsincluj.dao.UserDAO;
import com.boioio.restsincluj.domain.User;
import com.boioio.restsincluj.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    public Collection<User> listAll() {
        return userDAO.getAll();
    }

    public Collection<User> search( String query) {
        LOGGER.debug("Searching for " + query);
        return userDAO.searchByName(query);
    }

    public boolean delete(Long id) {
        LOGGER.debug("Deleting employee for id: " + id);
        User user = null;
        try {
            user = userDAO.findById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("trying to fing an inexisting employee");
            return false;
        }
        if (user != null) {
            userDAO.delete(user);
            return true;
        }

        return false;
    }

    public User get(Long id) {
        LOGGER.debug("Getting employee for id: " + id);
        return userDAO.findById(id);
    }

    public void save(User user) throws ValidationException {
        LOGGER.debug("Saving: " + user);
        validate(user);
        userDAO.update(user);
    }

    private void validate(User user) throws ValidationException {
        Date currentDate = new Date();
        List<String> errors = new LinkedList<String>();

        if(StringUtils.isEmpty(user.getEmail())){
            errors.add("Email required");
        }

        if(StringUtils.isEmpty(user.getPassword())){
            errors.add("Password required");
        }

        if (StringUtils.isEmpty(user.getFirstName())) {
            errors.add("First Name is Empty");
        }

        if (StringUtils.isEmpty(user.getLastName())) {
            errors.add("Last Name is Empty");
        }

        if (user.getEmail() == null) {
            errors.add("Email is Empty");
        }
        if(user.getPassword() == null) {
            errors.add("Password Missing");
        }


            if (!errors.isEmpty()) {
                throw new ValidationException(errors.toArray(new String[]{}));
            }
        }


    public UserDAO getDao() {
        return userDAO;
    }

    public void setDao(UserDAO dao) {
        this.userDAO = dao;
    }


}