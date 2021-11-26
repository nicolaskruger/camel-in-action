package my.failover_load_balance;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MyFailoverLoadBalanceTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_A = "mock:a";
    public static final String MOCK_B = "mock:b";
    public static final String KABUM = "kabum";
    public static final String DIRECT_A = "direct:a";
    public static final String DIRECT_B = "direct:b";

    public void myMock(String endPoint, String ...values){
        getMockEndpoint(endPoint).expectedBodiesReceived(values);
    }

    @Test
    public void test() throws InterruptedException {

        myMock(MOCK_A, "ola,test,foi".split(","));
        myMock(MOCK_B, "kabum");
        Arrays.stream("ola,kabum,test,foi".split(","))
                .collect(Collectors.toList())
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
                        .loadBalance().failover()
                            .to(DIRECT_A).to(DIRECT_B)
                        .end();

                from(DIRECT_A)
                        .choice()
                        .when(body().contains(KABUM))
                                .throwException(new IllegalArgumentException("erro"))
                            .end()
                        .end()
                        .to(MOCK_A);
                from(DIRECT_B)
                        .to(MOCK_B);
            }
        };
    }
}
