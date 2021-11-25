package my.routing_slip_simple;

import lombok.var;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;

public class MyRoutingSlipSimpleTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MY_HEADER = "myHeader";
    public static final String MOCK_A = "mock:a";
    public static final String MOCK_B = "mock:b";
    public static final String MOCK_C = "mock:c";
    public static final String MOCK_A_MOCK_B_MOCK_C = "mock:a,mock:b,mock:c";

    @Test
    public void test() throws InterruptedException {

        var mockList = Arrays.asList(
                getMockEndpoint(MOCK_A),
                getMockEndpoint(MOCK_B),
                getMockEndpoint(MOCK_C)
        );

        mockList.forEach(mock -> {
            mock.expectedMessageCount(1);
        });

        template.sendBodyAndHeader(DIRECT_START, "hello word", MY_HEADER, MOCK_A_MOCK_B_MOCK_C);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .routingSlip(header(MY_HEADER));
            }
        };
    }
}
