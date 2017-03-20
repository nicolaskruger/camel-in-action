package com.camelinaction;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Camel as client that calls the hello service using a timer every 2nd seconds and logs the response
 */
@Component
public class HelloRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:bar?period=2000")
            // call the service using its servicename as hostname
            .to("http4://helloswarm-kubernetes/say?connectionClose=true")
            .log("${body}");
    }
}


