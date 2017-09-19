package com.tp.loadbalancer;

import com.tp.loadbalancer.config.GroupsPropertiesInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties(GroupsPropertiesInterceptor.class)
public class TPLoadBalancer {

    public static void main(String[] args){
        SpringApplication.run(TPLoadBalancer.class, args);
    }
}
