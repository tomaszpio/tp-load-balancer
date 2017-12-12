package com.tp.loadbalancer.controller;

import com.tp.loadbalancer.dispatching.RequestsDispatcher;
import com.tp.loadbalancer.exceptions.GroupNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ratpack.handling.Handler;
import ratpack.server.RatpackServer;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoadBalancerRatpackController {

    private final RequestsDispatcher requestsDispatcher;
    private final Map<String,String> requestsCache = new ConcurrentHashMap<>();
    public LoadBalancerRatpackController(final RequestsDispatcher requestsDispatcher) {

        this.requestsDispatcher = requestsDispatcher;
    }

    public void init(){
       try {
           RatpackServer.start(server -> server
                   .handlers(chain -> chain
                           .get(":route", processRouteParam())
                   )
           );
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

    private Handler processRouteParam() {
        return context -> {
            if(!context.getRequest().getQueryParams().containsKey("id")){
                throw new GroupNotFoundException("Wrong parameters passed");
            }
            context.getResponse().send(route(context.getRequest().getQueryParams().get("id")));
        };
    }

    public String route(final String id){
        if(requestsCache.containsKey(id)) {
            return requestsCache.get(id);
        }else {
            final String group = this.requestsDispatcher.dispatch(id);
            requestsCache.put(id, group);
            return group;
        }

    }
}
