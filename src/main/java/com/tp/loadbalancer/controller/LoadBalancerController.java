package com.tp.loadbalancer.controller;

import com.tp.loadbalancer.dispatching.RequestsDispatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoadBalancerController {

    private final RequestsDispatcher requestsDispatcher;

    public LoadBalancerController(final RequestsDispatcher requestsDispatcher) {
        this.requestsDispatcher = requestsDispatcher;
    }

    @RequestMapping("/route")
    public String route(@RequestParam(value="id")final String id){

        return this.requestsDispatcher.dispatch(id);
    }
}
