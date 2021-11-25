package my.split_aggregation_stop_on_exception;

import my.split_aggregate.SpliterWord;
import my.split_aggregate.WordAggregation;
import my.split_aggregate.WordConverter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.toolbox.AggregationStrategies;
import org.junit.Test;

public class SplitterStopOnException extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";

    @Test
    public void test(){
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);

        try {
            template.sendBody(DIRECT_START, "A,B,E");
            fail("should throw exception");
        }
        catch (Exception e){
            assertNotNull(e);
        }
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .split(method(SpliterWord.class), AggregationStrategies.bean(WordAggregation.class))
                            .stopOnException()
                            .bean(WordConverter.class)
                        .end()
                        .to(MOCK_END);
            }
        };
    }
}
