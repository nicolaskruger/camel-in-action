package my.load_balancer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyLoadBalancerTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String DIRECT_BALANCE_D = "direct:balance_%d";
    public static final String MOCK_END_D = "mock:end_%d";
    private List<Integer> range = Arrays.stream("012".split("")).map(v -> Integer.parseInt(v))
            .collect(Collectors.toList());

    private String toDirectRoute(Integer v){
        return String.format(DIRECT_BALANCE_D, v);
    }
    private String toMockRoute(Integer v){
        return String.format(MOCK_END_D,v);
    }

    private List<String> routes = range.stream().map(v -> toDirectRoute(v)).collect(Collectors.toList());
    private List<String> mockRoutes = range.stream().map(v -> toMockRoute(v)).collect(Collectors.toList());

    @Test
    public void test() throws InterruptedException {
        mockRoutes.stream().map(v -> getMockEndpoint(v))
                .forEach( v -> v.expectedMessageCount(1));
        Arrays.stream("oi;boa noite;bom dia".split(";"))
                .collect(Collectors.toList())
                .forEach( v -> {
                    template.sendBody(DIRECT_START, v);
                });
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .loadBalance().roundRobin()
                            .to(routes.toArray(new String[routes.size()]))
                        .end();
                range.forEach(
                        v -> {
                            from(toDirectRoute(v))
                                    .log("body: ${body}")
                                    .to(toMockRoute(v));
                        }
                );
            }
        };
    }
}
