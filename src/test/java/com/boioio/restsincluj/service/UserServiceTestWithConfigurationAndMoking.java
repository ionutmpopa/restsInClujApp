package com.boioio.restsincluj.service;

import com.boioio.restsincluj.ApplicationConfiguration;
import com.boioio.restsincluj.dao.UserDAO;
import com.boioio.restsincluj.domain.User;
import com.boioio.restsincluj.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class})
public class UserServiceTestWithConfigurationAndMoking extends BaseUserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDAO userDAO;

    @Override
    protected UserService getUserService() {
        return userService;
    }

    @Before
    public void setup() {

    }

    @Test
    public void test_add_empty_get_all() {
        Collection<User> users = getUserService().listAll();
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void test_add_valid_user() throws ValidationException {
        User user = new User();
        user.setEmail("ciuve@gmail.com");
        user.setPassword("1111");
        user.setFirstName("Bogdi");
        user.setLastName("Ciuve");
        user.setEnabled(true);
        user.setId(1);

        when(getUserService().listAll()).thenReturn(Arrays.asList(user));

        getUserService().save(user);

        Assert.assertEquals(1, getUserService().listAll().size());
        User fromDB = getUserService().listAll().iterator().next();
        Assert.assertNotNull(fromDB);
        Assert.assertTrue(fromDB.getId() > 0);
        user.setId(fromDB.getId());
        Assert.assertEquals(user, fromDB);
    }

    @Test(expected = ValidationException.class)
    public void test_add_user_without_email() throws ValidationException {
        User user = new User();
        user.setPassword("1111");
        user.setFirstName("Bogdi");
        user.setLastName("Ciuve");
        user.setEnabled(true);
        getUserService().save(user);
    }

    @Test(expected = ValidationException.class)
    public void test_add_user_without_password() throws ValidationException {
        User user = new User();
        user.setEmail("ciuve@gmail.com");
        user.setFirstName("Bogdi");
        user.setLastName("Ciuve");
        user.setEnabled(true);
        getUserService().save(user);
    }

    @Test(expected = ValidationException.class)
    public void test_add_user_without_first_name() throws ValidationException {
        User user = new User();
        user.setEmail("ciuve@gmail.com");
        user.setPassword("1111");
        user.setLastName("Ciuve");
        user.setEnabled(true);
        getUserService().save(user);
    }

    @Test(expected = ValidationException.class)
    public void test_add_user_without_last_name() throws ValidationException{
        User user = new User();
        user.setEmail("ciuve@gmail.com");
        user.setPassword("1111");
        user.setFirstName("Bogdi");
        user.setEnabled(true);
        getUserService().save(user);
    }



    @Test(expected = ValidationException.class)
    public void test_add_empty_user() throws ValidationException{
        User user = new User();

        getUserService().save(user);
    }

    @Test
    public void test_delete_nonexistent_user() throws ValidationException{
        Assert.assertFalse(getUserService().delete(4l));
    }

    @Test
    public void test_search_by_name_no_result() throws ValidationException{
        User user = new User();
        user.setEmail("ciuve@gmail.com");
        user.setPassword("1111");
        user.setFirstName("Bogdi");
        user.setLastName("Ciuve");
        user.setEnabled(true);
        getUserService().save(user);
        Assert.assertEquals(0, getUserService().search("Johnny").size());
    }


}


