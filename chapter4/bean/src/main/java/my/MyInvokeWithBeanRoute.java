package my;

import org.apache.camel.builder.RouteBuilder;

public class MyInvokeWithBeanRoute extends RouteBuilder {

    public static final String DIRECT_START = "direct:start";

    @Override
    public void configure() throws Exception {
        from(DIRECT_START)
                .bean(BussHelloBean.class, "tuEh");
    }
}
