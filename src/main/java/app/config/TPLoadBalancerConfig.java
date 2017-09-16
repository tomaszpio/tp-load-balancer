package app.config;

import exceptions.WrongArgumentsExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import controller.LoadBalancerController;

@Configuration
public class TPLoadBalancerConfig {

    @Bean
    public LoadBalancerController loadBalancerView(){
        return new LoadBalancerController();
    }

//    @Bean
//    public WrongArgumentsExceptionHandler wrongArgumentsExceptionHandler(){
//        return new WrongArgumentsExceptionHandler();
//    }
}
