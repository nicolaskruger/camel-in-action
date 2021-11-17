package camelinaction;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CatConverterTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";
    public static final String GELEIA = "geleia";

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        mockEndpoint.expectedMessageCount(1);
        template.sendBody(DIRECT_START, GELEIA);
        assertMockEndpointsSatisfied();
        Cat cat = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Cat.class);

        assertEquals(cat.getName(), GELEIA);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .convertBodyTo(Cat.class)
                        .to(MOCK_END);
            }
        };
    }
}
