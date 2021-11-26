package my.dinamic_router;

import camelinaction.DynamicRouterBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;

public class MyDynamicRouter extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String ROUTE = "route";
    public static final String MOCK_END = "mock:end";

    @Test
    public void test() throws InterruptedException {

        template.sendBody(DIRECT_START, "3");

        Arrays.asList(
                "mock:0!_1",
                "mock:1!_1",
                "mock:2!_2",
                "mock:3!_6"
        ).stream().map(v -> getMockEndpoint(v))
                .forEach( v -> v.expectedMessageCount(1));


        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .dynamicRouter(method(FatorialRouterBean.class, ROUTE))
                        .to(MOCK_END);

            }
        };
    }
}
