package my.routing_slip_header;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;

public class MyRoutingSlipHeaderTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MY_HEADER = "myHeader";

    private class AnswareDto{
        Integer times;
        MockEndpoint mockEndpoint;

        public AnswareDto(Integer times, String mockEndpoint) {
            this.times = times;
            this.mockEndpoint = getMockEndpoint(mockEndpoint);
        }
    }

    @Test
    public void test() throws InterruptedException {
        Arrays.asList(
                new AnswareDto(1, MyAnimeHeader.MOCK_FLCL),
                new AnswareDto(1, MyAnimeHeader.MOCK_OYASUMI_PUNPUN),
                new AnswareDto(1, MyAnimeHeader.MOCK_SAMURAY_CHAMPLO)
        ).forEach( v -> v.mockEndpoint.expectedMessageCount(v.times));

        template.sendBody(DIRECT_START, "naota,punpun,fu");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .setHeader(MY_HEADER).method(MyAnimeHeader.class)
                        .routingSlip(header(MY_HEADER));
            }
        };
    }
}
