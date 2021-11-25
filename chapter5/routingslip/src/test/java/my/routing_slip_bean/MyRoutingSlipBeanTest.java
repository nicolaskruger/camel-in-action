package my.routing_slip_bean;

import lombok.var;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MyRoutingSlipBeanTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";

    @Test
    public void test() throws InterruptedException {
        var shinob = Arrays.asList("naruto", "sasuke", "sakura");
            shinob
                .stream()
                    .map(v -> getMockEndpoint("mock:"+v))
                    .forEach(v -> v.expectedMessageCount(1));
            template.sendBody(DIRECT_START, shinob.stream().collect(Collectors.joining(",")));
            assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .bean(MyRoutingSlipBean.class);
            }
        };
    }
}
