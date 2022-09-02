package com.example.MC3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Mc3Application
{

    public static void main( String[] args )
    {
        SpringApplication.run( Mc3Application.class, args );
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
