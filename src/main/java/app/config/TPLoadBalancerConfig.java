package app.config;

import dispatching.RequestsDispatcher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import controller.LoadBalancerController;

@Configuration
public class TPLoadBalancerConfig {

    @Bean
    public RequestsDispatcher requestsDispatcher(GroupsPropertiesInterceptor groupsProperties){
        return new RequestsDispatcher(groupsProperties);
    }

    @Bean
    public LoadBalancerController loadBalancerView(final RequestsDispatcher requestsDispatcher){
        return new LoadBalancerController(requestsDispatcher);
    }

}
