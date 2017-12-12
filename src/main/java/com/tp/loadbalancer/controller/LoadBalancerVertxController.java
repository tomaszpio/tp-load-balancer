package com.tp.loadbalancer.controller;

import com.tp.loadbalancer.dispatching.RequestsDispatcher;
import com.tp.loadbalancer.exceptions.GroupNotFoundException;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.impl.VertxImpl;
import io.vertx.ext.web.Router;
import io.vertx.rxjava.ext.web.Route;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoadBalancerVertxController {

    private final RequestsDispatcher requestsDispatcher;
    private final Map<String,String> requestsCache = new ConcurrentHashMap<>();
    private final Vertx vertx = Vertx.vertx();
    private final HttpServer server = vertx.createHttpServer();

    public LoadBalancerVertxController(final RequestsDispatcher requestsDispatcher) {

        this.requestsDispatcher = requestsDispatcher;
    }

    public void init(){

        server.requestHandler(new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest httpServerRequest) {
                final String id = httpServerRequest.getParam("id");
                if(id == null) {
                    throw new GroupNotFoundException("Wrong parameters");
                }
                httpServerRequest.response().write(route(id));
                httpServerRequest.response().end();
            }
        }).listen(1234, "localhost");
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
