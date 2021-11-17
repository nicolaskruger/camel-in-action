package camelinaction;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileCopierWithCamel {

    public static void main(String args[]) throws Exception {
        // create CamelContext
        CamelContext context = new DefaultCamelContext();

        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                        from("file:data/inbox?noop=true")
                                .log("#################################")
                                .setProperty("matue", constant("tue"))
                                .log("#################################")
                        .to("file:data/outbox");
            }
        });

        // start the route and let it do its work
        context.start();
        Thread.sleep(1000);

        // stop the CamelContext
        context.stop();
    }

}
