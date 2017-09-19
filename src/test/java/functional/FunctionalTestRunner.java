package functional;

import com.tp.loadbalancer.TPLoadBalancer;
import functional.config.FunctionalTestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FunctionalTestRunner {
    public static void main(String[] args){
        final SpringApplication app = new SpringApplication(TPLoadBalancer.class, FunctionalTestConfig.class);
        app.run(args);
    }
}
