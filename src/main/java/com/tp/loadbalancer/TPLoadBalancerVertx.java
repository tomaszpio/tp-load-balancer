package com.tp.loadbalancer;

import com.tp.loadbalancer.config.GroupsPropertiesInterceptor;
import com.tp.loadbalancer.exceptions.GroupNotFoundException;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties(GroupsPropertiesInterceptor.class)
public class TPLoadBalancerVertx {

    public static void main(String[] args){
       Vertx vertx = Vertx.vertx();
        final HttpServer server = vertx.createHttpServer();
        server.requestHandler(new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest httpServerRequest) {
                final String id = httpServerRequest.getParam("id");
                if(id == null) {
                    throw new GroupNotFoundException("Wrong parameters");
                }
                httpServerRequest.response().write(id);
                httpServerRequest.response().end();
            }
        }).listen(1234, "localhost");
    }
}
