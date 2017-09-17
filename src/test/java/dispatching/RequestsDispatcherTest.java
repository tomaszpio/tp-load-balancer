package dispatching;

import app.config.GroupsPropertiesInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.mockito.Mockito.when;

public class RequestsDispatcherTest {

    private static final double SMALL_AMOUNT_REQUESTS = 1000.0;
    private static final double BIG_AMOUNT_REQUESTS = 1000_000.0;

    @Mock
    private GroupsPropertiesInterceptor groupsProperties;

    private RequestsDispatcher requestsDispatcher;
    private Map<String, Integer> groupWeights = new HashMap<>();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        groupWeights.put("GroupA", 2);
        groupWeights.put("GroupB", 3);
        groupWeights.put("GroupC", 5);
        when(groupsProperties.getGroupsWeights()).thenReturn(groupWeights);
        requestsDispatcher = new RequestsDispatcher(groupsProperties);
    }

    @Test
    public void WhenOneIdGiven_ShouldReturnTheSameValue() throws Exception {
        //given
        IntStream.range(0,10).forEach(value -> {

            //when
            String group = requestsDispatcher.dispatch("12334");

            //then
            assertThat(group).isEqualTo("GroupC");
        });
    }

    @Test
    public void WhenSmallerAmountOfIdsGiven_DistributionIsProperWith5PercentageAccuracy(){

        //given
        final Map<String, AtomicInteger> distribution = new HashMap<>();
        IntStream.range(0, (int)SMALL_AMOUNT_REQUESTS).forEach(value -> {
            String id = Long.toHexString(Double.doubleToLongBits(Math.random()));

            //when
            String group = requestsDispatcher.dispatch(id);
            calculateDistribution(distribution, group);
        });

        //then
        distribution.entrySet().forEach(entry -> {
            final Double expectedPercentageDistribution = getExpectedPercentageDistribution(entry);
            final double actualPercentageDistribution = getActualPercentageDistribution(entry, SMALL_AMOUNT_REQUESTS);
            assertThat(expectedPercentageDistribution).isCloseTo(actualPercentageDistribution, withinPercentage(5));
        });
    }

    @Test
    public void WhenBigAmountOfIdsGiven_DistributionIsProperWith1PercentageAccuracy(){

        //given
        final Map<String, AtomicInteger> distribution = new HashMap<>();
        IntStream.range(0, (int)BIG_AMOUNT_REQUESTS).forEach(value -> {
            String id = Long.toHexString(Double.doubleToLongBits(Math.random()));

            //when
            String group = requestsDispatcher.dispatch(id);
            calculateDistribution(distribution, group);
        });

        //then
        distribution.entrySet().forEach(entry -> {
            final Double expectedPercentageDistribution = getExpectedPercentageDistribution(entry);
            final double actualPercentageDistribution = getActualPercentageDistribution(entry, BIG_AMOUNT_REQUESTS);
            assertThat(expectedPercentageDistribution).isCloseTo(actualPercentageDistribution, withinPercentage(1));
        });
    }

    private void calculateDistribution(Map<String, AtomicInteger> distribution, String group) {
        distribution.computeIfAbsent(group, s -> new AtomicInteger(1));
        distribution.computeIfPresent(group, (s, atomicInteger) -> {
            atomicInteger.incrementAndGet();
            return atomicInteger;
        });
    }

    private Double getExpectedPercentageDistribution(Map.Entry<String, AtomicInteger> entry) {
        final String group = entry.getKey();
        return (double) (groupsProperties.getGroupsWeights().get(group) * 10);
    }

    private double getActualPercentageDistribution(Map.Entry<String, AtomicInteger> entry, Double amountOfRequests) {
        final double countedRequests = entry.getValue().get();
        return ( countedRequests / amountOfRequests) * 100;
    }

}
