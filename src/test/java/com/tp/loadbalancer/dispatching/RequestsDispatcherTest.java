package com.tp.loadbalancer.dispatching;

import com.tp.loadbalancer.exceptions.GroupNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class RequestsDispatcherTest {

    private RequestsDispatcher requestsDispatcher;

    private Map<Integer, String> groupWeights = spy(new HashMap<>());


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        groupWeights.put(2, "GroupA");
        groupWeights.put(5, "GroupB");
        groupWeights.put(10, "GroupC");
        requestsDispatcher = new RequestsDispatcher(groupWeights, new BucketCalculator(10));
    }

    @Test
    public void WhenOneIdGiven_ShouldReturnTheSameValue() throws Exception {

        //given
        IntStream.range(0,10).forEach(value -> {

            //when
            String group = requestsDispatcher.dispatch("12334");

            //then
            assertThat(group).isEqualTo("GroupC");

            //when
            group = requestsDispatcher.dispatch("12334");

            //then
            assertThat(group).isEqualTo("GroupC");
        });
    }

    @Test(expected= GroupNotFoundException.class)
    public void WhenGroupNotFound_ShouldThrow() throws Exception {

        //given
        IntStream.range(0,10).forEach(value -> {
        when(groupWeights.entrySet()).thenReturn(new HashSet<>());

            //when
            requestsDispatcher.dispatch("12334");

        });
    }

}
