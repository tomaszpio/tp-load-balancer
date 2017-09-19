package functional.config;

import com.tp.loadbalancer.config.GroupsPropertiesInterceptor;
import com.tp.loadbalancer.config.TPLoadBalancerConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@Import(TPLoadBalancerConfig.class)
@Configuration
@EnableAutoConfiguration
@EnableScheduling
@EnableConfigurationProperties(GroupsPropertiesInterceptor.class)
public class FunctionalTestConfig {

    @Bean
    public FakeClient fakeClient(){
        return new FakeClient();
    }
}
