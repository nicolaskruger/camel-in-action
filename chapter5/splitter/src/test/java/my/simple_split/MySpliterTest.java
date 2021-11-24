package my.simple_split;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MySpliterTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";

    public void assertMock(MockEndpoint mockEndpoint, List<String> list){
        for (int i = 0; i < list.size(); i++) {
            assertEquals(
                    mockEndpoint.getExchanges().get(i).getIn().getBody(String.class),
                    list.get(i)
            );
        }
    }

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        mockEndpoint
                .expectedMessageCount(3);
        List<String> list = Arrays.asList("A","B","C");
        mockEndpoint.expectedMessageCount(3);
        template.sendBody(DIRECT_START, list);

        assertMock(mockEndpoint, list);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .split(body())
                        .to(MOCK_END);
            }
        };
    }
}
