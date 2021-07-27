package com.otus.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Encoder {

    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
