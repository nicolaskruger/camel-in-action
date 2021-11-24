package my.valorant_agent_pojo_header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.toolbox.AggregationStrategies;
import org.junit.Test;

import java.util.Arrays;

import static my.valorant_agent_pojo_header.HeaderOperator.CLASS;

public class MyValorantAgentAggregationTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String BEAN = "bean";
    public static final String MOCK_END = "mock:end";

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private class ValorantDto{
        String name;
        ValorantClass valorantClass;
    }

    public void sendAgent(ValorantAgent agent, ValorantClass valorantClass){
        template.sendBodyAndHeader(DIRECT_START, agent, CLASS, valorantClass);
    }

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        Arrays.asList(
                new ValorantDto("jet", ValorantClass.DUELIST),
                new ValorantDto("reyna", ValorantClass.DUELIST),
                new ValorantDto("sova", ValorantClass.STARTER),
                new ValorantDto("skye", ValorantClass.STARTER),
                new ValorantDto("omen", ValorantClass.CONTROLER),
                new ValorantDto("vaiper", ValorantClass.CONTROLER)
        ).forEach( agent -> {
            sendAgent(new ValorantAgent(agent.name), agent.valorantClass);
        });
        assertNotNull(
                mockEndpoint.getExchanges().get(0).getIn().getBody()
        );
        mockEndpoint.expectedMessageCount(1);
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .aggregate(AggregationStrategies.bean(new ValorantAggregation(), BEAN))
                        .constant(true)
                        .completionSize(6)
                        .bean(DebbugValorant.class)
                        .to(MOCK_END);
            }
        };
    }
}
