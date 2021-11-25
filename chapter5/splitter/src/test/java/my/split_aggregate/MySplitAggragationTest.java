package my.split_aggregate;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.toolbox.AggregationStrategies;
import org.junit.Test;

public class MySplitAggragationTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";
    public static final String AGGREAGATE = "aggregate";

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        template.sendBody(DIRECT_START, "A,B,C");
        assertMockEndpointsSatisfied();
        mockEndpoint.expectedMessageCount(1);
        assertEquals(
                mockEndpoint.getExchanges().get(0).getIn().getBody(String.class),
                "bulbasaur_squirdle_charmander"
        );
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .split(method(SpliterWord.class),AggregationStrategies.bean(new WordAggregation(), AGGREAGATE))
                            .bean(WordConverter.class)
                        .end()
                        .to(MOCK_END);
            }
        };
    }
}
