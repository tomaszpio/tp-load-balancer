package com.tp.loadbalancer.controller;

import com.tp.loadbalancer.dispatching.RequestsDispatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class LoadBalancerController {

    private final RequestsDispatcher requestsDispatcher;
    private final Map<String,String> requestsCache = new ConcurrentHashMap<>();
    public LoadBalancerController(final RequestsDispatcher requestsDispatcher) {

        this.requestsDispatcher = requestsDispatcher;
    }

    @RequestMapping("/route")
    public String route(@RequestParam(value="id")final String id){

//        if(requestsCache.containsKey(id)) {
//            return requestsCache.get(id);
//        }else {
//            final String group = this.requestsDispatcher.dispatch(id);
//            requestsCache.put(id, group);
//            return group;
//        }
        return this.requestsDispatcher.dispatch(id);
    }
}
