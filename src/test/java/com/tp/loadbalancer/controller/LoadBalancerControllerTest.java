package com.tp.loadbalancer.controller;

import com.tp.loadbalancer.config.GroupsPropertiesInterceptor;
import com.tp.loadbalancer.dispatching.RequestsDispatcher;
import com.tp.loadbalancer.exceptions.GroupNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(LoadBalancerController.class)
public class LoadBalancerControllerTest {

    public static final String GROUP = "group";

    @MockBean
    private RequestsDispatcher requestsDispatcher;

    @MockBean
    private GroupsPropertiesInterceptor groupsPropertiesInterceptor;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void WhenIdGiven_ShouldReturnGroupAnd200Status() throws Exception {
        //given
        when(requestsDispatcher.dispatch(anyString())).thenReturn(GROUP);

        //when
        this.mvc.perform(get("/route?id=123").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().string(GROUP));
    }

    @Test
    public void WhenIdMissed_ShouldReturn400Status() throws Exception {

        //when
        this.mvc.perform(get("/route").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void WhenWrongParam_ShouldReturn400Status() throws Exception {

        //when
        this.mvc.perform(get("/route?wer=123").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void WhenGroupNotFount_ShouldReturn404Status() throws Exception {

        //given
        when(requestsDispatcher.dispatch(anyString())).thenThrow(new GroupNotFoundException(""));

        //when
        this.mvc.perform(get("/route?wer=123").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest());
    }

}