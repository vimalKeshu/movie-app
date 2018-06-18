package com.movie;

import org.keycloak.adapters.springboot.MultitenantConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration(MultitenantConfiguration.class)
//@EnableAutoConfiguration
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(App.class, args);
    }
}
