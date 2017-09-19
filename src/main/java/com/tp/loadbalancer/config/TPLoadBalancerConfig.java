package com.tp.loadbalancer.config;

import com.tp.loadbalancer.dispatching.BucketCalculator;
import com.tp.loadbalancer.dispatching.GroupWeightsLoader;
import com.tp.loadbalancer.dispatching.RequestsDispatcher;
import com.tp.loadbalancer.controller.LoadBalancerController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
