package my;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import static my.MyInvokeWithProcessorRoute.DIRECT_START;
import static my.MyInvokeWithProcessorRoute.MOCK_END;

public class MyInvokeWithProcessorTest extends CamelTestSupport {



    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        mockEndpoint.expectedMessageCount(1);
        template.sendBody(DIRECT_START, "nicolas");
        assertMockEndpointsSatisfied();
        String tuEh = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(String.class);

        assertEquals(tuEh, "Tu eh nicolas ?");
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new MyInvokeWithProcessorRoute();
    }
}
