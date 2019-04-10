package com.boioio.restsincluj.service;

import com.boioio.restsincluj.domain.User;
import org.junit.After;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.LinkedList;

@TestPropertySource(locations="classpath:test.properties")
public abstract class BaseUserServiceTest {

    protected abstract UserService getUserService();

    @After
    public void tearDown(){
        Collection<User> users = new LinkedList<>(getUserService().listAll());

        for (User user: users) {
         getUserService().delete(user.getId()) ;
        }
    }
}
