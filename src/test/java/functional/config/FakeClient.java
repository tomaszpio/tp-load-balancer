package functional.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FakeClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(FakeClient.class);
    private final RestTemplate restTemplate = new RestTemplate();
    final Map<String, AtomicInteger> distribution = new HashMap<>();
    final AtomicLong requestsCounter = new AtomicLong(0);

    @Scheduled(fixedRate = 10000)
    private void printStats(){
        LOGGER.info("Distribution stats for {} requests", requestsCounter.get());
        distribution.entrySet().forEach(entry -> {
            LOGGER.info("{} percentage of traffic goes to group {}", getActualPercentageDistribution(entry, requestsCounter.doubleValue()), entry.getKey());
        });
    }


    @Scheduled(fixedRate = 10)
    private void start() {
        String group = restTemplate.getForObject("http://localhost:8080/route?id="+Long.toHexString(Double.doubleToLongBits(Math.random())), String.class);
        calculateDistribution(distribution, group);
        requestsCounter.incrementAndGet();
    }

    private void calculateDistribution(Map<String, AtomicInteger> distribution, String group) {
        distribution.computeIfAbsent(group, s -> new AtomicInteger(1));
        distribution.computeIfPresent(group, (s, atomicInteger) -> {
            atomicInteger.incrementAndGet();
            return atomicInteger;
        });
    }

    private double getActualPercentageDistribution(Map.Entry<String, AtomicInteger> entry, Double amountOfRequests) {
        final double countedRequests = entry.getValue().get();
        return ( countedRequests / amountOfRequests) * 100;
    }
}
