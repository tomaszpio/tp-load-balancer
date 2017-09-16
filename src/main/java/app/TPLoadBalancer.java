package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class TPLoadBalancer {
    public static void main(String[] args){
        SpringApplication.run(TPLoadBalancer.class, args);
    }
}
