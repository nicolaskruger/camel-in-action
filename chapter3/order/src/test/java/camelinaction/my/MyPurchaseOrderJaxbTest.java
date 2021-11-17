package camelinaction.my;

import camelinaction.PurchaseOrder;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class MyPurchaseOrderJaxbTest extends CamelTestSupport {

    public static final String MOCK_ORDER = "mock:order";
    public static final String DIRECT_ORDER = "direct:order";
    public static final String SEDA_QUEUE_ORDER = "seda:queue:order";
    public static final String DIRECT_ROUTE = "direct:route";

    private void mockEndPoint(MockEndpoint mockEndpoints){
        mockEndpoints.expectedMessageCount(1);
        mockEndpoints.message(0).body().isInstanceOf(PurchaseOrder.class);
    }
    private PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder){
        purchaseOrder.setName("Camel in action");
        purchaseOrder.setPrice(6999);
        purchaseOrder.setAmount(1);
        return purchaseOrder;
    }

    @Test
    public void test() throws InterruptedException {
        mockEndPoint(getMockEndpoint(MOCK_ORDER));
        template.sendBody(
                DIRECT_ORDER, createPurchaseOrder(new PurchaseOrder()));
        assertMockEndpointsSatisfied();
    }

    private JaxbDataFormat createJacbDataFormat() throws JAXBException {
        return new JaxbDataFormat(
                JAXBContext.newInstance(PurchaseOrder.class)
        );
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_ORDER)
                        .marshal()
                        .jaxb()
                        .log("\n marchal: \n ${body}")
                        .to(DIRECT_ROUTE);
                from(DIRECT_ROUTE)
                        .unmarshal(createJacbDataFormat())
                        .log("\n umarchal: \n ${body}")
                        .to(MOCK_ORDER);
            }
        };
    }
}
