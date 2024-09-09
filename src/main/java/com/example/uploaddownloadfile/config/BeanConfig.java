package com.example.uploaddownloadfile.config;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
public class BeanConfig {

    @Bean
    ModelMapper modelMapper () {
        return new ModelMapper();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }



}
