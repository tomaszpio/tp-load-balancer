package functional;

import app.config.GroupsPropertiesInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableConfigurationProperties(GroupsPropertiesInterceptor.class)
public class FunctionalTestRunner {
    public static void main(String[] args){
        SpringApplication.run(FunctionalTestRunner.class, args);
    }
}
