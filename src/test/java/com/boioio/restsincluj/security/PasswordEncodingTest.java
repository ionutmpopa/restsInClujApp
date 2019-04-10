package com.boioio.restsincluj.security;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodingTest {

    @Test
    public void encriptPasswordWithBCrypt() {
        String password = "12345";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        System.out.println(hashedPassword);
    }
}
