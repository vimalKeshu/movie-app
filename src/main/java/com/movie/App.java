package com.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import({ com.corvesta.keyspring.activitylog.client.ActivityLogInfoService.class, com.corvesta.keyspring.activitylog.client.RestTemplateConfig.class })
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(App.class, args);
    }
}
