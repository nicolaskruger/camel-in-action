package my.failover_rouding_robin;

import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MyFailoverRoudingRobin extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String DIRECT_A = "direct:a";
    public static final String DIRECT_B = "direct:b";
    public static final String BOOM = "boom";
    public static final String KABUM = "kabum";
    public static final String MOCK_A = "mock:a";
    public static final String MOCK_B = "mock:b";

    private void toMock(String endPoint, String... values ){
        getMockEndpoint(endPoint).expectedBodiesReceived(values);
    }


    @Test
    public void test() throws InterruptedException {

        toMock(MOCK_A, "a,kabum".split(","));
        toMock(MOCK_B, "b,boom".split(","));

        Arrays.stream("a,b,boom,kabum".split(",")).collect(Collectors.toList())
                .forEach(v -> {
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
                        .loadBalance().failover(1,false, true)
                        .to(DIRECT_A).to(DIRECT_B)
                        .end();
                @AllArgsConstructor
                class RouteDto{
                    String route;
                    String wordToCompare;
                    String routeMock;
                }
                Arrays.asList(
                        new RouteDto(DIRECT_A, BOOM, MOCK_A),
                        new RouteDto(DIRECT_B, KABUM, MOCK_B)
                ).forEach(v -> {
                    from(v.route)
                            .choice()
                            .when(body().contains(v.wordToCompare))
                                .throwException(new IllegalArgumentException("gay"))
                            .end()
                            .end()
                            .to(v.routeMock);
                });
            }
        };
    }
}
