package app.config;

import dispatching.BucketCalculator;
import dispatching.GroupWeightsLoader;
import dispatching.RequestsDispatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import controller.LoadBalancerController;

@Configuration
public class TPLoadBalancerConfig {

    @Bean
    public GroupWeightsLoader groupWeightsCalculator(final GroupsPropertiesInterceptor groupsProperties) {
        return new GroupWeightsLoader(groupsProperties);
    }

    @Bean
    public BucketCalculator bucketCalculator(@Value("${numberOfBuckets}") final Integer numberOfBuckets){
        return new BucketCalculator(numberOfBuckets);
    }
    @Bean
    public RequestsDispatcher requestsDispatcher(GroupWeightsLoader groupWeightsLoader, BucketCalculator bucketCalculator){
        return new RequestsDispatcher(groupWeightsLoader.load(), bucketCalculator);
    }

    @Bean
    public LoadBalancerController loadBalancerView(final RequestsDispatcher requestsDispatcher){
        return new LoadBalancerController(requestsDispatcher);
    }

}
