package functional.config;

import app.config.TPLoadBalancerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;


@Import(TPLoadBalancerConfig.class)
@Configuration
public class FunctionalTestConfig {

    @Bean
    public FakeClient fakeClient(){
        return new FakeClient();
    }
}
