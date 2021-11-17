package my;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MyTransformTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_START = "mock:start";
    public static final String HEART_RESPONSE = "<3<3<3";
    public static final String S_2_S_2_S_2 = "S2S2S2";
    public static final String S_2 = "S2";
    public static final String REPLACEMENT = "<3";

    @Test
    public void test() throws InterruptedException {
        getMockEndpoint(MOCK_START)
                .expectedBodiesReceived(HEART_RESPONSE);
        template.sendBody(DIRECT_START, S_2_S_2_S_2);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .transform(body().regexReplaceAll(S_2, REPLACEMENT))
                        .to(MOCK_START);
            }
        };
    }
}
