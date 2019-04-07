package com.boioio.restsincluj;

import com.boioio.restsincluj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by sebi on 4/19/17.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

//        CustomJdbcUserDetailsService customJdbcUserDetailsService = new CustomJdbcUserDetailsService();
//        customJdbcUserDetailsService.set(dataSource);
//
//        auth.authenticationProvider(authenticationProvider());
        auth
                .jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/", "/file").permitAll()
                .antMatchers("/confirm").permitAll()

               // .antMatchers("/employee/delete").hasRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .permitAll()


                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and().exceptionHandling()
                .accessDeniedPage("/access-denied");


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/ext-img/**");
    }
}
