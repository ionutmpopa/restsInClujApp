package com.boioio.restsincluj.dao;

import com.boioio.restsincluj.domain.User;

import java.util.Collection;

public interface UserDAO extends BaseDAO<User>{

    Collection<User> searchByName(String query);
}