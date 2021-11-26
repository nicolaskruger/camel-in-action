package my.dinamic_router_annotation;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;

public class MyDynamicRouterAnnotationBeanTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";

    @Test
    public void test() throws InterruptedException {
        Arrays.asList(
                "mock:naruto",
                "mock:sasuke",
                "mock:sakura"
        ).stream().map(v -> getMockEndpoint(v))
                .forEach(v -> {
                    v.expectedMessageCount(1);
                });

        template.sendBody(DIRECT_START, "naruto,sasuke,sakura");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .bean(MyDynamicRouterAnnotationBean.class, MyDynamicRouterAnnotationBean.METHOD)
                        .to(MOCK_END);
            }
        };
    }
}
