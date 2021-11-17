package camelinaction.bindy.my;

import junit.framework.TestCase;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;
import org.junit.Test;

import java.util.List;

public class MyPurchaseOrderUnmarshalBindyTest extends TestCase {

    public static final String DIRECT_TO_OBJECT = "direct:toObject";
    public static final String MOCK_RESULT = "mock:result";

    private void produceTemplate(ProducerTemplate producerTemplate){
        producerTemplate.sendBody(DIRECT_TO_OBJECT, "geleia,branco e preto,12\nmisa,tartaruga,13");

    }

    private void assetMyList(List<Cat> list){
        list.forEach(
                val ->{
                    assertNotNull(val);
                }
        );
    }

    private void mockCotext(MockEndpoint mockEndpoint, CamelContext camelContext) throws InterruptedException {
        mockEndpoint.expectedMessageCount(1);
        produceTemplate(camelContext.createProducerTemplate());
        mockEndpoint.assertIsSatisfied();
        assetMyList(mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(List.class));
    }

    private void context(CamelContext camelContext) throws Exception {
        camelContext.addRoutes(createRoute());
        camelContext.start();
        mockCotext(camelContext.getEndpoint(MOCK_RESULT, MockEndpoint.class), camelContext);

    }

    @Test
    public void test() throws Exception {
        context(new DefaultCamelContext());
    }

    public RouteBuilder createRoute() {
        return new RouteBuilder() {
            public void configure() throws Exception {
                from(DIRECT_TO_OBJECT)
                        .unmarshal().bindy(BindyType.Csv, Cat.class)
                        .to(MOCK_RESULT);
            }
        };
    }
}
