package camelinaction.bindy.my;

import camelinaction.bindy.PurchaseOrder;
import junit.framework.TestCase;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;
import org.junit.Test;

import java.math.BigDecimal;

public class MyPurchaseOrderBindyTest extends TestCase {

    public static final String MOCK_END = "mock:end";
    public static final String DIRECT_START = "direct:start";

    private void myContext(CamelContext camelContext){

    }

    @Test
    public void test() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(createRouteBuilder());
        context.start();

        MockEndpoint mock = context.getEndpoint(MOCK_END, MockEndpoint.class);
        mock.expectedBodiesReceived("geleia,black and white,12\n");

        Cat geleia = Cat.builder()
                .name("geleia")
                .age(12)
                .color("black and white")
                .build();

        ProducerTemplate template = context.createProducerTemplate();
        template.sendBody(DIRECT_START, geleia);

        mock.assertIsSatisfied();
    }


    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .marshal().bindy(BindyType.Csv, Cat.class)
                        .to(MOCK_END);
            }
        };
    }
}
