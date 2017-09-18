package functional.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

public class FakeClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(FakeClient.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10)
    private void start() {
        String response = restTemplate.getForObject("http://localhost:8080/route?id="+Long.toHexString(Double.doubleToLongBits(Math.random())), String.class);
       System.out.println(response);
    }
}
