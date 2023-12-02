package com.bloodbank.bloodbank.service.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
public class EncoderConfig {
    @Bean
    public PasswordEncoder encoder() {
        return new PasswordEncoder();
    }
}
