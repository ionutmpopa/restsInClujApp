package com.boioio.restsincluj.testsuite;

import com.boioio.restsincluj.service.RestaurantServiceTestWithConfigurationAndMocking;
import com.boioio.restsincluj.service.ReviewServiceTestWithConfigurationAndMocking;
import com.boioio.restsincluj.service.UserServiceTestWithConfigurationAndMoking;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ReviewServiceTestWithConfigurationAndMocking.class, RestaurantServiceTestWithConfigurationAndMocking.class, UserServiceTestWithConfigurationAndMoking.class})

public class ServiceSuite {

}
